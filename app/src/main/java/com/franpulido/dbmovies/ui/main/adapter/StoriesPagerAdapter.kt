package com.franpulido.dbmovies.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.franpulido.dbmovies.ui.common.basicDiffUtil
import com.franpulido.dbmovies.ui.main.listener.ListenerResult
import com.franpulido.dbmovies.ui.models.MovieModel
import com.franpulido.dbmovies.ui.story.StoryViewFragment

class StoriesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var movies: List<MovieModel> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id })

    private lateinit var listener: ListenerResult

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = StoryViewFragment.newInstance(movies[position])
        fragment.setListener(listener)
        return fragment
    }

    fun setListener(listener: ListenerResult) {
        this.listener = listener
    }
}