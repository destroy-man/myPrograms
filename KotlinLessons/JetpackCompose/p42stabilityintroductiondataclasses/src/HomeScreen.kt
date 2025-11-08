package ru.korobeynikov.p42stabilityintroductiondataclasses

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

const val TAG = "myLogs"

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    //Recomposition with Data class
    Column {
        val state = viewModel.state.collectAsState().value
        Log.d(TAG, "HomeScreen $state")

        Text(
            text = "Click ${state.clicksCount}",
            fontSize = 20.sp,
            modifier = Modifier.clickable(onClick = viewModel::onCounterClick)
        )

        MyFunction(state.myClass)
    }
}

@Composable
fun MyFunction(value: MyClass) {
    Text("test")
    Log.d(TAG, "MyFunction $value")
}

@Composable
fun HomeScreenMyFunctionBasicType(viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        val state = viewModel.state.collectAsState().value
        Log.d(TAG, "Home screen ${state.clicksCount}")

        Text(
            text = "Click ${state.clicksCount}",
            fontSize = 20.sp,
            modifier = Modifier.clickable(onClick = viewModel::onCounterClick)
        )

        MyFunctionBasicType(state.clicksCount)
    }
}

@Composable
fun HomeScreenMyFunctionConstant(viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        val state = viewModel.state.collectAsState().value
        Log.d(TAG, "Home screen ${state.clicksCount}")

        Text(
            text = "Click ${state.clicksCount}",
            fontSize = 20.sp,
            modifier = Modifier.clickable(onClick = viewModel::onCounterClick)
        )

        MyFunctionBasicType(100)
    }
}

@Composable
fun MyFunctionBasicType(value: Int) {
    Text("test")
    Log.d(TAG, "MyFunction $value")
}

@Composable
fun MyFunctionWithoutParameters() {
    Text("test")
    Log.d(TAG, "MyFunction")
}