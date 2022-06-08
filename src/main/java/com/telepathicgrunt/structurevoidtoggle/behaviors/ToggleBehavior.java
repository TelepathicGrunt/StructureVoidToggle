package com.telepathicgrunt.structurevoidtoggle.behaviors;

import com.mojang.blaze3d.platform.InputUtil;
import com.telepathicgrunt.structurevoidtoggle.mixin.StructureVoidBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBind;
import net.minecraft.text.MutableText;
import net.minecraft.text.component.TranslatableComponent;
import org.lwjgl.glfw.GLFW;

public class ToggleBehavior {
	public enum STRUCTURE_BLOCK_MODE {
		NO_HITBOX, 
		SMALL_HITBOX, 
		FULL_HITBOX;

		public STRUCTURE_BLOCK_MODE next() {
			// Loop back to start if on last enum
			if(ordinal() + 1 == values().length) {
				return values()[0];
			}
			
			return values()[ordinal() + 1];
		}
	}
	
	// The current mode for the structure void block for the current client
	public static STRUCTURE_BLOCK_MODE MODE = STRUCTURE_BLOCK_MODE.SMALL_HITBOX;

	public static final KeyBind KEY_BIND_STRUCTURE_VOID_TOGGLE =  new KeyBind(
			"key.structure_void", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.categories.misc");

	public static final KeyBind KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE =  new KeyBind(
			"key.structure_void_render", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, "key.categories.misc");

	// The current mode for the structure void block rendering for the current client
	public static boolean VISIBLE = true;

	/*
	 * Switches between modes when backtick is pressed.
	 *
	 * The mode is changed clientside so two player can be in different modes and nothing will break because
	 * the server is not what does raytracing. That's the client thing and is why changing hitboxes for a
	 * block that entities can't touch won't cause issues. In theory.
	 */
	public static void toggle(int key) {
		if (KEY_BIND_STRUCTURE_VOID_TOGGLE.isPressed()) {
			toggleHitbox();
		}
		if (KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE.isPressed()) {
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
		ClientPlayerEntity player  = MinecraftClient.getInstance().player;
		if(player == null) return;

		switch (MODE) {
			case NO_HITBOX -> {
				player.sendMessage(MutableText.create(new TranslatableComponent("Structure Void Toggle: No hitbox set.")), true);
				StructureVoidBlockAccessor.setSHAPE(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
			}
			case SMALL_HITBOX -> {
				player.sendMessage(MutableText.create(new TranslatableComponent("Structure Void Toggle: Small hitbox set.")), true);
				StructureVoidBlockAccessor.setSHAPE(Block.createCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D));
			}
			case FULL_HITBOX -> {
				player.sendMessage(MutableText.create(new TranslatableComponent("Structure Void Toggle: Full hitbox set.")), true);
				StructureVoidBlockAccessor.setSHAPE(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
			}
			default -> {
			}
		}
	}

	/**
	 * Switches between render modes when INSERT is pressed.
	 */
	private static void toggleRender() {
		VISIBLE = !VISIBLE;
		ClientPlayerEntity player  = MinecraftClient.getInstance().player;
		if (player == null) return;

		if (VISIBLE) {
			player.sendMessage(MutableText.create(new TranslatableComponent("Structure Void Toggle: Visible.")), true);
		}
		else {
			player.sendMessage(MutableText.create(new TranslatableComponent("Structure Void Toggle: Invisible.")), true);
		}
	}
}
