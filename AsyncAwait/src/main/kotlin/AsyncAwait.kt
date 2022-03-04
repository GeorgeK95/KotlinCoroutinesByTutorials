import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

fun main() {
  val userId = 992

  val start = System.currentTimeMillis()

  GlobalScope.launch {
    val userByIdFromNetwork = getUserByIdFromNetwork(userId)
    val usersFromFile = readUsersFromFile("users.txt")

    val userStoredInFile = checkUserExists(userByIdFromNetwork.await(), usersFromFile.await())

    if (userStoredInFile) {
      println("Found user in file!")
      val end = System.currentTimeMillis()
      print("Execution time: ${end-start}")
    }
  }

  Thread.sleep(5_000)
}

private fun getUserByIdFromNetwork(userId: Int) = GlobalScope.async {
  Thread.sleep(3_000)
  User(userId, "Filip", "Babic")
}

private fun readUsersFromFile(filePath: String) = GlobalScope.async {
  File(filePath)
    .readLines()
    .filter {
      it.isNotEmpty()
    }
    .map {
      val data = it.split(" ")
      if (data.size == 3) data else emptyList()
    }
    .filter {
      it.isNotEmpty()
    }
    .map {
      val userId = it[0].toInt()
      val name = it[1]
      val lastName = it[2]

      User(userId, name, lastName)
    }
}

private fun checkUserExists(user: User, users: List<User>): Boolean {
  return user in users
}