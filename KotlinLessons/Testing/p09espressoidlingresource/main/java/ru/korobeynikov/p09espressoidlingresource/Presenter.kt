package ru.korobeynikov.p09espressoidlingresource

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class Presenter(private val dataRepository: DataRepository,
                private val simpleIdlingResource: SimpleIdlingResource) {

    private var view: MainActivity? = null

    fun attachView(view: MainActivity) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun onButtonClick() {
        simpleIdlingResource.setIdleState(false)
        dataRepository.loadData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy {
                simpleIdlingResource.setIdleState(true)
                view?.setData(it)
            }
    }
}