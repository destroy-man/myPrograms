package ru.korobeynikov.p43stabilitycollectionsstateholderslambdas

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.collections.immutable.ImmutableList

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    //Lambda without stable parameter
    val holder = MyHolder()
    MyFunctionSimpleLambda {
        holder.toggle()
    }
}

@Composable
fun HomeScreenLambdaWithStableParameter() {
    val holder = MyHolderStable()
    MyFunctionSimpleLambda {
        holder.toggle()
    }
}

@Composable
fun HomeScreenLambdaWithList() {
    MyFunctionLambdaWithList {
        Log.d(TAG, "test")
    }
}

@Composable
fun MyFunctionLambdaWithList(onClick: (List<String>) -> Unit) {
    onClick(listOf())
}

@Composable
fun HomeScreenSimpleLambda() {
    MyFunctionSimpleLambda {
        Log.d(TAG, "test")
    }
}

@Composable
fun MyFunctionSimpleLambda(onClick: () -> Unit) {
    onClick()
}

@Composable
fun HomeScreenHolder() {
    Column {
        val holder = MyHolder()
        MyFunctionHolder(holder)
    }
}

@Composable
fun MyFunctionHolder(holder: MyHolder) {
    Text("test ${holder.isVisible.value}")
}

@Composable
fun HomeScreenHolderStable() {
    Column {
        val holder = MyHolderStable()
        MyFunctionHolderStable(holder)
    }
}

@Composable
fun MyFunctionHolderStable(holder: MyHolderStable) {
    Text("test ${holder.isVisible}")
}

@Composable
fun HomeScreenImmutableList(viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        val state = viewModel.state.collectAsState().value
        Log.d(TAG, "HomeScreen $state")

        Text(
            text = "Click ${state.clicksCount}",
            fontSize = 20.sp,
            modifier = Modifier.clickable(onClick = viewModel::onCounterClick)
        )

        MyFunctionImmutableList(state.list)
    }
}

@Composable
fun MyFunctionImmutableList(value: ImmutableList<String>) {
    Text("test $value")
    Log.d(TAG, "MyFunction")
}

@Composable
fun HomeScreenClassWithList(viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        val state = viewModel.stateClassWithList.collectAsState().value
        Log.d(TAG, "HomeScreen $state")

        Text(
            text = "Click ${state.clicksCount}",
            fontSize = 20.sp,
            modifier = Modifier.clickable(onClick = viewModel::onCounterClickClassWithList)
        )

        MyFunctionClassWithList(state.myClass)
    }
}

@Composable
fun MyFunctionClassWithList(value: MyClass) {
    Text("test")
    Log.d(TAG, "MyFunction $value")
}

@Composable
fun HomeScreenList(viewModel: HomeViewModel = hiltViewModel()) {
    Column {
        val state = viewModel.stateList.collectAsState().value
        Log.d(TAG, "HomeScreen $state")

        Text(
            text = "Click ${state.clicksCount}",
            fontSize = 20.sp,
            modifier = Modifier.clickable(onClick = viewModel::onCounterClickList)
        )

        MyFunctionList(state.list)
    }
}

@Composable
fun MyFunctionList(value: List<String>) {
    Text("test")
    Log.d(TAG, "MyFunction $value")
}