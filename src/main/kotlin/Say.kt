import kotlin.math.pow

class NumberSpeller {

    private val powOfThousand =
            listOf("unit", "thousand", "million", "billion")

    private val tens =
            listOf("zero", "ten", "twenty", "thirty", "forty",
                    "fifty", "sixty", "seventy", "eighty", "ninety")

    private val teens =
            listOf("ten", "eleven", "twelve", "thirteen", "fourteen",
                    "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")

    private val ones =
            listOf("zero", "one", "two", "three", "four",
                    "five", "six", "seven", "eight", "nine")

    fun say(input: Long): String {

        require(input in 0 until 1000.pow(powOfThousand.size))

        return powOfThousand.mapIndexed {
            index, s -> s to input[3 * index until 3 * (index + 1)]
                .let { if (it > 0 && index > 0) "${say(it)} $s" else "" }
        }.reversed().dropLast(1).toMap().plus(
                mapOf(
                    "hundred".let { it to if (input[2] > 0) "${say(input[2])} $it" else "" },
                    "tens".let { it to if (input[1] > 1) tens[input[1]] else "" },
                    "hyphen".let { it to if (input[1] > 1 && input[0] > 0) "-" else "" },
                    "teens".let { it to if (input[1] == 1) teens[input[0]] else "" },
                    "ones".let { it to if (input[1] != 1 && input[0] > 0) ones[input[0]] else "" },
                    "zero".let { it to if (input == 0L) ones[0] else "" }
                )
        ).values.filter { it != "" }.joinToString(" ").replace(" - ", "-")
    }

    private fun Int.pow(n: Int): Long =
            this.toDouble().pow(n).toLong()

    private operator fun Long.get(intRange: IntRange): Int =
            (this % 10.pow(intRange.last + 1) / 10.pow(intRange.first)).toInt()

    private operator fun Long.get(n: Int): Int = this[IntRange(n, n)]

    private fun say(input: Int): String = say(input.toLong())
}
