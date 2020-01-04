package com.telepathicgrunt.structurevoidtoggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import com.telepathicgrunt.structurevoidtoggle.blocks.BlocksInit;
import com.telepathicgrunt.structurevoidtoggle.networking.MessageHandler;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(StructureVoidToggleMain.MODID)
public class StructureVoidToggleMain
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "structure_void_toggle";


	public StructureVoidToggleMain()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		MinecraftForge.EVENT_BUS.register(this);
	}


	private void setup(final FMLCommonSetupEvent event)
	{
		//set up packet handling between client and server for auto-placing mode
		MessageHandler.init();
	}


	private void doClientStuff(final FMLClientSetupEvent event)
	{
		//add our own keybind for backtick (`) for toggling structure void hitboxes to vanilla
		KeyBinding[] kb = Minecraft.getInstance().gameSettings.keyBindings;
        List<KeyBinding> arrlist = new ArrayList<KeyBinding>(Arrays.asList(kb)); 
        arrlist.add(ToggleBehavior.keyBindStructureVoidToggle);
        Minecraft.getInstance().gameSettings.keyBindings = arrlist.toArray(kb);
	}

	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
		{
			//registry replace structure void.
			//This is safe to do as vanilla has no static instance of structure void like it does with Dirt and stuff.
			BlocksInit.registerBlocks(blockRegistryEvent);
		}
	}
}
