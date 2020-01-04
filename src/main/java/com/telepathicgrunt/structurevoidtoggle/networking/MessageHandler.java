package com.telepathicgrunt.structurevoidtoggle.networking;

import java.util.function.Supplier;

import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggleMain;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;


public class MessageHandler
{
	//setup channel to send packages through
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel DEFAULT_CHANNEL = NetworkRegistry.newSimpleChannel(
																			new ResourceLocation(StructureVoidToggleMain.MODID, "networking"), 
																			() -> PROTOCOL_VERSION,PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
																		);

	/*
	 * Register the channel so it exists
	 */
	public static void init()
	{
		int channelID = -1;
		DEFAULT_CHANNEL.registerMessage(++channelID, BlockPlacingPacket.class, BlockPlacingPacket::compose, BlockPlacingPacket::parse, BlockPlacingPacket.Handler::handle);
	}

	/*
	 * Packet to send to server and how the server will respond
	 * 
	 * Holds blockpos and block when sending and server will use pos and block to do setBlockState() which will then send change to all clients 
	 */
	public static class BlockPlacingPacket
	{
		String blockRL = null;
		BlockPos pos = BlockPos.ZERO;


		/*
		 * Convert block's resource location to string and sends it to server with blockpos
		 */
		public static void sendToServer(BlockPos pos, Block block)
		{
			String rlString = ForgeRegistries.BLOCKS.getKey(block).toString();

			if ((pos != null) && (rlString != null))
				MessageHandler.DEFAULT_CHANNEL.sendToServer(new BlockPlacingPacket(pos, rlString));
		}


		/*
		 * Sets block location and resource location
		 */
		public BlockPlacingPacket(BlockPos pos, String blockRLIn)
		{
			this.blockRL = blockRLIn;
			this.pos = pos;
		}


		/*
		 * How the server will read the packet. String length is set to 24 as that's how long
		 * the string `minecraft:structure_void` is.
		 */
		public static BlockPlacingPacket parse(final PacketBuffer buf)
		{
			return new BlockPlacingPacket(buf.readBlockPos(), buf.readString(24));
		}
		

		/*
		 * creates the packet buffer and sets its values
		 */
		public static void compose(final BlockPlacingPacket pkt, final PacketBuffer buf)
		{
			buf.writeBlockPos(pkt.pos);
			buf.writeString(pkt.blockRL);
		}

		
		/*
		 * What the server will do with the packet
		 */
		public static class Handler
		{
			//this is what gets run on the server
			@SuppressWarnings("deprecation")
			public static void handle(final BlockPlacingPacket pkt, final Supplier<NetworkEvent.Context> ctx)
			{
				ctx.get().enqueueWork(() ->
				{
					PlayerEntity player = ctx.get().getSender();
					World world = player.world;
					
					//Make sure world exist and position is loaded.
					//
					//Have to do the position check so people can't abuse it to cause
					//unloaded chunks to load to grief the server's performance.
					if (world == null || !world.isBlockLoaded(pkt.pos))
						return;

					//Set the block and send change to clients. (Doesn't cause block update to neighboring blocks)
					world.setBlockState(pkt.pos, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(pkt.blockRL)).getDefaultState(), 2);
				});
				ctx.get().setPacketHandled(true);
			}
		}
	}

}