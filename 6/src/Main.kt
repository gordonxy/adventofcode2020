import java.util.BitSet

private const val AasInt = 97

fun main() {
    // part one
    inputString.split("\n\n").map { group ->
        group.split(" ", "\n").map { person ->
            val bitSet = BitSet().apply {
                person.map { it.toInt() - AasInt }
                    .forEach { index ->
                        this.set(index)
                    }
            }
            bitSet
        }.reduce { acc, bitSet ->
            val new = BitSet()
            new.or(acc)
            new.or(bitSet)
            new
        }.let {
            var count = 0
            for(bitIndex in 0..31) if(it.get(bitIndex)) count++
            count
        }
    }.reduce { acc, count ->
        acc + count
    }.let {
        println(it)
    }

    // part two
    inputString.split("\n\n").map { group ->
        group.split(" ", "\n").let { persons ->
            var count = 0
            for(character in 'a'..'z') {
                if(persons.filter { it.contains(character) }.count() == persons.size) {
                    count++
                }
            }
            count
        }
    }.sum().let {
        println(it)
    }

    // part two alternative
    inputString.split("\n\n").map { group ->
        val collectBitSet = BitSet()
        collectBitSet.set(0, 31)
        group.split(" ", "\n").map { person ->
            val bitSet = BitSet().apply {
                person.map { it.toInt() - AasInt }
                    .forEach { index ->
                        this.set(index)
                    }
            }
            bitSet
        }.forEach {
            collectBitSet.and(it)
        }
        var count = 0
        for(bitIndex in 0..31) if(collectBitSet.get(bitIndex)) count++
        count
    }.reduce { acc, count ->
        acc + count
    }.let {
        println(it)
    }
}