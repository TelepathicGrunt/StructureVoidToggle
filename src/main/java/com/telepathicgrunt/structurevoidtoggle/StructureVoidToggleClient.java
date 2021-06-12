package com.telepathicgrunt.structurevoidtoggle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE;

@Environment(EnvType.CLIENT)
public class StructureVoidToggleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_TOGGLE);
    }
}