package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class StructureVoidToggleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        KeyBindingHelper.registerKeyBinding(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
        KeyBindingHelper.registerKeyBinding(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
    }
}