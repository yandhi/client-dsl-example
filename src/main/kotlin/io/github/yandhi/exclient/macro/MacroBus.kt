package io.github.yandhi.exclient.macro

/**
 * Handles actions for macros and the creation of them.
 *
 * @author yandhi
 * @since 2/3/2022
 */
object MacroBus {

    /**
     * The macros currently in the bus.
     */
    val macros = mutableSetOf<Macro>()

    /**
     * Registers a macro with just a key and action provided.
     *
     * @param key the key we want to register the action to.
     * @param action The action we want to execute.
     */
    fun registerMacro(key: String, action: () -> Unit) {
        for (macro in macros) {
            if (macro.key == key) {
                addActionToMacro(macro, action)

                return
            }
        }

        macros.add(Macro(key, arrayListOf(action)))
    }

    /**
     * Registers a macro to the macros list.
     */
    fun registerMacro(macro: Macro) {
        if (!macros.contains(macro)) {
            macros.add(macro)
        }
    }

    /**
     * Adds an action to the current actions shown on that macro.
     */
    private fun addActionToMacro(macro: Macro, action: () -> Unit) {
        macro.actions.add(action)
    }
}

/**
 * Lets us create a macro easily with a simple action using a basic dsl.
 */
fun macro(key: String, lambda: () -> Unit) {
    MacroBus.registerMacro(key, lambda)
}