package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ALPHABET
import com.example.myapplication.Contact
import com.example.myapplication.R

class AlphabetAdapter(context: Context) : RecyclerView.Adapter<AlphabetAdapter.MyViewHolder>() {
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var clickCallback: (char: Char) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(layoutInflater.inflate(R.layout.row_alphabet, parent, false))

    override fun getItemCount() = ALPHABET.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val letter = ALPHABET[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.tvLetter).apply {
                text = letter.toString()
                setOnClickListener {
                    clickCallback.invoke(letter)
                }
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}