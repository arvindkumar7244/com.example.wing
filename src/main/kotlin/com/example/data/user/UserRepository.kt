package com.example.data.user

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun getUser(id: Long?): User? {
        if (id == null) return null
        return userDao.getUser(id)
    }

    suspend fun updateUser(user: User): Long {
        return userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User): Boolean {
        return userDao.deleteUser(user)
    }

}