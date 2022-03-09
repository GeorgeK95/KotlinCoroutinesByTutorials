import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

    val kotlinChannel = Channel<String>()

    runBlocking {
        GlobalScope.launch {
            for (fruit in fruitArray) {
                if (fruit == "Grapes") {
                    println("Closed")
                    kotlinChannel.close()
                    return@launch
                }

                println("Sending $fruit")
                kotlinChannel.send(fruit)
            }
        }

        println("Received: ${kotlinChannel.receive()}")

        for (fruit in kotlinChannel) {
            println(fruit)
        }

        println("Done!")
    }
}