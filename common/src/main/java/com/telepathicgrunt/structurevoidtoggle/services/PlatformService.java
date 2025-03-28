package com.telepathicgrunt.structurevoidtoggle.services;

import com.telepathicgrunt.structurevoidtoggle.utils.GeneralUtils;

public interface PlatformService {
    PlatformService INSTANCE = GeneralUtils.loadService(PlatformService.class);
}
