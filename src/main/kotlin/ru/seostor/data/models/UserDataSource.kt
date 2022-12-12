package ru.seostor.data.models

import ru.seostor.data.models.responses.User

interface UserDataSource {
    suspend fun grtUserByUsername(username: String): User?

    suspend fun insertUser(user: User): Boolean
}