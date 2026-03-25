package com.telepathicgrunt.structurevoidtoggle.fabric.entrypoints;

import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE;

@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        StructureVoidToggle.StructureVoidToggleInit();
        KeyMappingHelper.registerKeyMapping(KEY_BIND_STRUCTURE_VOID_TOGGLE);
        KeyMappingHelper.registerKeyMapping(KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        KeyMappingHelper.registerKeyMapping(KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }
}