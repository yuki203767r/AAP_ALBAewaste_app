package com.google.codelabs.buildyourfirstmap.navFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.codelabs.buildyourfirstmap.R
import com.google.codelabs.buildyourfirstmap.SliderAdapter
import com.google.codelabs.buildyourfirstmap.databinding.FragmentEducationalBinding
import com.smarteist.autoimageslider.SliderView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EducationalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EducationalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var imageUrl: ArrayList<String>

    // on below line we are creating
    // a variable for our slider view.
    lateinit var sliderView: SliderView

    // on below line we are creating
    // a variable for our slider adapter.
    lateinit var sliderAdapter: SliderAdapter

//    private lateinit var binding: ActivityEducationalVideosBinding
    private lateinit var binding: FragmentEducationalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_educational_videos)
//        binding = ActivityEducationalVideosBinding.inflate(layoutInflater)


    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//    binding = FragmentEducationalBinding.inflate(layoutInflater)
//    set(binding.root)


    // on below line we are initializing our slier view.
//    sliderView = findViewById(com.google.codelabs.buildyourfirstmap.R.id.aslider)

    val view: View = inflater.inflate(R.layout.fragment_educational, container, false)
    val sliderView = view.findViewById<View>(R.id.aslider) as SliderView

//    sliderView = inflater.inflate(R.layout.fragment_educational,null).findViewById(R.id.aslider)

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

    sliderView.setSliderAdapter(sliderAdapter)

    return view

//    return inflater.inflate(R.layout.fragment_educational, container, false)

}

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment EducationalFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            EducationalFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}