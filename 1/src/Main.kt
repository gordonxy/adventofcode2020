fun main() {
    val numbers = inputString.lines().map { it.toInt() }.sorted()

    println(numbers)

    run { // 2020 sum part one (sum of tuple)
        val result = findPairThatSumsToTarget(numbers)!!
        println("$result: ${result.first * result.second}")
    }

    run { // 2020 sum part two (sum of triple)
        val result = findTripleThatSumsToTarget(numbers)!!
        println("$result: ${result.first * result.second * result.third}")
    }
}

fun findPairThatSumsToTarget(numbers : List<Int>, target : Int = 2020) : Pair<Int, Int>? {
    var start = 0
    var end = numbers.lastIndex
    while(start < end) {
        val startVal = numbers[start]
        val endVal = numbers[end]

        if(startVal > target || endVal > target) return null
        if(startVal + endVal == target) return startVal to endVal

        if(startVal + endVal < target)  start++
        if(startVal + endVal > target)  end--
    }
    return null
}

fun findTripleThatSumsToTarget(numbers : List<Int>, target : Int = 2020) : Triple<Int, Int, Int>? {
    var third = numbers.lastIndex
    while(third >= 0) {
        val remainder = target - numbers[third]
        val pair = findPairThatSumsToTarget(numbers.subList(0, third-1), remainder)
        if(pair != null) {
            return Triple(pair.first, pair.second, numbers[third])
        }
        third--
    }
    return null
}