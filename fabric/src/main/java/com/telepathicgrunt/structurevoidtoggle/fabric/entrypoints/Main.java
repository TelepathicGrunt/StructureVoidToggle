package com.telepathicgrunt.structurevoidtoggle.fabric.entrypoints;

import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE;

@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        StructureVoidToggle.StructureVoidToggleInit();
        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_TOGGLE);
        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        KeyBindingHelper.registerKeyBinding(KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }
}