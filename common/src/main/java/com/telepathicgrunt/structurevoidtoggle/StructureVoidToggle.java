package com.telepathicgrunt.structurevoidtoggle;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureVoidToggle {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "structure_void_toggle";

    public static void StructureVoidToggleInit() {
        ToggleBehavior.registerKeyMappingCategory();
    }
}