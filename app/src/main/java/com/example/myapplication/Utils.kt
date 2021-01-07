package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission

fun hasPermission(context:Context, permission: String) =
    checkSelfPermission(context, permission) == PERMISSION_GRANTED

fun Activity.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    rationaleStr: String
) {
    val provideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    if (provideRationale) {
        AlertDialog.Builder(this).apply {
            setTitle("Permission")
            setMessage(rationaleStr)
            setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(
                    this@requestPermissionWithRationale,
                    arrayOf(permission),
                    requestCode
                )
            }
            create()
            show()
        }
    } else {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
}

val ALPHABET = listOf(
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'H',
    'I',
    'J',
    'K',
    'L',
    'M',
    'N',
    'O',
    'P',
    'Q',
    'R',
    'S',
    'T',
    'U',
    'V',
    'W',
    'X',
    'Y',
    'Z'
)