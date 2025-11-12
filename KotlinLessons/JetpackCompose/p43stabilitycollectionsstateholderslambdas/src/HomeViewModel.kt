package ru.korobeynikov.p43stabilitycollectionsstateholderslambdas

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SomeState())
    val state = _state.asStateFlow()

    fun onCounterClick() {
        _state.update {
            it.copy(
                clicksCount = it.clicksCount + 1,
                list = it.list.toPersistentList().add("${it.clicksCount + 1}")
            )
        }
    }

    private val _stateClassWithList = MutableStateFlow(SomeStateClassWithList())
    val stateClassWithList = _stateClassWithList.asStateFlow()

    fun onCounterClickClassWithList() {
        _stateClassWithList.update {
            it.copy(
                clicksCount = it.clicksCount + 1,
                myClass = MyClass(it.myClass.list + "${it.clicksCount + 1}")
            )
        }
    }

    private val _stateList = MutableStateFlow(SomeStateList())
    val stateList = _stateList.asStateFlow()

    fun onCounterClickList() {
        _stateList.update {
            it.copy(
                clicksCount = it.clicksCount + 1
            )
        }
    }
}