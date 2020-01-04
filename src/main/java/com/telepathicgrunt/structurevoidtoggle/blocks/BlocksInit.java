package com.telepathicgrunt.structurevoidtoggle.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksInit
{
	//Lazy loaded instance of the block. Use Structure_Void.get() to get the block
    public static final DeferredRegister<Block> BLOCKS_VANILLA_REPLACE = new DeferredRegister<>(ForgeRegistries.BLOCKS, "minecraft");
    public static final RegistryObject<Block> STRUCTURE_VOID = BLOCKS_VANILLA_REPLACE.register("structure_void",
            () -> new StructureVoidBlock()
    );
    
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		//registry replace the structure void block in vanilla
        event.getRegistry().register(new StructureVoidBlock().setRegistryName(new ResourceLocation("minecraft", "structure_void")));
	}
}
