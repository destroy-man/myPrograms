package ru.korobeynikov.p42stabilityintroductiondataclasses

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
                clicksCount = it.clicksCount + 1
            ).apply {
                myClass.i = clicksCount
            }
        }
    }

    /*stable version
    fun onCounterClick(){
        _state.update {
             it.copy(
                 clicksCount = it.clicksCount+1,
                 myClass = MyClass(it.clicksCount+1)
             )
        }
    }
    */
}

data class SomeState(
    val clicksCount: Int = 0,
    val myClass: MyClass = MyClass(100),
)


data class SomeStateStable(
    val clicksCount: Int = 0,
    val myClass: MyClassVal = MyClassVal(100),
)