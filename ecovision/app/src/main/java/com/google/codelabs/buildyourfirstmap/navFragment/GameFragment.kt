package com.google.codelabs.buildyourfirstmap.navFragment

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.codelabs.buildyourfirstmap.MemoryGame
import com.google.codelabs.buildyourfirstmap.QuizGame
import com.google.codelabs.buildyourfirstmap.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View = inflater.inflate(R.layout.fragment_game, container, false)
        val game1 : RelativeLayout = view.findViewById(R.id.game1);
        game1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // do something
                val intent = Intent(activity, QuizGame::class.java)
                startActivity(intent)

            }
        })
        val game2 : RelativeLayout = view.findViewById(R.id.game2);
        game2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // do something
                val intent = Intent(activity,  MemoryGame::class.java)
                startActivity(intent)

            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}