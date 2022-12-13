package ru.korobeynikov.p04mockobjects

open class BasePresenter<V> {

    private var view: V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun getView(): V? {
        return view
    }

    fun detachView() {
        view = null
    }
}