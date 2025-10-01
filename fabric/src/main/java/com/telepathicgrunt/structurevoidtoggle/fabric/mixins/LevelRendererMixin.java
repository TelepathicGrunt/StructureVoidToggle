package com.telepathicgrunt.structurevoidtoggle.fabric.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import com.telepathicgrunt.structurevoidtoggle.mixin.client.LevelRendererAccessor;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 1010, value = LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "method_62214(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;ZLnet/minecraft/client/renderer/culling/Frustum;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;renderGroup(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;)V",
                    ordinal = 0)
    )
    private void structureVoidToggle$renderAfterSolidBlocks(CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) Frustum frustum) {
        ToggleBehavior.forceRenderInvisibleBlocks(((LevelRendererAccessor)this).getLevelRenderState().cameraRenderState, frustum, new PoseStack(), false);
    }
}
