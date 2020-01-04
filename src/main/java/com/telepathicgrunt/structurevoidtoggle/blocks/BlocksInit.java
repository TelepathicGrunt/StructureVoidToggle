package com.telepathicgrunt.structurevoidtoggle.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksInit
{
    public static final DeferredRegister<Block> BLOCKS_VANILLA_REPLACE = new DeferredRegister<>(ForgeRegistries.BLOCKS, "minecraft");
    
    public static final RegistryObject<Block> STRUCTURE_VOID = BLOCKS_VANILLA_REPLACE.register("structure_void",
            () -> new StructureVoidBlock()
    );
    
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
        event.getRegistry().register(new StructureVoidBlock().setRegistryName(new ResourceLocation("minecraft", "structure_void")));
	}
}
