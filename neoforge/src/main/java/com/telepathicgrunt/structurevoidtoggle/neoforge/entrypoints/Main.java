package com.telepathicgrunt.structurevoidtoggle.neoforge.entrypoints;

import com.telepathicgrunt.structurevoidtoggle.StructureVoidToggle;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

@Mod(value = StructureVoidToggle.MODID, dist = Dist.CLIENT)
public class Main {

    public Main(IEventBus modEventBus) {
        StructureVoidToggle.StructureVoidToggleInit();
        modEventBus.addListener(Main::registerKeyBinding);
        NeoForge.EVENT_BUS.addListener(Main::forceRenderInvisibleBlocks1);
        NeoForge.EVENT_BUS.addListener(Main::forceRenderInvisibleBlocks2);
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_RENDER_TOGGLE);
        event.register(ToggleBehavior.KEY_BIND_STRUCTURE_VOID_FORCED_RENDER_TOGGLE);
    }

    record ForceRenderInvisibleBlocksState(Frustum frustum, Vec3 cameraPos, ClientLevel clientLevel) {}

    private static final ContextKey<ForceRenderInvisibleBlocksState> CONTEXT_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(StructureVoidToggle.MODID, "force_render_invisible_blocksstate"));

    public static void forceRenderInvisibleBlocks1(ExtractLevelRenderStateEvent event) {
        event.getRenderState().setRenderData(CONTEXT_KEY, new ForceRenderInvisibleBlocksState(event.getFrustum(), event.getCamera().position(), event.getLevel()));
    }

    public static void forceRenderInvisibleBlocks2(RenderLevelStageEvent.AfterOpaqueBlocks event) {
        @Nullable ForceRenderInvisibleBlocksState renderData = event.getLevelRenderState().getRenderData(CONTEXT_KEY);
        if (renderData != null) {
            ToggleBehavior.forceRenderInvisibleBlocks(renderData.clientLevel(), renderData.cameraPos(), renderData.frustum(), event.getPoseStack(), false);
        }
    }
}