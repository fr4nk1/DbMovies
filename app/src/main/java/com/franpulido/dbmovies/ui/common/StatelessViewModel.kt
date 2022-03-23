package com.franpulido.dbmovies.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

abstract class StatelessViewModel<VIEW_EVENT> : ViewModel() {

    private val _viewEvents = Channel<VIEW_EVENT>()
    val viewEvents: ReceiveChannel<VIEW_EVENT>
        get() = _viewEvents

    /**
     * Function for scaffolding the ViewModel.
     * This shouldn't be extended if you don't know what you are doing.
     */
    open fun init() {
        viewModelScope.launch {
            extraInitializationSteps()
        }
    }

    /**
     * Reserved function for extra steps needed on View Model initialization.
     * Trackings, navigation, sending view events, etc.
     */
    protected open suspend fun extraInitializationSteps() {}

    override fun onCleared() {
        super.onCleared()

        _viewEvents.close()
    }

    protected suspend fun sendViewEvent(viewEvent: VIEW_EVENT) {
        _viewEvents.send(viewEvent)
    }
}
