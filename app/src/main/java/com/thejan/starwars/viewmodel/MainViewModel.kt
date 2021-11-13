package com.thejan.starwars.viewmodel

import androidx.lifecycle.MutableLiveData
import com.thejan.starwars.model.Planet
import com.thejan.starwars.network.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Thejan Thrimanna on 11/13/21.
 */
class MainViewModel : BaseViewModel() {
    val planets: MutableLiveData<List<Planet>> by lazy {
        MutableLiveData<List<Planet>>()
    }
    val loadingMore: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val pageNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        state = MutableLiveData()
    }

    fun getPlanets(page: Int) {
        addDisposable(
            ServiceGenerator.instance.getPlanets(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state!!.postValue(ViewModelState.loading()) }
                .subscribe({
                    if (it.next != null) {
                        loadingMore.postValue(true)
                        pageNumber.postValue(page + 1)
                    } else {
                        loadingMore.postValue(false)
                    }
                    planets.postValue(it.results)
                    if (it.results.isNullOrEmpty())
                        state!!.postValue(ViewModelState.list_empty())
                    else
                        state!!.postValue(ViewModelState.success())
                },
                    {
                        message.postValue(it.message)
                        state!!.postValue(ViewModelState.error())
                    }

                )
        )
    }
}