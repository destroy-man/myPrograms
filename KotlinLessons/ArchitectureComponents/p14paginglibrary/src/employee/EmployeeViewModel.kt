package ru.korobeynikov.p14paginglibrary.employee

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class EmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val pager = Pager(
        PagingConfig(
            100, //количество элементов для одной загрузки данных
            enablePlaceholders = false, //указывает нужно ли включать placeholder-ы (заглушки для подгружаемых элементов)
            initialLoadSize = 100 //количество элементов для начальной загрузки данных
            //prefetchDistance = 10 //количество элементов, которое должно остаться чтобы подгрузить следующую порцию данных
        ),
        //initialKey = 3 //указывает с какой порции данных начинать загружать данные
    ) {
        EmployeeDataSource(employeeRepository, 10)
    }
    val pagerFlow = pager.flow.cachedIn(scope)
}