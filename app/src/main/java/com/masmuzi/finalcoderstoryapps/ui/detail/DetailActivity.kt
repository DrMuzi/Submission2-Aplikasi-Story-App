package com.masmuzi.finalcoderstoryapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.masmuzi.finalcoderstoryapps.R
import com.masmuzi.finalcoderstoryapps.data.local.entity.Story
import com.masmuzi.finalcoderstoryapps.databinding.ActivityDetailBinding
import com.masmuzi.finalcoderstoryapps.utils.setLocalDateFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Detail Activity"

        val stories = intent.getParcelableExtra<Story>(EXTRA_STORY)
        binding.apply {
            tvDetailUsername.text = stories?.name
            tvCreatedAt.setLocalDateFormat(stories?.createdAt.toString())
            tvDetailDescription.text = stories?.description
            tvLatitude.text = stories?.lat.toString()
            tvLongitude.text = stories?.lon.toString()
        }
        Glide.with(this)
            .load(stories?.photoUrl)
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_error)
            .into(binding.imgAvatar)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}