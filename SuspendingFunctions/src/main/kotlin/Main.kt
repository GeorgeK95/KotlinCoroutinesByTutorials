import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun main() {
  GlobalScope.launch {
    val user = getUserSuspend("123")
    println(user)
  }
  println("main end")
}

suspend fun getUserSuspend(userId: String): User {
  delay(1000)
  return User(userId, "Filip")
}

fun getUserFromNetworkCallback(
  userId: String,
  onUserResponse: (User?, Throwable?) -> Unit
) {
  thread {
    try {
      Thread.sleep(1000)

      val user = User(userId, "Filip")
      onUserResponse(user, null)
    } catch (e: Exception) {
      onUserResponse(null, e)
    }
  }
  println("end")
}

data class User(
  val userId: String,
  val name: String
)