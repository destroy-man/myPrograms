package ru.korobeynikov.p0131menu

import android.util.Log
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    Column {
        ContextMenuTextColor()
        ContextMenuTextSize()
    }
}

@Composable
fun ContextMenuTextColor() {
    var expanded by remember { mutableStateOf(false) }
    var textColor by remember { mutableLongStateOf(0xFF000000) }
    Box(modifier = Modifier.padding(vertical = 50.dp)) {
        Text(
            "Text color",
            fontSize = 26.sp,
            color = Color(textColor),
            modifier = Modifier.combinedClickable(
                onClick = {},
                onLongClick = {
                    expanded = !expanded
                }
            )
        )
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            DropdownMenuItem(
                text = {
                    Text("Red")
                },
                onClick = {
                    textColor = 0xFFFF0000
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Green")
                },
                onClick = {
                    textColor = 0xFF00FF00
                }
            )
            DropdownMenuItem(
                text = {
                    Text("Blue")
                },
                onClick = {
                    textColor = 0xFF0000FF
                }
            )
        }
    }
}

@Composable
fun ContextMenuTextSize() {
    var expanded by remember { mutableStateOf(false) }
    var textSize by remember { mutableStateOf(22.sp) }
    Box {
        Text(
            "Text size",
            fontSize = textSize,
            modifier = Modifier.combinedClickable(
                onClick = {},
                onLongClick = {
                    expanded = !expanded
                }
            )
        )
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            DropdownMenuItem(
                text = {
                    Text("22")
                },
                onClick = {
                    textSize = 22.sp
                }
            )
            DropdownMenuItem(
                text = {
                    Text("26")
                },
                onClick = {
                    textSize = 26.sp
                }
            )
            DropdownMenuItem(
                text = {
                    Text("30")
                },
                onClick = {
                    textSize = 30.sp
                }
            )
        }
    }
}

@Composable
fun StandardMenuScreen() {
    var isExtendedMenu by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        StandardMenu(isExtendedMenu, Modifier.align(Alignment.End))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isExtendedMenu, onCheckedChange = {
                isExtendedMenu = !isExtendedMenu
            })
            Text("Расширенное меню")
        }
    }
}

@Composable
fun StandardMenu(isExtendedMenu: Boolean, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier) {
        IconButton(onClick = {
            expanded = !expanded
        }) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                },
                text = {
                    Text("Add")
                },
                onClick = {
                    Log.d("myLogs", "Добавить")
                }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(Icons.Outlined.Edit, contentDescription = null)
                },
                text = {
                    Text("Edit")
                },
                onClick = {
                    Log.d("myLogs", "Редактировать")
                }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(Icons.Outlined.Check, contentDescription = null)
                },
                text = {
                    Text("Copy")
                },
                onClick = {
                    Log.d("myLogs", "Копировать")
                }
            )

            if (isExtendedMenu) {
                HorizontalDivider()
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Outlined.CheckCircle, contentDescription = null)
                    },
                    text = {
                        Text("Paste")
                    },
                    onClick = {
                        Log.d("myLogs", "Вставить")
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Outlined.Delete, contentDescription = null)
                    },
                    text = {
                        Text("Delete")
                    },
                    onClick = {
                        Log.d("myLogs", "Удалить")
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = null)
                    },
                    trailingIcon = {
                        Icon(Icons.Outlined.Home, contentDescription = null)
                    },
                    text = {
                        Text("Exit")
                    },
                    onClick = {
                        Log.d("myLogs", "Выход")
                    }
                )
            }
        }
    }
}

@Composable
fun SimpleMenuScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        SimpleMenu(Modifier.align(Alignment.End))
    }
}

@Composable
fun SimpleMenu(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier) {
        IconButton(onClick = {
            expanded = !expanded
        }) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            DropdownMenuItem(
                text = {
                    Text("menu1")
                },
                onClick = {
                    Log.d("myLogs", "Выбрано меню 1")
                }
            )
            DropdownMenuItem(
                text = {
                    Text("menu2")
                },
                onClick = {
                    Log.d("myLogs", "Выбрано меню 2")
                }
            )
            DropdownMenuItem(
                text = {
                    Text("menu3")
                },
                onClick = {
                    Log.d("myLogs", "Выбрано меню 3")
                }
            )
            DropdownMenuItem(
                text = {
                    Text("menu4")
                },
                onClick = {
                    Log.d("myLogs", "Выбрано меню 4")
                }
            )
        }
    }
}