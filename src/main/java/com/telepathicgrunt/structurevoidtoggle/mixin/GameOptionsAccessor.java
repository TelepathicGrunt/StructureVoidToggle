package com.telepathicgrunt.structurevoidtoggle.mixin;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface GameOptionsAccessor {
    @Mutable
    @Accessor
    void setAllKeys(KeyBind[] keysAll);
}
