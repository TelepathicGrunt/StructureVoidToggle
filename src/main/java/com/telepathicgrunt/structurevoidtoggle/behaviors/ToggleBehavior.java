package com.telepathicgrunt.structurevoidtoggle.behaviors;

import com.telepathicgrunt.structurevoidtoggle.mixin.StructureVoidBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.StringTextComponent;
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

	// The current mode for the structure void block rendering for the current client
	public static boolean VISIBLE = true;
	
	// Keybind for switching hitbox modes. 96 is the keycode for backtick `
	public static final KeyBinding KEY_BIND_STRUCTURE_VOID_TOGGLE = new KeyBinding(
			"key.structure_void", GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.misc");

	// Keybind for switching render modes. INSERT by default
	public static final KeyBinding KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE = new KeyBinding(
			"key.structure_void_render", GLFW.GLFW_KEY_INSERT, "key.categories.misc"
	);

	/**
	 * Toggles settings for the relevant keypress.
	 */
	public static void toggle(int key) {
		if (KEY_BIND_STRUCTURE_VOID_TOGGLE.isDown()) {
			toggleHitbox();
		}
		if (KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE.isDown()) {
			toggleRender();
		}
	}

	/*
	 * Switches between hitbox modes when backtick is pressed.
	 *
	 * The mode is changed clientside so two player can be in different modes and nothing will break because
	 * the server is not what does raytracing. That's the client thing and is why changing hitboxes for a
	 * block that entities can't touch won't cause issues. In theory.
	 */
	private static void toggleHitbox() {
		MODE = MODE.next();
		ClientPlayerEntity player  = Minecraft.getInstance().player;
		if(player == null) return;

		switch (MODE) {
			case NO_HITBOX :
				player.displayClientMessage(new StringTextComponent("Structure Void Toggle: No hitbox set."), true);
				StructureVoidBlockAccessor.setSHAPE(Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
				break;
			case SMALL_HITBOX :
				player.displayClientMessage(new StringTextComponent("Structure Void Toggle: Small hitbox set."), true);
				StructureVoidBlockAccessor.setSHAPE(Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D));
				break;
			case FULL_HITBOX :
				player.displayClientMessage(new StringTextComponent("Structure Void Toggle: Full hitbox set."), true);
				StructureVoidBlockAccessor.setSHAPE(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
				break;
			default :
				break;
		}
	}

	/**
	 * Switches between render modes when INSERT is pressed.
	 */
	private static void toggleRender() {
		VISIBLE = !VISIBLE;
		ClientPlayerEntity player  = Minecraft.getInstance().player;
		if (player == null) return;

		if (VISIBLE) {
			player.displayClientMessage(new StringTextComponent("Structure Void Toggle: Visible."), true);
		} else {
			player.displayClientMessage(new StringTextComponent("Structure Void Toggle: Invisible."), true);
		}
	}
}
