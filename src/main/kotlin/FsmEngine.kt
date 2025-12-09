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
    fun processInput(input: String): OutputSymbol? {
        val inputSymbol = InputSymbol(input)

        val transition = currentState.transitionTable.entries[inputSymbol]
        return if (transition != null) {
            println("Transitioned from ${currentStateName.value} on input $input to ${transition.stateName.value}")
            currentStateName = transition.stateName
            transition.outputSymbol
        } else {
            println("No transition from ${currentStateName.value} for input $input No state changes")
            null
        }
    }

    fun  processSequenceInput(inputSequence: String): String{
        var result = ""
        for(char in inputSequence){
           result+= processInput(char.toString())?.value
        }
        return result
    }

    fun isFinished() = currentState.isFinal
}