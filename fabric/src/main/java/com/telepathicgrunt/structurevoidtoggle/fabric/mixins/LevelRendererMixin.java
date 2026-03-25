package com.telepathicgrunt.structurevoidtoggle.fabric.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import com.telepathicgrunt.structurevoidtoggle.mixin.client.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 1010, value = LevelRenderer.class)
public class LevelRendererMixin {

    @Unique
    private static Frustum structureVoidToggle$frustum = null;



    @Inject(method = "addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/culling/Frustum;Lorg/joml/Matrix4fc;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;ZLnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;)V",
            at = @At(value = "HEAD",
                    ordinal = 0)
    )
    private void structureVoidToggle$renderAfterSolidBlocks1(CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) Frustum frustum) {
        structureVoidToggle$frustum = frustum;
    }


    @Inject(method = "lambda$addMainPass$0(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;ZLorg/joml/Matrix4fc;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;Lcom/mojang/blaze3d/textures/GpuSampler;)V",
                    ordinal = 0)
    )
    private void structureVoidToggle$renderAfterSolidBlocks2(CallbackInfo ci) {
        if (structureVoidToggle$frustum != null) {
            ToggleBehavior.forceRenderInvisibleBlocks(Minecraft.getInstance().player.level(), ((LevelRendererAccessor)this).getLevelRenderState().cameraRenderState.pos, structureVoidToggle$frustum, new PoseStack(), false);
        }
    }
}
