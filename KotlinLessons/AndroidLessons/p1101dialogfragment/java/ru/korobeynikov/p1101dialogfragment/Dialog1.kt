package ru.korobeynikov.p1101dialogfragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import ru.korobeynikov.p1101dialogfragment.databinding.FragmentDialog1Binding

class Dialog1 : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        dialog?.setTitle("Title!")
        val binding = DataBindingUtil
            .inflate<FragmentDialog1Binding>(inflater, R.layout.fragment_dialog1, container, false)
        binding.view = this
        return binding.root
    }

    fun clickButton(v: View) {
        Log.d(MainActivity.LOG_TAG, "Dialog 1: ${(v as Button).text}")
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(MainActivity.LOG_TAG, "Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(MainActivity.LOG_TAG, "Dialog 1: onCancel")
    }
}