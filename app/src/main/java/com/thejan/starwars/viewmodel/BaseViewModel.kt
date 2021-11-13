package com.thejan.starwars.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Thejan Thrimanna on 11/12/21.
 */
open class BaseViewModel : ViewModel() {
    var state: MutableLiveData<ViewModelState>? = null
    private lateinit var disposables: CompositeDisposable
    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        initRx()
    }

    private fun initRx() {
        disposables = CompositeDisposable()
    }

    @Synchronized
    fun addDisposable(disposable: Disposable?) {
        if (disposable == null) return
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) disposables.dispose()
    }
}