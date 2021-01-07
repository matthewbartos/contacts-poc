package com.example.myapplication.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Contact
import com.example.myapplication.R

class ContactsAdapter(context: Context) : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var contacts = mutableListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(layoutInflater.inflate(R.layout.row_contact, parent, false))

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.tvContactName).text = contact.name
            findViewById<TextView>(R.id.tvContactEmail).text = contact.emails.joinToString()

            findViewById<CardView>(R.id.cvContactRow).setOnClickListener {
                findViewById<CheckBox>(R.id.cbContactSelect).toggle()
            }

            // displaying letter header
            if (contact.firstForGivenLetter != null) {
                findViewById<TextView>(R.id.tvContactAlphabet).apply {
                    isVisible = true
                    text = contact.firstForGivenLetter.toString()
                }
            } else {
                findViewById<TextView>(R.id.tvContactAlphabet).apply { isVisible = false }
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}