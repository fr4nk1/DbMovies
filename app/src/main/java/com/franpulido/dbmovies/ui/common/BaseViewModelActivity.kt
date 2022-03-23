package com.franpulido.dbmovies.ui.common

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseViewModelActivity<
    BINDING : ViewBinding,
    VIEW_STATE,
    VIEW_EVENT,
    VIEW_MODEL : BaseViewModel<VIEW_STATE, VIEW_EVENT>> :
    StatelessViewModelActivity<BINDING, VIEW_EVENT, VIEW_MODEL>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.observe(
            this,
            Observer(this::renderViewState)
        )
    }

    protected abstract fun renderViewState(viewState: VIEW_STATE)
}
