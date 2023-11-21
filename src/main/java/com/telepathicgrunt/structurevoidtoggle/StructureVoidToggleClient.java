package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

public class StructureVoidToggleClient {

    public static void onInitializeClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(StructureVoidToggleClient::registerKeyBinding);
        NeoForge.EVENT_BUS.addListener(ToggleBehavior::forceRenderInvisibleBlocks);
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }
}