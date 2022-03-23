package com.franpulido.dbmovies.ui.common

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseViewModelFragment<
    BINDING : ViewBinding,
    VIEW_STATE,
    VIEW_EVENT,
    VIEW_MODEL : BaseViewModel<VIEW_STATE, VIEW_EVENT>> :
    StatelessViewModelFragment<BINDING, VIEW_EVENT, VIEW_MODEL>() {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        viewModel.viewState.observe(
            viewLifecycleOwner,
            Observer { renderViewState(it) }
        )
    }

    protected abstract fun renderViewState(viewState: VIEW_STATE)
}
