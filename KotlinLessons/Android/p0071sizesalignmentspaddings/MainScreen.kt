package ru.korobeynikov.p0071sizesalignmentspaddings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    //End and bottom paddings
    Column {
        Row {
            Button(onClick = {}) {
                Text("Button1")
            }
            Button(onClick = {}) {
                Text("Button2")
            }
            Button(onClick = {}) {
                Text("Button3")
            }
        }
        Row {
            Button(onClick = {}) {
                Text("Button4")
            }
            Button(modifier = Modifier.padding(end = 30.dp, bottom = 40.dp), onClick = {}) {
                Text("Padding")
            }
            Button(onClick = {}) {
                Text("Button5")
            }
        }
        Row {
            Button(onClick = {}) {
                Text("Button6")
            }
            Button(onClick = {}) {
                Text("Button7")
            }
            Button(onClick = {}) {
                Text("Button8")
            }
        }
    }
}

@Composable
fun StartAndTopPaddingsScreen() {
    Column {
        Row {
            Button(onClick = {}) {
                Text("Button1")
            }
            Button(onClick = {}) {
                Text("Button2")
            }
            Button(onClick = {}) {
                Text("Button3")
            }
        }
        Row {
            Button(onClick = {}) {
                Text("Button4")
            }
            Button(modifier = Modifier.padding(start = 10.dp, top = 20.dp), onClick = {}) {
                Text("Padding")
            }
            Button(onClick = {}) {
                Text("Button5")
            }
        }
        Row {
            Button(onClick = {}) {
                Text("Button6")
            }
            Button(onClick = {}) {
                Text("Button7")
            }
            Button(onClick = {}) {
                Text("Button8")
            }
        }
    }
}

@Composable
fun AllPaddingsScreen() {
    Column {
        Row {
            Button(onClick = {}) {
                Text("Button1", fontSize = 12.sp)
            }
            Button(onClick = {}) {
                Text("Button2", fontSize = 12.sp)
            }
            Button(onClick = {}) {
                Text("Button3", fontSize = 12.sp)
            }
        }
        Row {
            Button(onClick = {}) {
                Text("Button4", fontSize = 12.sp)
            }
            Button(modifier = Modifier.padding(50.dp), onClick = {}) {
                Text("Padding", fontSize = 12.sp)
            }
            Button(onClick = {}) {
                Text("Button5", fontSize = 12.sp)
            }
        }
        Row {
            Button(onClick = {}) {
                Text("Button6", fontSize = 12.sp)
            }
            Button(onClick = {}) {
                Text("Button7", fontSize = 12.sp)
            }
            Button(onClick = {}) {
                Text("Button8", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ModifierAlignmentScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(modifier = Modifier.align(Alignment.TopStart), onClick = {}) {
            Text("alignment = top start", fontSize = 12.sp)
        }
        Button(modifier = Modifier.align(Alignment.TopEnd), onClick = {}) {
            Text("alignment = top end", fontSize = 12.sp)
        }
        Button(modifier = Modifier.align(Alignment.Center), onClick = {}) {
            Text("alignment = center", fontSize = 12.sp)
        }
        Button(modifier = Modifier.align(Alignment.BottomStart), onClick = {}) {
            Text("alignment = bottom start", fontSize = 12.sp)
        }
        Button(modifier = Modifier.align(Alignment.BottomEnd), onClick = {}) {
            Text("alignment = bottom end", fontSize = 12.sp)
        }
    }
}

@Composable
fun OneButtonWithWeightScreen() {
    Row {
        Button(modifier = Modifier.weight(1f), onClick = {}) {
            Text("B1")
        }
        Button(onClick = {}) {
            Text("B2")
        }
        Button(onClick = {}) {
            Text("B3")
        }
    }
}

@Composable
fun ButtonsWithDifferentWeightScreen() {
    Row {
        Button(modifier = Modifier.weight(1f), onClick = {}) {
            Text("B1", fontSize = 12.sp)
        }
        Button(modifier = Modifier.weight(3f), onClick = {}) {
            Text("B2", fontSize = 12.sp)
        }
        Button(modifier = Modifier.weight(2f), onClick = {}) {
            Text("B3", fontSize = 12.sp)
        }
    }
}

@Composable
fun ButtonsWithEqualWeightScreen() {
    Row {
        Button(modifier = Modifier.weight(1f), onClick = {}) {
            Text("B1")
        }
        Button(modifier = Modifier.weight(1f), onClick = {}) {
            Text("B2")
        }
    }
}

@Composable
fun ButtonMaxWidthScreen() {
    Row {
        Button(modifier = Modifier.fillMaxWidth(), onClick = {}) {
            Text("Button with text")
        }
    }
}

@Composable
fun ButtonFixedSizeScreen() {
    Row {
        Button(modifier = Modifier.width(250.dp), onClick = {}) {
            Text("Button with text")
        }
    }
}

@Composable
fun ButtonDynamicSizeScreen() {
    Row {
        Button(onClick = {}) {
            Text("Button with text")
        }
    }
}