package com.telepathicgrunt.structurevoidtoggle.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class StructureVoidBlock extends Block
{
	//public so we can access and change the hitbox
	public static VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

	/*
	 * Rest of this code is exactly like Structure Void to cause no issues or weird behavior since we are registry replacing it.
	 * Thus people will be expecting this block to behave exactly like the vanilla block minus the hitbox changing.
	 */

	protected StructureVoidBlock()
	{
		super(Block.Properties.create(Material.STRUCTURE_VOID).doesNotBlockMovement().noDrops());
	}

	
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE; //Uses the current hitbox we have set
	}

	@OnlyIn(Dist.CLIENT)
	public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return 1.0F;
	}

	public PushReaction getPushReaction(BlockState state)
	{
		return PushReaction.DESTROY;
	}
}