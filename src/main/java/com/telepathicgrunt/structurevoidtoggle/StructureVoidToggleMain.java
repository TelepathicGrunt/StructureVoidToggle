package com.telepathicgrunt.structurevoidtoggle;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(StructureVoidToggleMain.MODID)
public class StructureVoidToggleMain {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "structure_void_toggle";


	public StructureVoidToggleMain() {
		if(FMLEnvironment.dist == Dist.CLIENT) {
			StructureVoidToggleClient.onInitializeClient();
		}
	}
}
