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
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel DEFAULT_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(StructureVoidToggleMain.MODID, "networking"), () -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);


	public static void init()
	{
		int channelID = -1;
		DEFAULT_CHANNEL.registerMessage(++channelID, BlockPlacingMessage.class, BlockPlacingMessage::compose, BlockPlacingMessage::parse, BlockPlacingMessage.Handler::handle);
	}

	public static class BlockPlacingMessage
	{
		String blockRL = null;
		BlockPos pos = BlockPos.ZERO;


		public static void sendToServer(BlockPos pos, Block block)
		{
			String rlString = ForgeRegistries.BLOCKS.getKey(block).toString();

			if ((pos != null) && (rlString != null))
				MessageHandler.DEFAULT_CHANNEL.sendToServer(new BlockPlacingMessage(pos, rlString));
		}


		public BlockPlacingMessage()
		{
		}


		public BlockPlacingMessage(BlockPos pos, String blockRLIn)
		{
			this.blockRL = blockRLIn;
			this.pos = pos;
		}


		public static BlockPlacingMessage parse(final PacketBuffer buf)
		{
			return new BlockPlacingMessage(buf.readBlockPos(), buf.readString(24));
		}


		public static void compose(final BlockPlacingMessage pkt, final PacketBuffer buf)
		{
			buf.writeBlockPos(pkt.pos);
			buf.writeString(pkt.blockRL);
		}

		public static class Handler
		{
			@SuppressWarnings("deprecation")
			public static void handle(final BlockPlacingMessage pkt, final Supplier<NetworkEvent.Context> ctx)
			{
				ctx.get().enqueueWork(() ->
				{
					//this is what gets run on the server

					PlayerEntity player = ctx.get().getSender();
					World world = player.world;
					if (world == null || !world.isBlockLoaded(pkt.pos))
						return;

					world.setBlockState(pkt.pos, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(pkt.blockRL)).getDefaultState(), 2);
				});
				ctx.get().setPacketHandled(true);
			}
		}
	}

}