package com.franpulido.dbmovies.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class StatelessViewModelActivity<
    BINDING : ViewBinding,
    VIEW_EVENT,
    VIEW_MODEL : StatelessViewModel<VIEW_EVENT>> :
    AppCompatActivity() {

    abstract val viewBinding: (LayoutInflater) -> BINDING
    protected lateinit var binding: BINDING

    protected abstract val viewModel: VIEW_MODEL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBinding(layoutInflater)
        setContentView(binding.root)

        setupUI()

        viewModel.init()
        viewModel.viewEvents
            .consumeAsFlow()
            .onEach { handleViewEvent(it) }
            .launchIn(lifecycleScope)
    }

    protected abstract fun setupUI()

    protected abstract fun handleViewEvent(viewEvent: VIEW_EVENT)
}
