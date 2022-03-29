package testing

import kotlinx.coroutines.delay

class MainPresenter {

    suspend fun getUser(userId: String): User {
        delay(1000)

        return User(userId, "Joe")
    }
}