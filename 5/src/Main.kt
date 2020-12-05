import kotlin.math.max

fun binaryPartition(max : Int, lowerChar : Char, upperChar : Char, input : String) : Int {
    var range = 0..max
    for(char in input) {
        when(char) {
            lowerChar -> range = range.first..(range.first + (range.last - range.first)/2)
            upperChar -> range = (range.first + (range.last - range.first)/2 + 1)..range.last
        }
    }
    return range.first
}

fun main() {
    println(getHighestSeatId(inputString))
    println(findMySeatId(inputString))
}

fun getHighestSeatId(inputString : String) : Int {
    var currentMaxSeatId = 0
    inputString.lines().forEach { line ->
        val row = binaryPartition(127, 'F', 'B', line.substring(0,7))
        val column = binaryPartition(7, 'L', 'R', line.substring(7))
        currentMaxSeatId = max(currentMaxSeatId, row * 8 + column)
    }
    return currentMaxSeatId
}

fun findMySeatId(inputString : String) : Int {
    inputString.lines().map { line ->
        val row = binaryPartition(127, 'F', 'B', line.substring(0,7))
        val column = binaryPartition(7, 'L', 'R', line.substring(7))
        row * 8 + column
    }.sorted().zipWithNext {
        firstId, secondId ->
        if(secondId == firstId + 2) return firstId + 1
    }
    return -1
}