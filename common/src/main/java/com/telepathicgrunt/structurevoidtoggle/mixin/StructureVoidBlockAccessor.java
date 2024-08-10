package com.telepathicgrunt.structurevoidtoggle.mixin;

import net.minecraft.world.level.block.StructureVoidBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureVoidBlock.class)
public interface StructureVoidBlockAccessor {
    @Mutable
    @Accessor("SHAPE")
    static void setSHAPE(VoxelShape shape) {
        throw new UnsupportedOperationException();
    }
}
