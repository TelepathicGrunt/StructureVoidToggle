package com.telepathicgrunt.structurevoidtoggle.behaviors;

import com.telepathicgrunt.structurevoidtoggle.mixin.StructureVoidBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class ToggleBehavior
{
	public enum STRUCTURE_BLOCK_MODE
	{
		NO_HITBOX, 
		SMALL_HITBOX, 
		FULL_HITBOX;

		public STRUCTURE_BLOCK_MODE next()
		{
			// Loop back to start if on last enum
			if(ordinal() + 1 == values().length)
			{
				return values()[0];
			}
			
			return values()[ordinal() + 1];
		}
	}
	
	// The current mode for the structure void block for the current client
	public static STRUCTURE_BLOCK_MODE MODE = STRUCTURE_BLOCK_MODE.SMALL_HITBOX;
	
	// Keybind for switching modes. 96 is the keycode for backtick `
	public static final KeyBinding KEY_BIND_STRUCTURE_VOID_TOGGLE =  new KeyBinding(
			"key.structure_void", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.misc");

	/*
	 * Switches between modes when backtick is pressed.
	 *
	 * The mode is changed clientside so two player can be in different modes and nothing will break because
	 * the server is not what does raytracing. That's the client thing and is why changing hitboxes for a
	 * block that entities can't touch won't cause issues. In theory.
	 */
	public static void toggle(int key) {
		// Changes the structure void mode
		if (KEY_BIND_STRUCTURE_VOID_TOGGLE.isPressed()) {
			MODE = MODE.next();
			ClientPlayerEntity player  = MinecraftClient.getInstance().player;
			if(player == null) return;

			switch (MODE) {
				case NO_HITBOX -> {
					player.sendMessage(new LiteralText("Structure Void Toggle: No hitbox set."), true);
					StructureVoidBlockAccessor.setSHAPE(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
				}
				case SMALL_HITBOX -> {
					player.sendMessage(new LiteralText("Structure Void Toggle: Small hitbox set."), true);
					StructureVoidBlockAccessor.setSHAPE(Block.createCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D));
				}
				case FULL_HITBOX -> {
					player.sendMessage(new LiteralText("Structure Void Toggle: Full hitbox set."), true);
					StructureVoidBlockAccessor.setSHAPE(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
				}
				default -> {
				}
			}
		}
	}
}
