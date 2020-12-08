data class DependencyGraph(
    val dependencies : MutableMap<String, MutableMap<String, Int>> = mutableMapOf(),
)

fun main() {
    val dependencyGraph = buildDependencyGraph(inputString)
    println(dependencyGraph.dependencies.toString())
    println(dependencyGraph.findDistinctAmountOfDependentBags("shiny gold"))

    val inverseDependencyGraph = buildInverseDependencyGraph(inputString)
    println(inverseDependencyGraph.dependencies.toString())
    println(inverseDependencyGraph.findAmountOfDependentBags("shiny gold"))
}

fun buildDependencyGraph(inputString: String): DependencyGraph  {
    return DependencyGraph().apply {
        inputString.lines().forEach { line ->
            val containSplit = line.split(" contain ")
            val containerBag = containSplit[0].split(" bag")[0]
            val containedBags = containSplit[1].removeSuffix(".").split(", ")
            if(containedBags.size == 1 && containedBags.first() == "no other bags") {
                dependencies["no other bags"]?.let {
                    it[containerBag] = 1
                } ?: run {
                    dependencies["no other bags"] = mutableMapOf(containerBag to 1)
                }
            } else {
                containedBags.map {
                        bagString ->  bagString.split(" ", limit = 2).let { it[1].split(" bag")[0] to it[0].toInt() }
                }.forEach { (identifier, amount) ->
                    mutableMapOf(containerBag to amount).let { newMapPair ->
                        dependencies[identifier]?.putAll(newMapPair) ?: run {
                            dependencies[identifier] = newMapPair
                        }
                    }
                }
            }
        }
    }
}

fun buildInverseDependencyGraph(inputString: String): DependencyGraph  {
    return DependencyGraph().apply {
        inputString.lines().forEach { line ->
            val containSplit = line.split(" contain ")
            val containerBag = containSplit[0].split(" bag")[0]
            val containedBags = containSplit[1].removeSuffix(".").split(", ")
            if(containedBags.size == 1 && containedBags.first() == "no other bags") {
                dependencies[containerBag]?.let {
                    it["no other bags"] = 1
                } ?: run {
                    dependencies[containerBag] = mutableMapOf("no other bags" to 1)
                }
            } else {
                containedBags.map {
                    bagString ->  bagString.split(" ", limit = 2).let { it[1].split(" bag")[0] to it[0].toInt() }
                }.forEach { (identifier, amount) ->
                    mutableMapOf(identifier to amount).let { newMapPair ->
                        dependencies[containerBag]?.putAll(newMapPair) ?: run {
                            dependencies[containerBag] = newMapPair
                        }
                    }
                }
            }
        }
    }
}

/* tried to reduce graph first, but won't work, gave up
fun DependencyGraph.resolveDependencies(): DependencyGraph {
    var solveThese = mutableListOf("no other bags" to 1)
    while(solveThese.isNotEmpty()) {
        var nextSolve = mutableListOf<Pair<String, Int>>()
        solveThese.forEach { (bagId, bagCount) ->
            dependencies[bagId]?.let {
                it.replaceAll { _, currentCount ->
                    bagCount * currentCount
                }
                nextSolve.addAll(it.toList())
            }

        }
        solveThese = nextSolve
    }
    return this
}*/

fun DependencyGraph.findDistinctAmountOfDependentBags(bagName : String): Int {
    return dependencies[bagName]?.map { (name, _) -> findDistinctDependentBags(name) }?.flatten()?.distinct()?.size ?: 0
}

fun DependencyGraph.findDistinctDependentBags(bagName : String): List<String> {
    return listOf(listOf(bagName), dependencies[bagName]?.map { (name, _) -> findDistinctDependentBags(name) }?.flatten() ?: emptyList()).flatten()
}

fun DependencyGraph.findAmountOfDependentBags(bagName : String): Int {
    return dependencies[bagName]?.let {
        it.map { (name, amount) ->  recFindAmountOfDependentBags(name, amount) }.reduce { acc, value -> acc + value }
    }?: 0
}

fun DependencyGraph.recFindAmountOfDependentBags(bagName : String, bagAmount : Int): Int {
    return dependencies[bagName]?.let {
        bagAmount + bagAmount * it.map { (name, amount) ->  recFindAmountOfDependentBags(name, amount) }.reduce { acc, value -> acc + value }
    }?: 0
}