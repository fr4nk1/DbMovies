package com.franpulido.dbmovies.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE, VIEW_EVENT> : StatelessViewModel<VIEW_EVENT>() {

    private val _viewState = MutableLiveData<VIEW_STATE>()
    val viewState: LiveData<VIEW_STATE>
        get() = _viewState

    override fun init() {
        viewModelScope.launch {
            val viewState = getInitialViewState()
            _viewState.value = viewState

            extraInitializationSteps()
        }
    }

    protected abstract suspend fun getInitialViewState(): VIEW_STATE

    protected fun updateViewState(update: VIEW_STATE.() -> VIEW_STATE) {
        val newState = update(getViewState())
        _viewState.value = newState
    }

    protected fun getViewState(): VIEW_STATE = _viewState.value!!
}
