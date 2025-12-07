package org.example

import java.lang.IllegalStateException

class FsmEngine(
    private val fsm: FiniteStateMachine
) {
    var currentStateName: StateName = fsm.initialStateName
        private set // Only the engine can set the state internally

    // Helper to get the current state object from the states map
    private val currentState: State
        get() = fsm.states[currentStateName]
            ?: throw IllegalStateException("FSM is in unknown state: $currentStateName")

    /**
     * process input and attempt a transition
     * @param input symbol e.g. "0" or "1"
     * @return true if transition happened, false otherwise
     */
    fun processInput(input: String): Boolean {
        val inputSymbol = InputSymbol(input)

        val nextStateName = currentState.transitionTable.entries[inputSymbol]
        return if (nextStateName != null) {
            println("Transitioned from ${currentStateName.value} on input $input to ${nextStateName.value}")
            currentStateName = nextStateName
            true
        } else {
            println("No transition from ${currentStateName.value} for input $input No state changes")
            false
        }
    }

    fun  processSequenceInput(inputSequence: String): StateName{
        for(char in inputSequence){
            processInput(char.toString())
        }
        return currentStateName
    }

    fun isFinished() = currentState.isFinal
}