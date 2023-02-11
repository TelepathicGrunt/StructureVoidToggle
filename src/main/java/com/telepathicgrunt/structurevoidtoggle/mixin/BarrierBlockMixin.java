package com.telepathicgrunt.structurevoidtoggle.mixin;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ShapeInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BarrierBlock.class)
public class BarrierBlockMixin extends Block implements ShapeInterface {

    @Unique
    private static VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public BarrierBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public void setShape(VoxelShape shape) {
        SHAPE = shape;
    }

    @Override
    public VoxelShape getShape() {
        return SHAPE;
    }
}

