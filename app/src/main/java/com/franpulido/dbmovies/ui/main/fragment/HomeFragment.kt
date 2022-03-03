package com.franpulido.dbmovies.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.franpulido.dbmovies.R
import com.franpulido.dbmovies.ui.common.BaseFragment
import com.franpulido.dbmovies.ui.main.MainViewModel
import com.franpulido.dbmovies.ui.main.adapter.StoriesPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var storiesPagerAdapter: StoriesPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        progress.visibility =
            if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> {
                storiesPagerAdapter = StoriesPagerAdapter(this)
                storiesPagerAdapter.movies = model.movies.results
                view_pager_stories.adapter = storiesPagerAdapter
            }
            MainViewModel.UiModel.Init -> viewModel.initUi()
        }
    }

}