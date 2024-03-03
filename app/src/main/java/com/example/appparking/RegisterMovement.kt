package com.example.appparking

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.example.appparking.databinding.ActivityRegisterMovementBinding
import com.example.appparking.model.Car
import com.example.appparking.model.MovementRequest
import com.example.appparking.services.RestApiService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterMovement : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterMovementBinding
    private lateinit var currentDateTime: LocalDateTime
    private lateinit var car: Car

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // configuramos el binding
        binding = ActivityRegisterMovementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            searchCar()
        }

        binding.txtNumeroPlacaEdit.filters = arrayOf<InputFilter>(UpperCaseInputFilter())


        // Ocultamos el card
        binding.card.visibility = View.GONE
        binding.containerDateHour.visibility = View.GONE

        binding.btnRegistrarIngreso.setOnClickListener(::registrarIngreso)
    }

    private fun registrarIngreso(view: View) {
        if (car.id == 0) {
            Snackbar.make( binding.root, "No se encontro el vehiculo", Snackbar.LENGTH_LONG).show()
            return
        }

        val movementRequest = car.id?.let {
            MovementRequest(
                cardID = it,
                enterDate = currentDateTime.toString()
            )
        }

        val apiService = RestApiService()
        if (movementRequest != null) {
            apiService.createMovement(movementRequest) {
                Snackbar.make( binding.root, "Ingreso registrado", Snackbar.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun setCardData(car: Car) {
        binding.lbPlateNumber.text = car.plateNumber
        binding.lbModel.text = car.model
        binding.lbYear.text = car.year.toString()
        binding.lbColor.text = car.color
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun searchCar() {
        val apiService = RestApiService()
        val plateNumber = binding.txtNumeroPlacaEdit.text.toString()
        apiService.getByPlateNumber(plateNumber) {
            if (it != null) {
                Snackbar.make( binding.root, "Datos obtenidos", Snackbar.LENGTH_LONG).show()
                binding.card.visibility = View.VISIBLE
                car = it
                setCardData(it)

                // Recuperamos la hora para establecerla en el formulario.
                currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formattedDateTime = currentDateTime.format(formatter)

                binding.lbDateHour.text = formattedDateTime
                binding.containerDateHour.visibility = View.VISIBLE


            } else {
                Snackbar.make(binding.root, "Se debe registrar el carro", Snackbar.LENGTH_LONG).show()
                // Ocultamos los elementos en caso de no existir la información.
                binding.card.visibility = View.GONE
                binding.containerDateHour.visibility = View.GONE

                MaterialAlertDialogBuilder(this)
                    .setTitle("Auto no registrado")
                    .setMessage("¿Deseas registrar el nuevo auto?")
                    .setNeutralButton("Cancelar", { dialog, which ->

                    })
                    .setPositiveButton("Nuevo auto", { dialog, which ->
                        Log.d("sdf", "Nuevo auto")
                    }).show()
            }
        }
    }

    // Clase para convertir las letras a mayúsculas
    private class UpperCaseInputFilter : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val sb = StringBuilder()
            for (i in start until end) {
                sb.append(Character.toUpperCase(source!![i]))
            }
            return sb.toString()
        }
    }

}