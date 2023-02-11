package com.telepathicgrunt.structurevoidtoggle.mixin;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StructureVoidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StructureVoidBlock.class)
public class StructureVoidBlockMixin extends Block {

    public StructureVoidBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext context) {
        if (ToggleBehavior.FORCED_NON_REPLACEABLE) {
            return false;
        }
        return super.canBeReplaced(blockState, context);
    }
}

