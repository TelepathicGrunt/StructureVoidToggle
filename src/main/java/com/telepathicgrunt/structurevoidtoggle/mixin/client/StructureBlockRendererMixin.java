package com.telepathicgrunt.structurevoidtoggle.mixin.client;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.renderer.blockentity.StructureBlockRenderer;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureBlockRenderer.class)
public class StructureBlockRendererMixin {
    @Redirect(method = "render(Lnet/minecraft/world/level/block/entity/StructureBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE",
                    target="Lnet/minecraft/world/level/block/entity/StructureBlockEntity;getShowAir()Z"))
    public boolean toggleShowAir(StructureBlockEntity tileEntity) {
        if (!ToggleBehavior.VISIBLE) return false;
        return tileEntity.getShowAir();
    }
}
