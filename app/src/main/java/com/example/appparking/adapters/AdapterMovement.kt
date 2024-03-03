package com.example.appparking.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.appparking.databinding.ItemMovementBinding
import com.example.appparking.model.Movement
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

class AdapterMovement(private val context: Context, private val arrayList:java.util.ArrayList<Movement>) :
    BaseAdapter() {


    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val binding = ItemMovementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var convertView = convertView
        convertView = binding.root

        binding.txtPlateNumber.text = arrayList[position].plateNumber
        binding.txtModelCard.text = arrayList[position].model
        binding.txtCardYear.text = arrayList[position].year.toString()

        // convertir a fecha para sacar la hora y la fecha por separado
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = format.parse(arrayList[position].enterDate)
            val calendar = Calendar.getInstance()
            calendar.time = date

            binding.txtDateHour.text = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"
            binding.txtDate.text = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(Calendar.YEAR)}"
        } catch (e: Exception) {
            binding.txtDateHour.text = "--"
            binding.txtDate.text = "--"
        }


        return convertView
    }

}