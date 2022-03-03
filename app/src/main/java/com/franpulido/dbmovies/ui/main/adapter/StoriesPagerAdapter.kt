package com.franpulido.dbmovies.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.franpulido.dbmovies.data.models.MovieModel
import com.franpulido.dbmovies.ui.common.basicDiffUtil
import com.franpulido.dbmovies.ui.story.StoryViewFragment

class StoriesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var movies: List<MovieModel> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id })


    override fun getItemCount(): Int {
        return movies.size
    }

    override fun createFragment(position: Int): Fragment {
        return StoryViewFragment.newInstance(movies[position])
    }
}