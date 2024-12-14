package com.telepathicgrunt.structurevoidtoggle.fabric.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 1010, value = LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "renderSectionLayer(Lnet/minecraft/client/renderer/RenderType;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType;clearRenderState()V",
                    ordinal = 1)
    )
    private void structureVoidToggle$renderAfterSolidBlocks(CallbackInfo ci, @Local(argsOnly = true, ordinal = 0) RenderType renderType) {
        if (renderType == RenderType.solid()) {
            ToggleBehavior.forceRenderInvisibleBlocks(Minecraft.getInstance().gameRenderer.getMainCamera(), new PoseStack(), (LevelRenderer) (Object) this, false);
        }
    }
}
