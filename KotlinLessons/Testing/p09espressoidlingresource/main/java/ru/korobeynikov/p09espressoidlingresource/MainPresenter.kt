package ru.korobeynikov.p09espressoidlingresource

class MainPresenter(private val dataRepository: DataRepository) {

    private var view: MainActivity? = null
    private lateinit var simpleIdlingResource: SimpleIdlingResource

    fun attachView(view: MainActivity) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun onButtonClick() {
        simpleIdlingResource.setIdleState(false)
        dataRepository.loadData().subscribe { s ->
            simpleIdlingResource.setIdleState(true)
            view?.setData(s)
        }
    }

    fun setIdlingResource(simpleIdlingResource: SimpleIdlingResource) {
        this.simpleIdlingResource = simpleIdlingResource
    }
}