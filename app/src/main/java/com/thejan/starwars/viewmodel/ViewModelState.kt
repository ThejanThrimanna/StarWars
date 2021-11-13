package com.thejan.starwars.viewmodel

/**
 * Created by Thejan Thrimanna on 11/10/21.
 */

class ViewModelState constructor(
    var status: Status,
    var error: Throwable? = null
) {
    companion object {
        fun loading(): ViewModelState {
            return ViewModelState(Status.LOADING)
        }

        fun success(): ViewModelState {
            return ViewModelState(Status.SUCCESS)
        }

        fun error(): ViewModelState {
            return ViewModelState(Status.ERROR)
        }

        fun list_empty(): ViewModelState {
            return ViewModelState(Status.LIST_EMPTY)
        }
    }
}