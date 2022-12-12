package ru.seostor.data.models

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import ru.seostor.data.models.responses.User

class MongoUserDataSource(
    db: CoroutineDatabase
) : UserDataSource {


    private val users = db.getCollection<User>()
    override suspend fun grtUserByUsername(username: String): User? =
        users.findOne(User::username eq username)


    override suspend fun insertUser(user: User): Boolean =
        users.insertOne(user).wasAcknowledged()

}