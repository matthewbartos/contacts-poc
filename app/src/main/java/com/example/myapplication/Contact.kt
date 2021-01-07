package com.example.myapplication

data class Contact(val id: String, val name: String, var firstForGivenLetter: Char? = null) {
    var numbers = ArrayList<String>()
    var emails = ArrayList<String>()
}