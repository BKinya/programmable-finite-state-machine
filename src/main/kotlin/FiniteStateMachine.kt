package org.example

data class FiniteStateMachine(
    val name: String,
    val initialStateName: StateName,
    val states: Map<StateName, State>
)

data class State(
    /* Name of the state must be unique within the FSM */
    val transitionTable: TransitionTable,
    val isFinal: Boolean
)

data class Transition(
    val stateName: StateName,
    val outputSymbol: OutputSymbol
)

data class TransitionTable(
    val entries: Map<InputSymbol, Transition>
)

@JvmInline value class StateName(val value: String)

@JvmInline value class InputSymbol(val value: String)

@JvmInline value class OutputSymbol(val value: String)