package com.telepathicgrunt.structurevoidtoggle.mixin;

import com.telepathicgrunt.structurevoidtoggle.behaviors.ToggleBehavior;
import net.minecraft.client.KeyboardListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(KeyboardListener.class)
public class KeyboardMixin {

    @Inject(method = "keyPress",
            at = @At(value = "TAIL"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void keyPress(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        ToggleBehavior.toggle(key);
    }
}