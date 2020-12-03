data class AOCMap(
    val map : List<String>,
    val width : Int = map.first().length,
    val height : Int = map.size,
) {
    fun isTreeAtIndex(index : Index) : Boolean {
        return map[index.vertical][index.horizontal % width] == '#'
    }
}

data class Index(val slope: Slope = Slope(0, 0),
                 var horizontal : Int = slope.deltaH,
                 var vertical: Int = slope.deltaV) {
    fun update(slope : Slope) {
        horizontal += slope.deltaH
        vertical += slope.deltaV
    }
}

data class Slope(val deltaH : Int, val deltaV : Int)

fun main() {
    val map = AOCMap(inputString.lines())

    val partOne = Slope(3, 1)

    val partTwoList = listOf(Slope(1, 1),
                            partOne,
                            Slope(5, 1),
                            Slope(7, 1),
                            Slope(1, 2))

    val result = findAmountOfTreesTraversedWithSlope(map, partOne)
    println(result)

    val resultTwo = partTwoList.map { scope -> findAmountOfTreesTraversedWithSlope(map, scope).toLong() }.reduce { acc, i -> acc * i }
    println(resultTwo)
}

fun findAmountOfTreesTraversedWithSlope(map : AOCMap, slope : Slope) : Int {
    var treeCount = 0
    val index = Index(slope)
    while(index.vertical < map.height) {
        if(map.isTreeAtIndex(index)) treeCount++
        index.update(slope)
    }
    return treeCount
}

