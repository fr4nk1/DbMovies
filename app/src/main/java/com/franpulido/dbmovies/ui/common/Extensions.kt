package com.franpulido.dbmovies.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.franpulido.dbmovies.R
import kotlin.properties.Delegates

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity)
    startActivity(intentFor<T>(body), options.toBundle())
}

inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }

fun View.hideWithFadeOut() {
    if (visibility == View.GONE) return
    val animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    startAnimation(animFadeIn)
    visibility = View.GONE
}

fun View.showWithFadeIn() {
    if (visibility == View.VISIBLE) return
    val animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    startAnimation(animFadeIn)
    visibility = View.VISIBLE
}