package com.franpulido.dbmovies.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.databinding.ActivityMainBinding
import com.franpulido.dbmovies.ui.detail.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var menuItemAlpha: MenuItem? = null
    private var menuItemVote: MenuItem? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MoviesAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
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

    private fun updateUi(model: MainViewModel.UiModel) {
        binding.progress.visibility =
            if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.movies = model.movies
            is MainViewModel.UiModel.Navigation -> {
                val intent = Intent(this, MovieActivity::class.java)
                intent.putExtra(MovieActivity.MOVIE, model.movie.id)
                launchDetailActivity.launch(intent)
            }
            MainViewModel.UiModel.Init -> viewModel.initUi()
            MainViewModel.UiModel.Error -> binding.layoutError.viewError.visibility = View.VISIBLE
            MainViewModel.UiModel.HideIconAlpha -> {
                menuItemAlpha?.isEnabled = false
                menuItemAlpha?.isVisible = false
            }
            MainViewModel.UiModel.HideIconVote -> {
                menuItemVote?.isEnabled = false
                menuItemVote?.isVisible = false
            }
            MainViewModel.UiModel.ShowIconAlpha -> {
                menuItemAlpha?.isEnabled = true
                menuItemAlpha?.isVisible = true
            }
            MainViewModel.UiModel.ShowIconVote -> {
                menuItemVote?.isEnabled = true
                menuItemVote?.isVisible = true
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