package ru.korobeynikov.p04rowcolumnboxconstraintlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun HomeScreen() {
    ConstraintLayout {
        val (n, ame, title, description) = createRefs()
        Text(text = "N", fontSize = 48.sp, modifier = Modifier.constrainAs(n) {
            top.linkTo(parent.top, margin = 0.dp)
        })
        Text(text = "ame", modifier = Modifier.constrainAs(ame) {
            start.linkTo(parent.start, margin = 4.dp)
            top.linkTo(description.bottom, margin = 0.dp)
        })
        Text(text = "Title", modifier = Modifier.constrainAs(title) {
            start.linkTo(n.end, margin = 8.dp)
            top.linkTo(parent.top, margin = 8.dp)
        })
        Text(text = "Description", modifier = Modifier.constrainAs(description) {
            start.linkTo(n.end, margin = 8.dp)
            top.linkTo(title.bottom, margin = 0.dp)
        })
    }
}

@Composable
fun HomeScreenColumnGrayBackground() {
    Column(
        modifier = Modifier.background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "Title", fontSize = 32.sp)
        Text(text = "Description", fontSize = 20.sp)
    }
}

@Composable
fun HomeScreenColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "Title", fontSize = 32.sp)
        Text(text = "Description", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))
    }
}

@Composable
fun HomeScreenRowFixSpacer() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Name", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Surname", fontSize = 20.sp)
    }
}

@Composable
fun HomeScreenRow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Name", fontSize = 20.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Surname", fontSize = 20.sp)
    }
}

@Composable
fun HomeScreenBoxBackground() {
    Box {
        Text(text = "N", fontSize = 48.sp, modifier = Modifier.background(color = Color.Green))
        Text(
            text = "ame",
            modifier = Modifier
                .background(color = Color.Yellow)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun HomeScreenBox() {
    Box {
        Text(text = "N", fontSize = 48.sp)
        Text(text = "ame", modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun HomeScreenCombineLayout() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box {
            Text(text = "N", fontSize = 48.sp)
            Text(text = "ame", modifier = Modifier.align(Alignment.BottomCenter))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("Title")
            Text("Description")
        }
    }
}

@Composable
fun HomeScreenPadding() {
    Column(modifier = Modifier.padding(start = 32.dp, top = 16.dp)) {
        Text("Title")
        Text("Description")
    }
}

@Composable
fun HomeScreenIfFor(list: List<String>) {
    if (list.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Empty screen")
        }
    } else {
        Column {
            for (s in list) {
                Text(text = s)
            }
        }
    }
}

@Composable
fun HomeScreenResources() {
    Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.large_padding))) {
        Text(text = stringResource(id = R.string.home_screen_title))
        Text(text = stringResource(id = R.string.home_screen_description))
    }
}

@Composable
fun HomeScreenConstraintEqualElements() {
    ConstraintLayout {
        val (name, surname, title, description) = createRefs()
        Text(text = "Name", modifier = Modifier.constrainAs(name) {
            top.linkTo(parent.top, margin = 0.dp)
        })
        Text(text = "Surname", modifier = Modifier.constrainAs(surname) {
            top.linkTo(name.bottom, margin = 0.dp)
        })
        Text(text = "Title", modifier = Modifier.constrainAs(title) {
            start.linkTo(surname.end, margin = 8.dp)
            top.linkTo(parent.top, margin = 0.dp)
        })
        Text(text = "Description", modifier = Modifier.constrainAs(description) {
            start.linkTo(surname.end, margin = 8.dp)
            top.linkTo(title.bottom, margin = 0.dp)
        })
    }
}