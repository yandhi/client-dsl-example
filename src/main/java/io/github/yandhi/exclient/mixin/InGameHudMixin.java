package io.github.yandhi.exclient.mixin;

import io.github.yandhi.exclient.ClientModKt;
import io.github.yandhi.exclient.events.RenderInGameHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", shift = At.Shift.BEFORE))
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        ClientModKt.getClient().getEventBus().publish(new RenderInGameHud(matrices, tickDelta));
    }
}
