package com.example.appparking

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.appparking.databinding.ActivityCheckoutBinding
import com.example.appparking.model.Movement
import com.example.appparking.model.MovementUpdate
import com.example.appparking.services.RestApiService
import java.text.NumberFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Checkout : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private var fare: Int = 0
    private lateinit var movement: Movement
    private lateinit var currentDateTime: LocalDateTime
    private lateinit var entryDateTime: LocalDateTime
    private var diferenciaMinutes: Long = 0
    private var diferenciaHora: Float = 0.0f
    private var total: Float = 0.0f

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentDateTime = LocalDateTime.now()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                val data = getSerializable("movement") as Movement?
                if (data != null) {
                    movement = data
                    binding.lbPlateNumber.text = data.plateNumber
                    binding.lbModel.text = data.model
                    binding.lbYear.text = data.year.toString()
                    binding.lbColor.text = data.color

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    entryDateTime = LocalDateTime.parse(data.enterDate, formatter)
                    val fechaActual = LocalDateTime.now()
                    diferenciaMinutes = ChronoUnit.MINUTES.between(entryDateTime, fechaActual)

                    binding.lbEntrada.text = data.enterDate
                    binding.lbSalida.text = currentDateTime.toString()

                    // mostrar la diferencia
                    val diferencia = Duration.between(entryDateTime, fechaActual)
                    val horas = diferencia.toHours()
                    val minutos = diferencia.toMinutes() % 60
                    binding.lbDiferencia.text = "$horas:$minutos"
                } else {
                    finish()
                }
            }
        }


        // TODO Recuperar la terifa.
        val apiService = RestApiService()
        apiService.getFare {
            if (it != null) {
                fare = it.amountPerHour
                diferenciaHora = diferenciaMinutes.toFloat() / 60

                total = fare * diferenciaHora
                val lastPlate = movement.plateNumber?.last()
                val numberLast = lastPlate?.digitToInt()
                val esPar = numberLast?.rem(2) == 0

                if (esPar) {
                    total += 0.2f * total
                    binding.lbporcentaje.text = "+20%"
                } else {
                    total -= 0.1f * total
                    binding.lbporcentaje.text = "-20%"
                }

                val dolaresStr = NumberFormat.getCurrencyInstance().format(total)

                binding.lbMonto.text = dolaresStr
            }
        }



        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)
        binding.lbSalida.text = formattedDateTime

        binding.btnSalida.setOnClickListener(::RegistrarSalida)
    }

    fun RegistrarSalida(view: View) {

        val apiService = RestApiService()
        // Construirmos el objecto para actualizar la base de datos
        val movementUpdate = movement.movementID?.let {
            MovementUpdate(
                id = it,
                amount = total,
                exitDate = currentDateTime.toString()
            )
        }
        // Actualizamos la base de datos
        apiService.updateMovement(movementUpdate!!) {
            finish()
        }
    }
}