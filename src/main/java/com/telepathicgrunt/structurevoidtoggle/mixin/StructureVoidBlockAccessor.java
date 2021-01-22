package com.telepathicgrunt.structurevoidtoggle.mixin;

import net.minecraft.block.StructureVoidBlock;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureVoidBlock.class)
public interface StructureVoidBlockAccessor {
    @Mutable
    @Accessor
    static void setSHAPE(VoxelShape SHAPE) {
        throw new UnsupportedOperationException();
    }
}
