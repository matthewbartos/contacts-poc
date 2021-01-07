package com.example.myapplication

import android.app.Application
import android.provider.ContactsContract.*

class ContactRepository(private val application: Application) {

    fun getPhoneContacts() = ArrayList<Contact>().apply {
        application.contentResolver?.query(
            Contacts.CONTENT_URI,
            null,
            null,
            null,
            CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
            ?.takeUnless { it.count == 0 }
            ?.let {
                val idIndex = it.getColumnIndex(Contacts._ID)
                val nameIndex = it.getColumnIndex(Contacts.DISPLAY_NAME)

                while (it.moveToNext()) {
                    val id = it.getString(idIndex)
                    val name = it.getString(nameIndex)
                    if (name != null) {
                        add(Contact(id, name))
                    }
                }
                it
            }
            ?.close()
    }

    fun getContactEmails() = HashMap<String, ArrayList<String>>().apply {
        val emailCursor = application.contentResolver.query(
            CommonDataKinds.Email.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (emailCursor != null && emailCursor.count > 0) {
            val contactIdIndex =
                emailCursor.getColumnIndex(CommonDataKinds.Email.CONTACT_ID)
            val emailIndex =
                emailCursor.getColumnIndex(CommonDataKinds.Email.ADDRESS)
            while (emailCursor.moveToNext()) {
                val contactId = emailCursor.getString(contactIdIndex)
                val email = emailCursor.getString(emailIndex)
                //check if the map contains key or not, if not then create a new array list with email
                if (containsKey(contactId)) {
                    this[contactId]?.add(email)
                } else {
                    this[contactId] = arrayListOf(email)
                }
            }
            //contact contains all the emails of a particular contact
            emailCursor.close()
        }
    }

}