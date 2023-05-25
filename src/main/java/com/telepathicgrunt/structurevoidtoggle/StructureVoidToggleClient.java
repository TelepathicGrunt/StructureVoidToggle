package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE;

@Environment(EnvType.CLIENT)
public class StructureVoidToggleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((worldRenderContext, result) -> {
            ToggleBehavior.forceRenderInvisibleBlocks(worldRenderContext.matrixStack(), worldRenderContext.camera(), worldRenderContext.worldRenderer());
            return true;
        });

        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_TOGGLE);
        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }
}