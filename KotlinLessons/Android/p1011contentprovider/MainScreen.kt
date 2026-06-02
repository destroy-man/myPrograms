package ru.korobeynikov.p1011contentprovider

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri

@Composable
fun MainScreen() {
    val logTag = "myLogs"
    val contactUri = "content://ru.korobeynikov.providers.AddressBook/contacts".toUri()
    val contactName = "name"
    val contactEmail = "email"

    val context = LocalContext.current
    val addressList = remember { mutableStateListOf<Address>() }
    getDataFromCursor(context, contactUri, contactName, contactEmail, addressList)

    Column {
        Row {
            Button(onClick = {
                val cv = ContentValues()
                cv.put(contactName, "name 4")
                cv.put(contactEmail, "email 4")
                val newUri = context.contentResolver.insert(contactUri, cv)
                getDataFromCursor(context, contactUri, contactName, contactEmail, addressList)
                Log.d(logTag, "insert, result Uri: ${newUri.toString()}")
            }) {
                Text(stringResource(R.string.insert))
            }
            Button(onClick = {
                val cv = ContentValues()
                cv.put(contactName, "name 5")
                cv.put(contactEmail, "email 5")
                val uri = ContentUris.withAppendedId(contactUri, 2)
                val cnt = context.contentResolver.update(uri, cv, null, null)
                getDataFromCursor(context, contactUri, contactName, contactEmail, addressList)
                Log.d(logTag, "update, count = $cnt")
            }) {
                Text(stringResource(R.string.update))
            }
            Button(onClick = {
                val uri = ContentUris.withAppendedId(contactUri, 3)
                val cnt = context.contentResolver.delete(uri, null, null)
                getDataFromCursor(context, contactUri, contactName, contactEmail, addressList)
                Log.d(logTag, "delete, count = $cnt")
            }) {
                Text(stringResource(R.string.delete))
            }
            Button(onClick = {
                val uri = "content://ru.korobeynikov.providers.AddressBook/phones".toUri()
                try {
                    val cursor = context.contentResolver.query(
                        uri,
                        null,
                        null,
                        null,
                        null
                    )
                    cursor?.close()
                } catch (ex: Exception) {
                    Log.d(logTag, "Error: ${ex.javaClass}, ${ex.message}")
                }
            }) {
                Text(stringResource(R.string.error))
            }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(addressList) { address ->
                AddressElement(address)
            }
        }
    }
}

fun getDataFromCursor(
    context: Context,
    contactUri: Uri,
    contactName: String,
    contactEmail: String,
    addressList: SnapshotStateList<Address>
) {
    addressList.clear()
    val cursor =
        context.contentResolver.query(contactUri, null, null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
        val nameColIndex = cursor.getColumnIndex(contactName)
        val emailColIndex = cursor.getColumnIndex(contactEmail)
        do {
            addressList.add(
                Address(
                    cursor.getString(nameColIndex),
                    cursor.getString(emailColIndex)
                )
            )
        } while (cursor.moveToNext())
        cursor.close()
    } else {
        Toast.makeText(
            context,
            "ContentProvider не содержит данных",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun AddressElement(address: Address) {
    Column {
        Text(
            address.name,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
        )
        Text(
            address.email,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
}

data class Address(
    val name: String,
    val email: String
)