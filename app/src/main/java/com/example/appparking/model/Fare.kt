package com.example.appparking.model

import com.google.gson.annotations.SerializedName

data class Fare(
    @SerializedName("id") val id: Int?,
    @SerializedName("amount_per_hour") val amountPerHour: Int
)