package com.telepathicgrunt.structurevoidtoggle.behaviors;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StructureVoidBlockShape {
    // Holds Structure Void Block's shape
    public static VoxelShape STRUCTURE_VOID_TOGGLE$SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

    // The current mode for if player placing is put on top of this block or replaces this block
    public static boolean FORCED_NON_REPLACEABLE = false;
}
