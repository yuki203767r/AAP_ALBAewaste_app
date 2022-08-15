package com.google.codelabs.buildyourfirstmap.navFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.codelabs.buildyourfirstmap.*
import com.google.codelabs.buildyourfirstmap.BuildConfig.GOOGLE_MAPS_API_KEY
import com.google.codelabs.buildyourfirstmap.R
import com.google.codelabs.buildyourfirstmap.place.Place
import com.google.codelabs.buildyourfirstmap.place.PlaceRenderer
import com.google.codelabs.buildyourfirstmap.place.PlacesReader
import com.google.gson.Gson
import com.google.maps.GeoApiContext
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.addCircle
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import kotlinx.android.synthetic.main.fragment_map.*
import okhttp3.OkHttpClient
import okhttp3.Request





class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_map, container, false)
        val square1 : RelativeLayout = view.findViewById(R.id.square1);
        square1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // do something
                val intent = Intent(activity, map::class.java)
                startActivity(intent)

            }
        })
        val square2 : RelativeLayout = view.findViewById(R.id.square2);
        square2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // do something
                val intent = Intent(activity,  map::class.java)
                startActivity(intent)

            }
        })
        return view
    }

}