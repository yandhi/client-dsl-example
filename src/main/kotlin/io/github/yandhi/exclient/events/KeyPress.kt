package io.github.yandhi.exclient.events

import io.github.yandhi.client.event.Event

/**
 * Called on key presses.
 */
data class KeyPress(val key: Int) : Event()