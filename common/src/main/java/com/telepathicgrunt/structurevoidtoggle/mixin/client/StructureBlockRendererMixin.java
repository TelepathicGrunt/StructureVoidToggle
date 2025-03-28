package com.telepathicgrunt.structurevoidtoggle.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.renderer.blockentity.BlockEntityWithBoundingBoxRenderer;
import net.minecraft.world.level.block.entity.BoundingBoxRenderable;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockEntityWithBoundingBoxRenderer.class)
public class StructureBlockRendererMixin {
    @ModifyExpressionValue(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/phys/Vec3;)V",
                    at = @At(value = "INVOKE_ASSIGN", target="renderMode()Lnet/minecraft/world/level/block/entity/BoundingBoxRenderable$Mode;"))
    public BoundingBoxRenderable.Mode toggleShowAir(BoundingBoxRenderable.Mode mode) {
        if (!ToggleBehavior.VISIBLE && mode == BoundingBoxRenderable.Mode.BOX_AND_INVISIBLE_BLOCKS) return BoundingBoxRenderable.Mode.BOX;
        return mode;
    }
}
