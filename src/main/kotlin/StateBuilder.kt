package org.example

class StateBuilder {
    val transitions = mutableMapOf<InputSymbol, Transition>()

    fun transition(input: String, to: String, output: String) {
        val inputSymbol = InputSymbol(input)
        val nextStateName = StateName(to)
        val outputSymbol = OutputSymbol(output)
        val transition = Transition(nextStateName, outputSymbol)
        transitions[inputSymbol] = transition
    }
}