package com.example.appparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.appparking.adapters.AdapterMovement
import com.example.appparking.databinding.ActivityMainBinding
import com.example.appparking.model.Movement
import com.example.appparking.services.RestApiService
import okhttp3.OkHttpClient
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var movements: ArrayList<Movement> = ArrayList()
    private var movementsCopy: ArrayList<Movement> = ArrayList()
    private var adapterMovement: AdapterMovement? = null
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // config para el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Seteamos el adaptador
        adapterMovement = AdapterMovement(this, movements)


        // le pasamos el adaptador al listview
        binding.listaPeliculas.adapter = adapterMovement

        // Recuperamos los movimientos
        getMovementsActive()
        
        
        binding.listaPeliculas.setOnItemClickListener { parent, view, position, id ->

            // Recuperamos el item
            val itemSelecction = movements[position]

            // Ingreamos a la pantalla de registro de salida.
            val intent = Intent(this, Checkout::class.java)
            intent.putExtra("movement", itemSelecction as Serializable)
            startActivity(intent)

        }



        binding.btnIngresarAuto.setOnClickListener {
            goToRegisterCarEntry()
        }

        binding.txtBuscador.addTextChangedListener { text ->
            movements.clear()
            movements.addAll(movementsCopy.filter {
                it.plateNumber!!.contains(text.toString(), ignoreCase = true)
            })
            adapterMovement?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        getMovementsActive()
    }

    private fun goToRegisterCarEntry() {
        // Ingreamos a la pantalla de registro de ingreso
        Intent (this, RegisterMovement::class.java).also {
            startActivity(it)
        }
    }

    private fun getMovementsActive() {
        val apiService = RestApiService()

        apiService.getMovementsActive {
            if (it != null) {
                // limpiamos el buscador
                binding.txtBuscador.setText("")
                // Guardamos una copia.
                movementsCopy.clear()
                movementsCopy.addAll(it)

                movements.clear()
                movements.addAll(it)
                adapterMovement?.notifyDataSetChanged()
            } else {
                Log.d("MainActivity", "No hay datos")
            }
        }
    }

}