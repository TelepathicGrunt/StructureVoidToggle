package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = StructureVoidToggleClient.MODID, dist = Dist.CLIENT)
public class StructureVoidToggleClient {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "structure_void_toggle";

    public StructureVoidToggleClient(IEventBus modEventBus) {
        modEventBus.addListener(StructureVoidToggleClient::registerKeyBinding);
        NeoForge.EVENT_BUS.addListener(ToggleBehavior::forceRenderInvisibleBlocks);
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }
}