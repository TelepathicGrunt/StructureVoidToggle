package com.telepathicgrunt.structurevoidtoggle.mixin;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StructureVoidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StructureVoidBlock.class)
public class StructureVoidBlockMixin extends Block {

    public StructureVoidBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return ToggleBehavior.STRUCTURE_VOID_TOGGLE$SHAPE;
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext context) {
        if (ToggleBehavior.FORCED_NON_REPLACEABLE) {
            return false;
        }
        return super.canBeReplaced(blockState, context);
    }
}

