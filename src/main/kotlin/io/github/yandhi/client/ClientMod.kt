package io.github.yandhi.client

import io.github.yandhi.client.plugin.plugin
import io.github.yandhi.client.plugin.toggle

/**
 * our client.
 */
lateinit var client: Client

/**
 * entry method for fabric.
 */
fun init() {
    client = client(label = "ex client", version = "v0.0.1") {
        plugin(label = "Hud", it) {

        }
        toggle(label = "Fullbright", it) {

        }
    }
}