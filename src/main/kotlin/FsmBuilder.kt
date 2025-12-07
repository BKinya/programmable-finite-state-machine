package org.example

import java.lang.IllegalStateException

fun defineFSM(name: String, block: FsmBuilder.() -> Unit): FiniteStateMachine {
    val builder = FsmBuilder(name)
    builder.block()
    return builder.build()
}

class FsmBuilder(val name: String) {
    private val states = mutableMapOf<StateName, State>()
    private var initialStateName: StateName? = null

    fun initialState(name: String) {
        initialStateName = StateName(name)
    }

    fun state(name: String, isFinal: Boolean, build: StateBuilder.() -> Unit) {
        val stateName = StateName(name)

        val stateBuilder = StateBuilder()
        stateBuilder.build()

        val transitionTable = TransitionTable(
            entries = stateBuilder.transitions.toMap()
        )
        val state = State(transitionTable = transitionTable, isFinal = isFinal)
        states[stateName] = state

    }

    fun build(): FiniteStateMachine {
        val finalInitialStateName =
            initialStateName ?: throw IllegalStateException("Initial state must be defined using initalState()")
        require(states.containsKey(initialStateName)) { "Initial state $finalInitialStateName must be defined in states" }
        return FiniteStateMachine(name, finalInitialStateName, states.toMap())
    }
}