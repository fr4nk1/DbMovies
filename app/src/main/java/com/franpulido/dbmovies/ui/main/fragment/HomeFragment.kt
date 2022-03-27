package com.franpulido.dbmovies.ui.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.franpulido.dbmovies.databinding.FragmentHomeBinding
import com.franpulido.dbmovies.ui.common.BaseViewModelFragment
import com.franpulido.dbmovies.ui.main.adapter.StoriesPagerAdapter
import com.franpulido.dbmovies.ui.main.listener.ListenerResult

private typealias HomeFragmentParent = BaseViewModelFragment<FragmentHomeBinding,
        HomeViewModel.ViewState,
        HomeViewModel.ViewEvent,
        HomeViewModel>


class HomeFragment : HomeFragmentParent(), ListenerResult {

    private lateinit var storiesPagerAdapter: StoriesPagerAdapter
    private lateinit var listener : ListenerResult

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentHomeBinding = { layoutInflater, viewGroup ->
        FragmentHomeBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: HomeViewModel by activityViewModels()

    override fun renderViewState(viewState: HomeViewModel.ViewState) {
        binding.progress.visibility =
            if (viewState is HomeViewModel.ViewState.Loading) View.VISIBLE else View.GONE

        when(viewState){
            HomeViewModel.ViewState.Init -> viewModel.initUi()
            is HomeViewModel.ViewState.Content -> {
                storiesPagerAdapter = StoriesPagerAdapter(this)
                storiesPagerAdapter.movies = viewState.movies.results
                storiesPagerAdapter.setListener(this)
                binding.viewPagerStories.adapter = storiesPagerAdapter
            }
        }
    }

    override fun setupUI() {}

    override fun handleViewEvent(viewEvent: HomeViewModel.ViewEvent) {}

    override fun actionResult() {
        listener.actionResult()
    }

    fun setListener(listener: ListenerResult) {
        this.listener = listener
    }

}