package io.github.yandhi.exclient.macro

/**
 * Binds an action to a button.
 *
 * @author yandhi
 * @since 2/3/2022
 */
data class Macro(val key: String, val actions: ArrayList<() -> Unit>) {

    /**
     * Executes all the actions that the macro system handles.
     */
    fun executeActions() {
        actions.forEach { it.invoke() }
    }
}