package com.telepathicgrunt.structurevoidtoggle.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {
    @Accessor("renderBuffers")
    RenderBuffers getRenderBuffers();

    @Accessor("cullingFrustum")
    Frustum getCullingFrustum();
}
