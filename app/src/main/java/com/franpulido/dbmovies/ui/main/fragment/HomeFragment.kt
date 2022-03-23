package com.franpulido.dbmovies.ui.main.fragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.franpulido.dbmovies.databinding.FragmentHomeBinding
import com.franpulido.dbmovies.ui.common.BaseViewModelFragment
import com.franpulido.dbmovies.ui.detail.MovieActivity
import com.franpulido.dbmovies.ui.main.MainViewModel
import com.franpulido.dbmovies.ui.main.adapter.StoriesPagerAdapter

private typealias HomeFragmentParent = BaseViewModelFragment<FragmentHomeBinding,
        MainViewModel.ViewState,
        MainViewModel.ViewEvent,
        MainViewModel>


class HomeFragment : HomeFragmentParent() {

    private lateinit var storiesPagerAdapter: StoriesPagerAdapter

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentHomeBinding = { layoutInflater, viewGroup ->
        FragmentHomeBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: MainViewModel by activityViewModels()

    override fun renderViewState(viewState: MainViewModel.ViewState) {
        binding.progress.visibility =
            if (viewState is MainViewModel.ViewState.Loading) View.VISIBLE else View.GONE

        when(viewState){
            is MainViewModel.ViewState.Content -> {
                storiesPagerAdapter = StoriesPagerAdapter(this)
                storiesPagerAdapter.movies = viewState.movies.results
                binding.viewPagerStories.adapter = storiesPagerAdapter
            }
        }
    }

    override fun setupUI() {}

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvent) {
        when (viewEvent) {
            is MainViewModel.ViewEvent.Navigation -> {
                val intent = Intent(activity, MovieActivity::class.java)
                intent.putExtra(MovieActivity.MOVIE, viewEvent.movie.id)
                launchDetailActivity.launch(intent)
            }
        }
    }

    private var launchDetailActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.initUi()
            }
        }

}