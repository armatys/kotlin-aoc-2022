fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val figures = line.split(" ", limit = 2).toPair(String::toFigure, String::toFigure)
            acc + figures.matchScore()
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val guide = line.split(" ", limit = 2)
                .toPair(String::toFigure, String::toOutcome)
            val figures = guide.first to guide.pickSecondFigure()
            acc + figures.matchScore()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private fun <T, U, W> List<T>.toPair(firstTransform: (T) -> U, secondTransform: (T) -> W): Pair<U, W> =
    firstTransform(first()) to secondTransform(last())

private fun String.toFigure(): Figure = when (this) {
    "A", "X" -> Figure.Rock
    "B", "Y" -> Figure.Paper
    "C", "Z" -> Figure.Scissors
    else -> error("Unknown figure: $this")
}

private fun String.toOutcome(): Outcome = when (this) {
    "X" -> Outcome.Loss
    "Y" -> Outcome.Draw
    "Z" -> Outcome.Win
    else -> error("Unknown outcome: $this")
}

private infix fun Figure.against(other: Figure): Int {
    if (this == other) return 3
    return when (this) {
        Figure.Rock -> if (other == Figure.Scissors) 6 else 0
        Figure.Paper -> if (other == Figure.Rock) 6 else 0
        Figure.Scissors -> if (other == Figure.Paper) 6 else 0
    }
}

private fun Figure.shapeScore(): Int = when (this) {
    Figure.Rock -> 1
    Figure.Paper -> 2
    Figure.Scissors -> 3
}

private fun Pair<Figure, Figure>.matchScore(): Int {
    return (second against first) + second.shapeScore()
}

private fun Pair<Figure, Outcome>.pickSecondFigure(): Figure {
    if (second == Outcome.Draw) return first
    return when (first) {
        Figure.Rock -> if (second == Outcome.Win) Figure.Paper else Figure.Scissors
        Figure.Paper -> if (second == Outcome.Win) Figure.Scissors else Figure.Rock
        Figure.Scissors -> if (second == Outcome.Win) Figure.Rock else Figure.Paper
    }
}

private enum class Figure { Rock, Paper, Scissors }
private enum class Outcome { Loss, Draw, Win }
