package com.telepathicgrunt.structurevoidtoggle.behaviors;

import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggleMain;
import com.telepathicgrunt.structurevoidtoggle.blocks.BlocksInit;
import com.telepathicgrunt.structurevoidtoggle.blocks.StructureVoidBlock;
import com.telepathicgrunt.structurevoidtoggle.networking.MessageHandler;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = StructureVoidToggleMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = { Dist.CLIENT })
public class ToggleBehavior
{
	public static enum STRUCTURE_BLOCK_MODE
	{
		NO_HITBOX, 
		SMALL_HITBOX, 
		FULL_HITBOX,
		AUTO_PLACING{
			@Override
			public STRUCTURE_BLOCK_MODE next()
			{
				return NO_HITBOX; // see below for options for this line
			};
		};

		public STRUCTURE_BLOCK_MODE next()
		{
			// No bounds checking required here, because the last instance overrides
			return values()[ordinal() + 1];
		}
	}
	private static STRUCTURE_BLOCK_MODE mode = STRUCTURE_BLOCK_MODE.SMALL_HITBOX;
	
	//keybind for switching mod
	public static final KeyBinding keyBindStructureVoidToggle = new KeyBinding("key.structure_void", 96, "key.categories.misc");

	
	
	@Mod.EventBusSubscriber(modid = StructureVoidToggleMain.MODID, value = { Dist.CLIENT })
	private static class ForgeEvents
	{
		
		/*
		 * Switches between modes when backtick is pressed.
		 * 
		 * The mode is changed clientside so two player can be in different modes and nothing will break because
		 * the server is not what does raytracing. That's the client thing and is why changing hitboxes for a
		 * block that entities can't touch won't cause issues. In theory.
		 */
		@SubscribeEvent
		public static void onEvent(InputEvent.KeyInputEvent event)
		{
			//Changes the structure void mode globally
			if (keyBindStructureVoidToggle.isPressed())
			{
				mode = mode.next();

				switch (mode)
				{
					case NO_HITBOX:
					{
						Minecraft.getInstance().player.sendMessage(new StringTextComponent("Structure Void Toggle: No hitbox set."));
						StructureVoidBlock.SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
						break;
					}
					case SMALL_HITBOX:
					{
						Minecraft.getInstance().player.sendMessage(new StringTextComponent("Structure Void Toggle: Small hitbox set."));
						StructureVoidBlock.SHAPE = Block.makeCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);
						break;
					}
					case FULL_HITBOX:
					{
						Minecraft.getInstance().player.sendMessage(new StringTextComponent("Structure Void Toggle: Full hitbox set."));
						StructureVoidBlock.SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
						break;
					}
					case AUTO_PLACING:
					{
						Minecraft.getInstance().player.sendMessage(new StringTextComponent("Structure Void Toggle: Autoplacing set."));
						StructureVoidBlock.SHAPE = Block.makeCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);
						break;
					}
					default:
					{
						break;
					}
				}
			}
		}
		

		/*
		 * Only autoplaces structure void blocks if the mode is set to auto placing and
		 * the player is pressing one of the movement key. (falling won't place blocks)
		 * 
		 * Have check for creative so we can add moving up and down for flying
		 */
		@SubscribeEvent
		public static void sneakBoost(InputUpdateEvent event)
		{
			PlayerEntity player = event.getPlayer();

	        if(mode == STRUCTURE_BLOCK_MODE.AUTO_PLACING) 
	        {
				if ((event.getMovementInput().forwardKeyDown ||
					event.getMovementInput().leftKeyDown ||
					event.getMovementInput().rightKeyDown ||
					event.getMovementInput().backKeyDown) ||
					(player.isCreative() && 
					  (event.getMovementInput().jump ||
					   event.getMovementInput().field_228350_h_)
					))
				{
					//replaces block at feet.
					//move player pos up .3 blocks so blocks like dirt roads or slabs won't get replaced since it lower the player.
					BlockPos playerPosition = new BlockPos(player.func_226277_ct_(), player.func_226278_cu_()+0.99f, player.func_226281_cx_());
					MessageHandler.BlockPlacingMessage.sendToServer(playerPosition, BlocksInit.STRUCTURE_VOID.get());
					
					//crouching makes it not place block at eye level.
					if(!player.isCrouching()) {
						//raised one and a half block so walking on slabs won't replace the block above your head.
						playerPosition = new BlockPos(player.func_226277_ct_(), player.func_226278_cu_()+1.49f, player.func_226281_cx_());
						
						//replaced block at eye level
						MessageHandler.BlockPlacingMessage.sendToServer(playerPosition, BlocksInit.STRUCTURE_VOID.get());
					}
		        }
			}
		}
	}
}
