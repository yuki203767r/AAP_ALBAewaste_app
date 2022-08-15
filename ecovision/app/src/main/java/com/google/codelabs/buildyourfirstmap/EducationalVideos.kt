package com.google.codelabs.buildyourfirstmap

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.codelabs.buildyourfirstmap.databinding.ActivityEducationalVideosBinding
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_rewards.*


class EducationalVideos : AppCompatActivity() {
    // on below line we are creating a variable
    // for our array list for storing our images.
    lateinit var imageUrl: ArrayList<String>

    // on below line we are creating
    // a variable for our slider view.
    lateinit var sliderView: SliderView

    // on below line we are creating
    // a variable for our slider adapter.
    lateinit var sliderAdapter: SliderAdapter

    private lateinit var binding: ActivityEducationalVideosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_educational_videos)
        binding = ActivityEducationalVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // on below line we are initializing our slier view.
        sliderView = findViewById(com.google.codelabs.buildyourfirstmap.R.id.aslider)

        // on below line we are initializing
        // our image url array list.
        var image = intArrayOf(com.google.codelabs.buildyourfirstmap.R.drawable.info1, com.google.codelabs.buildyourfirstmap.R.drawable.info2)
        // on below line we are adding data to our image url array list.

        // on below line we are initializing our
        // slider adapter and adding our list to it.
        sliderAdapter = SliderAdapter(image )

        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        sliderView.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        sliderView.scrollTimeInSec = 4

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        sliderView.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        sliderView.startAutoCycle()

//        RewardsBtn.setOnClickListener {
//            var myIntent = Intent(this, Rewards::class.java)
//            startActivity(myIntent)
//        }
//
//        AccountBtn.setOnClickListener {
//            var myIntent = Intent(this, Account::class.java)
//            startActivity(myIntent)
//        }
//
//        Games.setOnClickListener {
//            var myIntent = Intent(this, GameListPage::class.java)
//            startActivity(myIntent)
//        }
//
//        Mapbtn.setOnClickListener {
//            var myIntent = Intent(this, map::class.java)
//            startActivity(myIntent)
//        }
    }
}