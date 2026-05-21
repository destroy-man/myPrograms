package ru.korobeynikov.p0751writingandreadingfiles

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    //Launcher для записи данных в файл
    val writeFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain"),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write("Содержимое файла".toByteArray())
                }
            }
        }
    )

    //Launcher для чтения данных из файла
    val readFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(uri)
                val text = inputStream?.bufferedReader()?.use { reader ->
                    reader.readText()
                }
                text?.let {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    Column {
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                try {
                    val fos = context.openFileOutput("myFileInt.txt", Context.MODE_PRIVATE)
                    fos.write("Содержимое файла".toByteArray())
                    fos.close()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Данные успешно сохранены во внутреннем хранилище",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (_: IOException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Не удалось сохранить данные во внутреннее хранилище",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }) {
            Text(stringResource(R.string.write_file_internal))
        }

        Button(modifier = Modifier.padding(top = 5.dp), onClick = {
            val file = File(context.filesDir, "myFileInt.txt")
            if (file.exists()) {
                Toast.makeText(context, file.readText(), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Файла во внутреннем хранилище не существует",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text(stringResource(R.string.read_file_internal))
        }

        Button(modifier = Modifier.padding(top = 5.dp), onClick = {
            writeFileLauncher.launch("myFileExt.txt")
        }) {
            Text(stringResource(R.string.write_file_external))
        }

        Button(modifier = Modifier.padding(top = 5.dp), onClick = {
            readFileLauncher.launch(arrayOf("text/plain"))
        }) {
            Text(stringResource(R.string.read_file_external))
        }
    }
}