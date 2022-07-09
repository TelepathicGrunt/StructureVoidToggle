package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class StructureVoidToggleClient {

    public static void onInitializeClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(StructureVoidToggleClient::registerKeyBinding);
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
    }
}