package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class StructureVoidToggleClient {

    public static void onInitializeClient() {
        ClientRegistry.registerKeyBinding(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
    }
}