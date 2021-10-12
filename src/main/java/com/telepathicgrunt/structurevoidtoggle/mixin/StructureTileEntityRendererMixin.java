package com.telepathicgrunt.structurevoidtoggle.mixin;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.client.render.block.entity.StructureBlockBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureBlockBlockEntityRenderer.class)
public class StructureTileEntityRendererMixin {
    @Redirect(method = "render(Lnet/minecraft/block/entity/StructureBlockBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
                    at = @At(value = "INVOKE",
                             target="Lnet/minecraft/block/entity/StructureBlockBlockEntity;shouldShowAir()Z"))
    public boolean toggleShowAir(StructureBlockBlockEntity tileEntity) {
        if (!ToggleBehavior.VISIBLE) return false;
        return tileEntity.shouldShowAir();
    }
}
