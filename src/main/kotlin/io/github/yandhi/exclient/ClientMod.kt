package io.github.yandhi.exclient

import io.github.yandhi.client.Client
import io.github.yandhi.client.client
import io.github.yandhi.client.event.listener
import io.github.yandhi.exclient.events.RenderInGameHud
import io.github.yandhi.client.plugin.*
import io.github.yandhi.exclient.events.KeyPress
import io.github.yandhi.exclient.macro.MacroBus
import io.github.yandhi.exclient.macro.macro
import net.minecraft.client.MinecraftClient
import org.lwjgl.glfw.GLFW
import java.awt.Color
import java.util.logging.Logger

/**
 * our client.
 */
lateinit var client: Client

/**
 * entry method for fabric.
 */
fun init() {
    client = client(label = "ex client", version = "v0.0.1") { ex ->
        plugin(label = "Hud", ex) {
            val renderInGameHud = listener<RenderInGameHud> {
                val textRenderer = MinecraftClient.getInstance().textRenderer

                textRenderer.drawWithShadow(
                    it.stack, ex.label + " " + ex.version, 4f,
                    4f, Color.WHITE.rgb
                )

                var y = 8f + textRenderer.fontHeight

                for (plugin in ex.plugins.getPlugins().values) {
                    if (plugin is TogglePlugin && plugin.running) {
                        textRenderer.drawWithShadow(
                            it.stack, plugin.label, 4f, y,
                            Color.LIGHT_GRAY.rgb
                        )
                        y += MinecraftClient.getInstance().textRenderer.fontHeight + 2f
                    }
                }
            }
            ex.eventBus.subscribe(renderInGameHud)
        }
        plugin("Macros", ex) {
            val keyPress = listener<KeyPress> { press ->
                MacroBus.macros.stream()
                    .filter { it.key.equals(GLFW.glfwGetKeyName(press.key, 0), true) }
                    .forEach { it.executeActions() }
            }

            ex.eventBus.subscribe(keyPress)
        }
        toggle(label = "Fullbright", ex) {
            /**
             * Our old gamma value.
             */
            var oldGamma = 0.0

            macro("b") { it.toggle() }

            enable(it) {
                oldGamma = MinecraftClient.getInstance().options.gamma
                MinecraftClient.getInstance().options.gamma = 16.0
            }
            disable(it) {
                MinecraftClient.getInstance().options.gamma = oldGamma
            }
        }
        Logger.getGlobal().info("Created client: " + ex.label + " " + ex.version)
    }
}