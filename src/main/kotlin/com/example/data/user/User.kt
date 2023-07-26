package com.example.data.user

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Long = System.currentTimeMillis(), val name: String, val email: String, val password: String)
