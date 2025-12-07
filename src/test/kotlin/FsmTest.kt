import org.example.FiniteStateMachine
import org.example.FsmEngine
import org.example.defineFSM
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class FsmTest {
    private lateinit var fsm: FiniteStateMachine

    @BeforeTest
    fun setUp() {
        fsm = defineFSM("TestFSm") {
            initialState("Qempty")
            state("Qempty", isFinal = true) {
                transition("0", "Qone")
                transition("1", "Qtwo")
            }
            state("Qone", isFinal = true) {
                transition("0", "Qdead")
                transition("1", "Qtwo")
            }
            state("Qtwo", isFinal = true) {
                transition("0", "Qone")
                transition("1", "Qdead")
            }
            state("Qdead", isFinal = false) {
                transition("0", "Qdead")
                transition("1", "Qdead")
            }
        }
    }

    @ParameterizedTest
    @MethodSource("fsmInputOutput")
    fun `given a valid input should map to correct state`(
        input: String,
        expectedCurrentStateName: String,
    ) {
        val fsmEngine = FsmEngine(fsm)
        val result = fsmEngine.processSequenceInput(input)
        assertEquals(
            expectedCurrentStateName, fsmEngine.currentStateName.value,
            "Sequence $input should end in $expectedCurrentStateName "
        )
    }


    companion object {
        @JvmStatic
        fun fsmInputOutput() = Stream.of(
            Arguments.of("0", "Qone"),
            Arguments.of("1", "Qtwo"),
            Arguments.of("01", "Qtwo"),
            Arguments.of("10", "Qone"),
            Arguments.of("00", "Qdead"),
            Arguments.of("11", "Qdead")
        )
    }
}