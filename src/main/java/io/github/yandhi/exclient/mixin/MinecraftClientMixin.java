package io.github.yandhi.exclient.mixin;

import io.github.yandhi.exclient.ClientModKt;
import io.github.yandhi.exclient.events.KeyPress;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    @Final
    private Window window;

    @Shadow
    @Nullable
    public Screen currentScreen;

    @Shadow
    @Final
    public Keyboard keyboard;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(RunArgs args, CallbackInfo ci) {
        GLFW.glfwSetKeyCallback(window.getHandle(), ((window, code, scancode, action, mods) -> {
            KeyPress keyPress = new KeyPress(code);

            if (currentScreen == null && action == GLFW.GLFW_PRESS) {
                ClientModKt.getClient().getEventBus().publish(keyPress);
            }

            if (!keyPress.getCancelled()) {
                keyboard.onKey(window, code, scancode, action, mods);
            }
        }));
    }
}
