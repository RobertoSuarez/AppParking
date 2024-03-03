package com.example.appparking.services

import com.example.appparking.model.Car
import com.example.appparking.model.Fare
import com.example.appparking.model.Movement
import com.example.appparking.model.MovementRequest
import com.example.appparking.model.MovementUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface RestApi {

    @Headers("Content-Type: application/json")
    @GET("/parking/api/movement")
    fun getMovements(): Call<ArrayList<Movement>>

    @Headers("Content-Type: application/json")
    @POST("/parking/api/movement")
    fun createMovement(@Body movement: MovementRequest): Call<Void>

    @Headers("Content-Type: application/json")
    @PATCH("/parking/api/movement")
    fun updateMovement(@Body movement: MovementUpdate): Call<Void>

    @Headers("Content-Type: application/json")
    @GET("/parking/api/car")
    fun getByPlateNumber(@Query("plate_number") plateNumber: String): Call<Car>


    @Headers("Content-Type: application/json")
    @POST("/parking/api/fare")
    fun getFare(): Call<Fare>
}