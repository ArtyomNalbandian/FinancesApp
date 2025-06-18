package com.example.financesapp.data.remote.models

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class Account(
    val id: Int,
    @SerializedName("userId")
    val userId: Long,
    val name: String,
    val balance: String,
    val currency: String,
    @SerializedName("createdAt")
    val createdAt: Instant,
    @SerializedName("updatedAt")
    val updatedAt: Instant,
)
