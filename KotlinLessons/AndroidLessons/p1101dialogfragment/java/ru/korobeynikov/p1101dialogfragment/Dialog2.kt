package ru.korobeynikov.p1101dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

class Dialog2 : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb = AlertDialog.Builder(activity).setTitle("Title!")
            .setPositiveButton(R.string.yes, dialogListener)
            .setNegativeButton(R.string.no, dialogListener)
            .setNeutralButton(R.string.maybe, dialogListener)
            .setMessage(R.string.message_text)
        return adb.create()
    }

    private val dialogListener = DialogInterface.OnClickListener { dialog, which ->
        var i = 0
        when (which) {
            Dialog.BUTTON_POSITIVE -> i = R.string.yes
            Dialog.BUTTON_NEGATIVE -> i = R.string.no
            Dialog.BUTTON_NEUTRAL -> i = R.string.maybe
        }
        if (i > 0)
            Log.d(MainActivity.LOG_TAG, "Dialog 2: ${resources.getString(i)}")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(MainActivity.LOG_TAG, "Dialog 2: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(MainActivity.LOG_TAG, "Dialog 2: onCancel")
    }
}