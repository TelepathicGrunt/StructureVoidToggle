package com.telepathicgrunt.structurevoidtoggle.mixin.client;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.renderer.tileentity.StructureTileEntityRenderer;
import net.minecraft.tileentity.StructureBlockTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureTileEntityRenderer.class)
public class StructureTileEntityRendererMixin {
    @Redirect(method = "render(Lnet/minecraft/tileentity/StructureBlockTileEntity;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;II)V",
                    at = @At(value = "INVOKE",
                             target="Lnet/minecraft/tileentity/StructureBlockTileEntity;getShowAir()Z"))
    public boolean toggleShowAir(StructureBlockTileEntity tileEntity) {
        if (!ToggleBehavior.VISIBLE) return false;
        return tileEntity.getShowAir();
    }
}
