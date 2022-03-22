package produsersAndActors

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

class WarehouseRobot(private val id: Int, private var packages: List<Package>) {

    fun organizeItems() {
        val itemsToProcess = packages.take(ROBOT_CAPACITY)
        val leftoverItems = packages.drop(ROBOT_CAPACITY)

        packages = itemsToProcess

        val packageIds = packages.map { it.id }
            .fold("") { acc, item -> "$acc$item " }

        if (leftoverItems.isNotEmpty()) {
            GlobalScope.launch {
                WarehouseRobot(id.inc(), leftoverItems).organizeItems()
            }
        }

        processItems(id, itemsToProcess)

        println("Robot #$id processed following packages:$packageIds")
    }

    private fun processItems(robotId: Int, items: List<Package>) {
        val actor = GlobalScope.actor<Package>(
            capacity = ROBOT_CAPACITY
        ) {
            var hasProcessedItems = false

            while (packages.isNotEmpty()) {
                val currentPackage = poll()

                currentPackage?.run {
                    organize(robotId, this)

                    packages -= currentPackage
                    hasProcessedItems = true
                }

                if (hasProcessedItems && currentPackage == null) {
                    cancel()
                }
            }
        }

        items.forEach { actor.offer(it) }
    }

    private fun organize(robotId: Int, warehousePackage: Package) = println(
        "Organized package " +
                "${warehousePackage.id}:" +
                warehousePackage.name +
                "by robotId: $robotId"
    )

    companion object {
        private const val ROBOT_CAPACITY = 3
    }
}