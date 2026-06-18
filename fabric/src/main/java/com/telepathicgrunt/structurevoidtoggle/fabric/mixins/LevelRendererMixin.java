package com.telepathicgrunt.structurevoidtoggle.fabric.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import com.telepathicgrunt.structurevoidtoggle.mixin.client.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 1010, value = LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "lambda$addMainPass$0(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;Lcom/mojang/blaze3d/textures/GpuSampler;)V",
                    ordinal = 0)
    )
    private void structureVoidToggle$renderAfterSolidBlocks2(CallbackInfo ci) {
        ToggleBehavior.forceRenderInvisibleBlocks(Minecraft.getInstance().player.level(), ((LevelRendererAccessor)this).getLevelRenderState().cameraRenderState.pos,  ((LevelRendererAccessor)this).getLevelRenderState().cameraRenderState.cullFrustum, new PoseStack(), false);
    }
}
