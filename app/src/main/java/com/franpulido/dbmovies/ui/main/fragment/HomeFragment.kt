package com.franpulido.dbmovies.ui.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.franpulido.dbmovies.databinding.FragmentHomeBinding
import com.franpulido.dbmovies.ui.common.BaseViewModelFragment
import com.franpulido.dbmovies.ui.main.adapter.StoriesPagerAdapter

private typealias HomeFragmentParent = BaseViewModelFragment<FragmentHomeBinding,
        HomeViewModel.ViewState,
        HomeViewModel.ViewEvent,
        HomeViewModel>


class HomeFragment : HomeFragmentParent() {

    private lateinit var storiesPagerAdapter: StoriesPagerAdapter

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
                binding.viewPagerStories.adapter = storiesPagerAdapter
            }
        }
    }

    override fun setupUI() {}

    override fun handleViewEvent(viewEvent: HomeViewModel.ViewEvent) {}

}