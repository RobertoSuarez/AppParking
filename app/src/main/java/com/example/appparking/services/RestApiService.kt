package com.example.appparking.services

import android.util.Log
import com.example.appparking.model.Car
import com.example.appparking.model.Fare
import com.example.appparking.model.Movement
import com.example.appparking.model.MovementRequest
import com.example.appparking.model.MovementUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {

    fun getMovementsActive(onResult: (ArrayList<Movement>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getMovements().enqueue(
            object : Callback<ArrayList<Movement>> {
                override fun onFailure(call: Call<ArrayList<Movement>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<ArrayList<Movement>>, response: Response<ArrayList<Movement>>) {
                    val movements = response.body()
                    onResult(movements)
                }
            }
        )
    }

    // Recuperamos el carro por la placa.
    fun getByPlateNumber(plateNumber: String, onResult: (Car?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getByPlateNumber(plateNumber).enqueue(
            object : Callback<Car> {
                override fun onFailure(call: Call<Car>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Car>, response: Response<Car>) {
                    val car = response.body()
                    onResult(car)
                }
            }
        )
    }

    fun createMovement(movement: MovementRequest, onResult: (Void?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.createMovement(movement).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Void>, response: Response<Void>) {
                    val statusCode = response.code()
                    Log.d("CODE", "$statusCode")
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }
            }
        )
    }

    fun updateMovement(movement: MovementUpdate, onResult: (Void?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.updateMovement(movement).enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Void>, response: Response<Void>) {
                    val statusCode = response.code()
                    Log.d("CODE", "$statusCode")
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }
            }
        )
    }

    fun getFare(onResult: (Fare?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getFare().enqueue(
            object : Callback<Fare> {
                override fun onFailure(call: Call<Fare>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Fare>, response: Response<Fare>) {
                    val fare = response.body()
                    onResult(fare)
                }
            }
        )
    }
}