package com.franpulido.dbmovies.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.databinding.ActivityMainBinding
import com.franpulido.dbmovies.ui.common.BaseViewModelActivity
import com.franpulido.dbmovies.ui.detail.MovieActivity
import com.franpulido.dbmovies.ui.main.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

private typealias MainParent = BaseViewModelActivity<ActivityMainBinding,
        MainViewModel.ViewState,
        MainViewModel.ViewEvent,
        MainViewModel>

@AndroidEntryPoint
class MainActivity : MainParent() {

    private var menuItemAlpha: MenuItem? = null
    private var menuItemVote: MenuItem? = null
    private lateinit var adapter: MoviesAdapter


    override val viewModel: MainViewModel by viewModels()

    override val viewBinding: (LayoutInflater) -> ActivityMainBinding = {
        ActivityMainBinding.inflate(it)
    }

    override fun setupUI() {
        setSupportActionBar(binding.toolbar)

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.layoutRecycler.recycler.adapter = adapter

        binding.fabMode.setOnClickListener {
            if (binding.layoutRecycler.rlRootRecycler.isVisible) {
                viewModel.flipMode()
            } else {
                viewModel.listMode()
            }
        }
    }

    private fun setFabIcon(drawable: Int) {
        binding.fabMode.setImageDrawable(ContextCompat.getDrawable(this, drawable))
    }

    override fun renderViewState(viewState: MainViewModel.ViewState) {
        binding.layoutRecycler.progress.visibility =
            if (viewState is MainViewModel.ViewState.Loading) View.VISIBLE else View.GONE

        when (viewState) {
            is MainViewModel.ViewState.Content -> adapter.movies = viewState.movies.results
            MainViewModel.ViewState.Error -> binding.layoutRecycler.layoutError.viewError.visibility =
                View.VISIBLE
            MainViewModel.ViewState.Init -> viewModel.initUi()
            MainViewModel.ViewState.HideIconAlpha -> {
                hideIconItemAlpha()
            }
            MainViewModel.ViewState.HideIconVote -> {
                hideIconItemVote()
            }
            MainViewModel.ViewState.ShowIconAlpha -> {
                showIconItemAlpha()
            }
            MainViewModel.ViewState.ShowIconVote -> {
                showIconItemVote()
            }
        }
    }

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvent) {
        when (viewEvent) {
            is MainViewModel.ViewEvent.Navigation -> {
                val intent = Intent(this, MovieActivity::class.java)
                intent.putExtra(MovieActivity.MOVIE, viewEvent.movie.id)
                launchDetailActivity.launch(intent)
            }
            MainViewModel.ViewEvent.FlipMode -> {
                hideRecyclerView()
                showViewPager()
                setFabIcon(R.drawable.ic_list)
            }
            MainViewModel.ViewEvent.ListMode -> {
                showRecycleView()
                hideViewPager()
                setFabIcon(R.drawable.ic_story)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuItemVote = menu.findItem(R.id.menu_sort_vote)
        menuItemAlpha = menu.findItem(R.id.menu_sort_alpha)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_info -> {
                InfoBottomSheetFragment.newInstance().show(supportFragmentManager, "")
                true
            }
            R.id.menu_sort_vote -> {
                viewModel.sortByVote()
                true
            }
            R.id.menu_sort_alpha -> {
                viewModel.sortByAlpha()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private var launchDetailActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.initUi()
            }
        }

    private fun showRecycleView() {
        binding.layoutRecycler.rlRootRecycler.visibility = View.VISIBLE
    }

    private fun showViewPager() {
        binding.layoutViewPager.flRootViewPager.visibility = View.VISIBLE
    }

    private fun hideViewPager() {
        binding.layoutViewPager.flRootViewPager.visibility = View.GONE
    }

    private fun hideRecyclerView() {
        binding.layoutRecycler.rlRootRecycler.visibility = View.GONE
    }

    private fun showIconItemAlpha() {
        menuItemAlpha?.isEnabled = true
        menuItemAlpha?.isVisible = true
    }

    private fun showIconItemVote() {
        menuItemVote?.isEnabled = true
        menuItemVote?.isVisible = true
    }

    private fun hideIconItemAlpha() {
        menuItemAlpha?.isEnabled = false
        menuItemAlpha?.isVisible = false
    }

    private fun hideIconItemVote() {
        menuItemVote?.isEnabled = false
        menuItemVote?.isVisible = false
    }

}