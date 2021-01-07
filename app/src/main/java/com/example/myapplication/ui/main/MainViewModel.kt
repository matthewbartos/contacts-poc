package com.example.myapplication.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.ALPHABET
import com.example.myapplication.Contact
import com.example.myapplication.ContactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository = ContactRepository(application)
    private val _contactsLiveData = MutableLiveData<List<Contact>>()
    val contactsLiveData = _contactsLiveData as LiveData<List<Contact>>

    private val usedLetters = mutableMapOf<Char, Boolean>().apply {
        ALPHABET.associateWithTo(this) { false }
    }

    fun fetchContacts() = with(contactRepository) {
        viewModelScope.launch {
            val contactsListAsync = async { getPhoneContacts() }
            val contactEmailAsync = async { getContactEmails() }

            val contacts = contactsListAsync.await()
            val contactEmails = contactEmailAsync.await()

            contacts.forEach {
                contactEmails[it.id]?.let { emails ->
                    it.emails = emails
                }
            }

            _contactsLiveData.postValue(contacts.prepareContacts())
        }
    }

    private fun ArrayList<Contact>.prepareContacts() = filter { it.emails.isNotEmpty() }
        .map { contact ->
            // displaying letter header
            val firstLetter = contact.name.first().toUpperCase()
            if (usedLetters[firstLetter] == false) {
                usedLetters[firstLetter] = true
                contact.apply {
                    firstForGivenLetter = firstLetter
                }
            } else {
                contact
            }
        }

}
