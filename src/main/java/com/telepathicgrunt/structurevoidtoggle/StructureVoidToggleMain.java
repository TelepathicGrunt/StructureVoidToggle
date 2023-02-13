package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StructureVoidToggleMain implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "structure_void_toggle";


	public void onInitialize() {
		WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((worldRenderContext, result) -> {
			ToggleBehavior.forceRenderInvisibleBlocks(worldRenderContext.matrixStack(), worldRenderContext.camera(), worldRenderContext.worldRenderer());
			return true;
		});

	}
}
