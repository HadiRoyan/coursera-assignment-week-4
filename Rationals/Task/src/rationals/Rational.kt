package rationals

import java.math.BigInteger

class Rational (val n: BigInteger, val d: BigInteger) : Comparable<Rational> {

    fun normalize(): Rational {
        val a = n.gcd(d)

        if (n == BigInteger.ZERO) return this

        return if (d < BigInteger.ZERO ) {
            -Rational(n/a, (d/a).abs())
        } else {
            Rational(n/a, (d/a).abs())
        }
    }

    operator fun minus(num: Rational) : Rational = (n.times(num.d).minus(num.n.times(d))).divBy(num.d.times(d)).normalize()

    operator fun plus(num: Rational) : Rational = (n.times(num.d).plus(num.n.times(d))).divBy(num.d.times(d)).normalize()

    operator fun times(num: Rational): Rational = (n.times(num.n).divBy(num.d.times(d))).normalize()

    operator fun div(num: Rational): Rational = (n.times(num.d).divBy(d.times(num.n))).normalize()

    operator fun unaryMinus(): Rational = Rational(-n, d)

    override fun equals(other: Any?): Boolean {
        when(other) {
            is Rational -> return this.n == other.n && this.d == other.d
            else -> return super.equals(other)
        }
    }

    override fun compareTo(other: Rational): Int {
        return n.times(other.d).compareTo(other.n.times(d))
    }

    override fun toString(): String {

        return when (d) {
            BigInteger.ONE -> n.toString()
            else -> "$n/$d"
        }
    }


}

infix fun Int.divBy(num: Int) : Rational = Rational(toBigInteger(), num.toBigInteger()).normalize()

infix fun Long.divBy(num: Long): Rational = Rational(toBigInteger(), num.toBigInteger()).normalize()

infix fun BigInteger.divBy(num: BigInteger): Rational = Rational(this, num).normalize()

fun String.toRational(): Rational {

    val num = this.split("/")

    if (num.size == 1) return Rational(num[0].toBigInteger(), 1.toBigInteger()).normalize()

    return Rational(num[0].toBigInteger(), num[1].toBigInteger()).normalize()
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(sum)
    println(5 divBy 6)
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
