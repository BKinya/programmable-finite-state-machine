package org.example

class StateBuilder {
    val transitions = mutableMapOf<InputSymbol, StateName>()

    fun transition(input: String, to: String){
        val inputSymbol = InputSymbol(input)
        val nextStateName = StateName(to)
        transitions[inputSymbol] = nextStateName
    }
}