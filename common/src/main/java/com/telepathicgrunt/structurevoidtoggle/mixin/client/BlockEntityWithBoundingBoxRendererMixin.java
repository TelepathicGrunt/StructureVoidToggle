package com.telepathicgrunt.structurevoidtoggle.mixin.client;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.renderer.blockentity.BlockEntityWithBoundingBoxRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityWithBoundingBoxRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.entity.BoundingBoxRenderable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithBoundingBoxRenderer.class)
public class BlockEntityWithBoundingBoxRendererMixin {
    @Inject(method = "renderInvisibleBlocks(Lnet/minecraft/client/renderer/blockentity/state/BlockEntityWithBoundingBoxRenderState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Vec3i;)V",
                    at = @At(value = "HEAD"),
                    cancellable = true)
    public void toggleShowAir(BlockEntityWithBoundingBoxRenderState blockEntityWithBoundingBoxRenderState, BlockPos blockPos, Vec3i vec3i, CallbackInfo ci) {
        if (!ToggleBehavior.VISIBLE && blockEntityWithBoundingBoxRenderState.mode == BoundingBoxRenderable.Mode.BOX_AND_INVISIBLE_BLOCKS) {
            ci.cancel();
        }
    }
}
