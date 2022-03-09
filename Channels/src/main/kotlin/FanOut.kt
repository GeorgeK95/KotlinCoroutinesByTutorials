import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

typealias Predicate<E> = (E) -> Boolean
typealias Rule<E> = Pair<Channel<E>, Predicate<E>>

fun main() {
    data class Fruit(override val name: String, override val color: String) : Item
    data class Vegetable(override val name: String, override val color: String) : Item

    fun isFruit(item: Item) = item is Fruit

    fun isVegetable(item: Item) = item is Vegetable

    fun produceItems(): ArrayList<Item> {
        val itemsArray = ArrayList<Item>()
        itemsArray.add(Fruit("Apple", "Red"))
        itemsArray.add(Vegetable("Zucchini", "Green"))
        itemsArray.add(Fruit("Grapes", "Green"))
        itemsArray.add(Vegetable("Radishes", "Red"))
        itemsArray.add(Fruit("Banana", "Yellow"))
        itemsArray.add(Fruit("Cherries", "Red"))
        itemsArray.add(Vegetable("Broccoli", "Green"))
        itemsArray.add(Fruit("Strawberry", "Red"))
        itemsArray.add(Vegetable("Red bell pepper", "Red"))
        return itemsArray
    }

    runBlocking {
        val kotlinChannel = Channel<Item>()
        val fruitsChannel = Channel<Item>()
        val vegetablesChannel = Channel<Item>()

        launch {
            produceItems().forEach {
                kotlinChannel.send(it)
            }
            kotlinChannel.close()
        }

        val typeDemultiplexer = Demultiplexer(
            fruitsChannel to { item: Item -> isFruit(item) },
            vegetablesChannel to { item: Item -> isVegetable(item) }
        )

        launch {
            typeDemultiplexer.consume(kotlinChannel)
        }

        launch {
            for (item in fruitsChannel) {
                // Consume fruitsChannel
                println("${item.name} is a fruit")
            }
        }

        launch {
            for (item in vegetablesChannel) {
                // Consume vegetablesChannel
                println("${item.name}  is a vegetable")
            }
        }
    }
}

class Demultiplexer<E>(vararg val rules: Rule<E>) {

    suspend fun consume(recv: ReceiveChannel<E>) {
        for (item in recv) {
            // 1
            for (rule in rules) {
                // 2
                if (rule.second(item)) {
                    // 3
                    rule.first.send(item)
                }
            }
        }
        // 4
        closeAll()
    }

    // Closes all the demultiplexed channels
    private fun closeAll() {
        rules.forEach { it.first.close() }
    }
}
