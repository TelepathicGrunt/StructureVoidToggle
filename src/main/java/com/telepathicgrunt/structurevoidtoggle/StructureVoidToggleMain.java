package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(StructureVoidToggleMain.MODID)
public class StructureVoidToggleMain {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "structure_void_toggle";


	public StructureVoidToggleMain() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> StructureVoidToggleClient::onInitializeClient);

		MinecraftForge.EVENT_BUS.addListener(ToggleBehavior::renderEvent);
	}
}
