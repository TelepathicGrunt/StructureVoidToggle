package com.telepathicgrunt.structurevoidtoggle.mixin;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ShapeInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LightBlock.class)
public class LightBlockMixin extends Block implements ShapeInterface {

    @Unique
    private static VoxelShape SHAPE = Block.box(-2, -2, -2, -1, -1, -1);

    public LightBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (SHAPE.isEmpty() || SHAPE.max(Direction.Axis.X) >= 0) {
            return SHAPE;
        }
        return collisionContext.isHoldingItem(Items.LIGHT) ? Shapes.block() : Shapes.empty();
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

