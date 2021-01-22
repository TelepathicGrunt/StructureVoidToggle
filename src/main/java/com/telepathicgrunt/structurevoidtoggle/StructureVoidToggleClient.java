package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import com.telepathicgrunt.structurevoidtoggle.mixin.GameOptionsAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.keyBindStructureVoidToggle;
import static com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior.toggle;

@Environment(EnvType.CLIENT)
public class StructureVoidToggleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingRegistry.INSTANCE.register(keyBindStructureVoidToggle);
    }
}