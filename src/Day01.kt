fun main() {
    fun part1(input: List<String>): Int {
        return mostCalories(input)
    }

    fun part2(input: List<String>): Int {
        return mostCalories(input, topN = 3)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

private fun mostCalories(lines: List<String>, topN: Int = 1): Int {
    return lines
        .map { it.toIntOrNull() }
        .delimitedByNull()
        .fold(listOf<Int>()) { acc, calories ->
            (acc + calories.sum()).sortedDescending().take(topN)
        }.sum()
}

private fun <T> List<T?>.delimitedByNull(): Sequence<Sequence<T>> = sequence {
    var i = 0
    while (i <= lastIndex) {
        yield(generateSequence {
            getOrNull(i).also { i += 1 }
        })
    }
}
