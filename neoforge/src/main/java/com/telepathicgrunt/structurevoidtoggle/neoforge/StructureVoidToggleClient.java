package com.telepathicgrunt.structurevoidtoggle.neoforge;

import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggle;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = StructureVoidToggle.MODID, dist = Dist.CLIENT)
public class StructureVoidToggleClient {

    public StructureVoidToggleClient(IEventBus modEventBus) {
        modEventBus.addListener(StructureVoidToggleClient::registerKeyBinding);
        NeoForge.EVENT_BUS.addListener(StructureVoidToggleClient::forceRenderInvisibleBlocks);
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }

    public static void forceRenderInvisibleBlocks(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            ToggleBehavior.forceRenderInvisibleBlocks(event.getCamera(), event.getPoseStack(), event.getLevelRenderer(), false);
        }
    }
}