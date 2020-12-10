import kotlin.math.abs

data class Program (val code: List<Operation>, var programCounter: Int = 0, var accumulator: Int = 0) {
    companion object {
        fun parse(input: String): Program {
            return Program(
                code = input.lines().map { line ->
                    val splitOnSpace = line.split(' ')
                    Operation(splitOnSpace[0], splitOnSpace[1].toInt())
                }
            )
        }
    }

    private fun executeCurrentOperation() {
        code[programCounter].apply {
            when(opCode) {
                "nop" -> programCounter++
                "acc" -> {
                    accumulator += attribute
                    programCounter++
                }
                "jmp" -> programCounter += attribute
            }
            executionCounter++
        }
    }

    fun changeBiggestNegativeJumpThatCantBeJumpedToNop(): Program {
        var currentBiggestNegativeJumpPC: Int = 0
        var currentBiggestNegativeJumpVal: Int = 0
        var pc = code.size - 1
        var found = false
        while(pc >= 0) {
            code[pc].takeIf { it.opCode == "jmp" }?.let { op ->
                if(op.attribute > 0 && op.attribute < abs(currentBiggestNegativeJumpVal)) {
                    found = true
                } else if(op.attribute < 0 && op.attribute < currentBiggestNegativeJumpVal) {
                    currentBiggestNegativeJumpPC = pc
                    currentBiggestNegativeJumpVal = op.attribute
                }
            }
            if(found) break
            pc--
        }

        val newCode = code.toMutableList()
        newCode[pc] = Operation("nop", currentBiggestNegativeJumpVal)
        return Program(newCode)
    }

    fun getAccumulatorWhenOperationIsExecutedTwice(): Int {
        var currentOp = code[programCounter]
        while(true) {
            val accumulatorBefore = accumulator
            executeCurrentOperation()
            if(currentOp.executionCounter == 2) return accumulatorBefore
            currentOp = code[programCounter]
        }
        return accumulator
    }

    fun getAccumulatorAfterExecution(): Int {
        while(programCounter < code.size) {
            executeCurrentOperation()
        }
        return accumulator
    }
}

data class Operation(val opCode: String, val attribute: Int, var executionCounter: Int = 0)

fun main() {
    val program = Program.parse(inputString)
    println(program.toString())
    println(program.getAccumulatorWhenOperationIsExecutedTwice())
    val breakLoopProgram = program.changeBiggestNegativeJumpThatCantBeJumpedToNop()
    println(breakLoopProgram.getAccumulatorAfterExecution())
}
