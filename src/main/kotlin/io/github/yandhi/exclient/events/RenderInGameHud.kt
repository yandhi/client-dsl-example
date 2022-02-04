package io.github.yandhi.exclient.events

import io.github.yandhi.client.event.Event
import net.minecraft.client.util.math.MatrixStack

/**
 * Called when the game renders the hud.
 */
data class RenderInGameHud(val stack: MatrixStack, val renderTicks: Float) : Event()
