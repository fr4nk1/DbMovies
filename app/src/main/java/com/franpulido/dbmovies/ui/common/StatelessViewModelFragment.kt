package com.franpulido.dbmovies.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class StatelessViewModelFragment<BINDING : ViewBinding, VE, VM : StatelessViewModel<VE>> : Fragment() {

    abstract val viewBinding: (LayoutInflater, ViewGroup?) -> BINDING

    private var _binding: BINDING? = null

    protected val binding: BINDING get() = _binding!!

    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = viewBinding(inflater, container)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()
        viewModel.viewEvents.consumeAsFlow()
            .onEach { handleViewEvent(it) }
            .launchIn(lifecycleScope)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        setupUI()
    }

    protected abstract fun setupUI()

    protected abstract fun handleViewEvent(viewEvent: VE)
}
