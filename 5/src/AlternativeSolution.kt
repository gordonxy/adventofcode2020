import java.util.*
import kotlin.math.max

/** DISCLAIMER:
 * Sadly, I did not come up with this myself, but it is way easier to just map F, L and B, R to 0 and 1 respectively
 * and interpret it as an Integer in its binary form.
 */

fun main() {
    println(getHighestSeatIdAlt(inputString))
    println(findMySeatIdAlt(inputString))
}

fun getHighestSeatIdAlt(inputString: String) : Int {
    return inputString.lines().map { line ->
        line.reversed().map { char ->
            when(char) {
                'F', 'L' -> 0
                'B', 'R' -> 1
                else -> throw InputMismatchException("Found a wrong character in the input line: $char")
            }
        }.reduceIndexed { bitCount, acc, bit ->
            acc.or(bit.shl(bitCount))
        }
    }.reduce { acc, id ->
        max(acc, id)
    }
}

fun findMySeatIdAlt(inputString : String) : Int {
    inputString.lines().map { line ->
        line.reversed().map { char ->
            when(char) {
                'F', 'L' -> 0
                'B', 'R' -> 1
                else -> throw InputMismatchException("Found a wrong character in the input line: $char")
            }
        }.reduceIndexed { bitCount, acc, bit ->
            acc.or(bit.shl(bitCount))
        }
    }.sorted().zipWithNext {
        firstId, secondId ->
        if(secondId == firstId + 2) return firstId + 1
    }
    return -1
}