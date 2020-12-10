fun main() {
    val invalidNumber = findFirstInvalidNumber(inputString).apply { println(this) }
    findSumOfContiguousSetThatAddsToInvalidNumber(inputString, invalidNumber).apply { println(this) }
}

fun findFirstInvalidNumber(inputString: String, subListLength: Int = 25): Long {
    inputString.lines().map { it.toLong() }.let { numbers ->
        var addends = numbers.subList(0, subListLength)
        for ((index, number) in numbers.subList(subListLength, numbers.size).withIndex()) {
            if(!number.isSumOfTwoAddends(addends)) return number
            addends = numbers.subList(index + 1, index + subListLength + 1)
        }
    }
    return -1
}

private fun Long.isSumOfTwoAddends(addends: List<Long>): Boolean {
    addends.map { first -> addends.map { second -> first + second } }.flatten().find { it == this }?.let {
        return true
    }
    return false
}

fun findSumOfContiguousSetThatAddsToInvalidNumber(inputString: String, target: Long): Long {
    inputString.lines().map { it.toLong() }.let { numbers ->
        numbers.forEachIndexed { index, number ->
            for(endIndex in index until numbers.size) {
                numbers.subList(index, endIndex).let {
                    if(it.sum() == target) {
                        it.sorted().apply {
                            return first() + last()
                        }
                    }
                }
            }
        }
    }
    return -1
}
