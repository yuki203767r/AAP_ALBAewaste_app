package com.google.codelabs.buildyourfirstmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.codelabs.buildyourfirstmap.databinding.ActivityEducationalVideosBinding
import android.R

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import androidx.annotation.NonNull

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener





class EducationalVideos : AppCompatActivity() {
    private lateinit var binding: ActivityEducationalVideosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_educational_videos)
        binding = ActivityEducationalVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val youTubePlayerView = findViewById<YouTubePlayerView>(binding.youtubePlayerView.id)
        lifecycle.addObserver(youTubePlayerView)

    }
}