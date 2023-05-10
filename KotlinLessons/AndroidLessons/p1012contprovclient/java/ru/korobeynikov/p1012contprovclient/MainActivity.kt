package ru.korobeynikov.p1012contprovclient

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p1012contprovclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private val contactName = "name"
    private val contactEmail = "email"
    private val contactUri = Uri.parse("content://ru.korobeynikov.providers.AddressBook/contacts")
    private val listContacts = ArrayList<String>()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        showData()
        val rvContact = binding.rvContact
        val layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(listContacts)
        with(rvContact) {
            this.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
            adapter = contactAdapter
        }
    }

    private fun showData() {
        listContacts.clear()
        val cursor = contentResolver.query(contactUri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val nameColIndex = cursor.getColumnIndex("name")
            val emailColIndex = cursor.getColumnIndex("email")
            do {
                listContacts.add("${cursor.getString(nameColIndex)}-${cursor.getString(emailColIndex)}\n")
            } while (cursor.moveToNext())
        }
        cursor?.close()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clickInsert() {
        val cv = ContentValues()
        cv.put(contactName, "name 4")
        cv.put(contactEmail, "email 4")
        val newUri = contentResolver.insert(contactUri, cv)
        Log.d(logTag, "insert, result Uri : ${newUri.toString()}")
        showData()
        contactAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clickUpdate() {
        val cv = ContentValues()
        cv.put(contactName, "name 5")
        cv.put(contactEmail, "email 5")
        val uri = ContentUris.withAppendedId(contactUri, 2)
        val cnt = contentResolver.update(uri, cv, null, null)
        Log.d(logTag, "update, count = $cnt")
        showData()
        contactAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clickDelete() {
        val uri = ContentUris.withAppendedId(contactUri, 3)
        val cnt = contentResolver.delete(uri, null, null)
        Log.d(logTag, "delete, count = $cnt")
        showData()
        contactAdapter.notifyDataSetChanged()
    }

    fun clickError() {
        val uri = Uri.parse("content://ru.korobeynikov.providers.AddressBook/phones")
        try {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.close()
        } catch (ex: Exception) {
            Log.d(logTag, "Error: ${ex::class}, ${ex.message}")
        }
    }
}