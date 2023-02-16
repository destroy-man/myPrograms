package ru.korobeynikov.p0611alertdialogprepare

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DialogAlert : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val adb = AlertDialog.Builder(activity)
        adb.setTitle("Текущее время")
        adb.setMessage(sdf.format(Date(System.currentTimeMillis())))
        return adb.create()
    }
}