package com.example.serializers

import com.example.data.user.User
import kotlinx.serialization.Serializable


@Serializable
data class SignupUser(val name: String, val password: String, val email: String)

fun SignupUser.toUser(): User {
    return User(name = this.name, email = this.email, password = this.password)
}