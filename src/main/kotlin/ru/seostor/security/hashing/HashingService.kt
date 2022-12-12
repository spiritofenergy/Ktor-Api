package ru.seostor.security.hashing

import sun.security.util.Length

interface HashingService {
    fun generateSaltedHash(value: String, saltLength: Int =32): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash):Boolean
}