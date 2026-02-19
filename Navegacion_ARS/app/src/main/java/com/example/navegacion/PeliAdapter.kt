package com.example.navegacion

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PeliAdapter(private val contex: Activity, private val arrayList: ArrayList<Peliculas>):
    ArrayAdapter<Peliculas>(contex, R.layout.item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(contex)
        val view: View = inflater.inflate(R.layout.item, null)

        view.findViewById<TextView>(R.id.itemNombre).text = arrayList[position].nombre.toString()
        view.findViewById<TextView>(R.id.itemGenero).text = arrayList[position].genero.toString()
        view.findViewById<TextView>(R.id.itemAnio).text = arrayList[position].anio.toString()

        return view
        //return super.getView(position, convertView, parent)
    }

}