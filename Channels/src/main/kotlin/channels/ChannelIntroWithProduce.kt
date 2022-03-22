import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() {
    val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

    fun produceFruits() = GlobalScope.produce {
        for (fruit in fruitArray) {
            send(fruit)

            if (fruit == "Pear") {
                close()
            }
        }
    }

    runBlocking {
        val fruits = produceFruits()
        fruits.consumeEach { println(it) }
        println("Done!")
    }
}