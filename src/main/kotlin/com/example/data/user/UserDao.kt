package com.example.data.user

interface UserDao {
    suspend fun insertUser(user: User): Long
    suspend fun getUser(id: Long): User?
    suspend fun updateUser(user: User): Long
    suspend fun deleteUser(user: User): Boolean
}



class UserDaoImpl : UserDao {
    private val users = mutableListOf<User>()

    override suspend fun insertUser(user: User): Long {
        val tempUser = User(name = user.name, email = user.email, password = user.password)
        users.add(tempUser)
        return tempUser.id
    }

    override suspend fun getUser(id: Long): User? {
        return users.find { it.id == id }
    }

    override suspend fun updateUser(user: User): Long {
        val tempUser = users.find { it.id == user.id }
        return if (tempUser == null) {
            val newUser = User(name = user.name, email = user.email, password = user.password)
            users.add(newUser)
            newUser.id
        } else {
            val index = users.indexOf(tempUser)
            users.removeAt(index)
            users.add(index, user)
            user.id
        }
    }

    override suspend fun deleteUser(user: User): Boolean {
        val tempUser = users.find { it.id == user.id }
        return users.remove(tempUser)
    }
}
