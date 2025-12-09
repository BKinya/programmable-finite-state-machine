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
                transition("0", "Qone", "1")
                transition("1", "Qtwo", "0")
            }
            state("Qone", isFinal = true) {
                transition("0", "Qdead", "1")
                transition("1", "Qtwo", "0")
            }
            state("Qtwo", isFinal = true) {
                transition("0", "Qone", "1")
                transition("1", "Qdead", "0")
            }
            state("Qdead", isFinal = false) {
                transition("0", "Qdead", "1")
                transition("1", "Qdead", "0")
            }
        }
    }

    @ParameterizedTest
    @MethodSource("fsmInputOutput")
    fun `given a valid input should map to correct state`(
        input: String,
        expectedCurrentStateName: String,
        expectedResult: String
    ) {
        val fsmEngine = FsmEngine(fsm)
        val result = fsmEngine.processSequenceInput(input)
        assertEquals(
            expectedCurrentStateName, fsmEngine.currentStateName.value,
            "Sequence $input should end in $expectedCurrentStateName "
        )
        assertEquals(expectedResult, result, "input  $input should produce output $expectedResult")
    }


    companion object {
        @JvmStatic
        fun fsmInputOutput() = Stream.of(
            Arguments.of("0", "Qone", "1"),
            Arguments.of("1", "Qtwo", "0"),
            Arguments.of("01", "Qtwo", "10"),
            Arguments.of("10", "Qone", "01"),
            Arguments.of("00", "Qdead", "11"),
            Arguments.of("11", "Qdead", "00")
        )
    }
}