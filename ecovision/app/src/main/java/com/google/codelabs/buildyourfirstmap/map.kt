package com.google.codelabs.buildyourfirstmap

import android.Manifest
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
import com.google.codelabs.buildyourfirstmap.BuildConfig.GOOGLE_MAPS_API_KEY
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
import okhttp3.OkHttpClient
import okhttp3.Request


private val mGeoApiContext: GeoApiContext? = null

lateinit var MapFragment : SupportMapFragment
lateinit var client: FusedLocationProviderClient
var currentLocation:Location? = null
val REQUEST_CODE = 101
lateinit var MMap: GoogleMap

class map : AppCompatActivity(),OnMapReadyCallback {


    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }


    // [START maps_android_add_map_codelab_ktx_coroutines]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val MapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment


        client = LocationServices.getFusedLocationProviderClient(this)

//        checkLocationPermission()

        lifecycleScope.launchWhenCreated {
            // Get map
            MMap = MapFragment.awaitMap()

            addClusteredMarkers(MMap)

            // Wait for map to finish loading
            MMap.awaitMapLoad()

//            // Ensure all places are visible in the map
//            val bounds = LatLngBounds.builder()
//            places.forEach { bounds.include(it.latLng) }
//
//
//
//            val currentLocation = LatLng(1.3695,103.8484)
//            val recycleBinLocationMock = LatLng(1.369215,103.846436)
//            MMap.addMarker(MarkerOptions().position(currentLocation).title("You"))
//            //Add marker for recycle bin.
//            MMap.addMarker(MarkerOptions().position(recycleBinLocationMock).title("RecycleBin 1").icon(binIcon))
//
//            MMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,17f))
//
//            Log.d("GoogleMap", "before URL")
//            val URL = getDirectionURL(currentLocation,recycleBinLocationMock)
//            Log.d("GoogleMap", "URL : $URL")
//            GetDirection(URL).execute()


            //current location
//        val latLng = LatLng(currentLocation!!.latitude.toDouble(), currentLocation!!.longitude.toDouble())
//        val markerOptions = MarkerOptions().position(latLng).title("I Am Here!")
//        MMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        MMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f))
//        MMap.addMarker(markerOptions)

            val bounds = LatLngBounds.builder()
            places.forEach { bounds.include(it.latLng) }



            val currentLocation = LatLng(1.3800,103.8489)
            val recycleBinLocationMock = LatLng(1.3730,103.8476)
            MMap.addMarker(MarkerOptions().position(currentLocation).title("You"))
            //Add marker for recycle bin.
            MMap.addMarker(MarkerOptions().position(recycleBinLocationMock).title("RecycleBin 1").icon(binIcon))

            MMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,17f))

            val URL = getDirectionURL(currentLocation,recycleBinLocationMock)
//        val URL = getDirectionURL(latLng,recycleBinLocationMock)

            GetDirection(URL).execute()
            MMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                val markerName = marker.title
                Toast.makeText(
                    this@map,
                    "Clicked location is $markerName",
                    Toast.LENGTH_SHORT
                ).show()
                onInfoWindowClick(marker)
                false
            }
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkLocationPermission()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onMapReady(googleMap: GoogleMap){
        MMap = googleMap

        //current location
//        val latLng = LatLng(currentLocation!!.latitude.toDouble(), currentLocation!!.longitude.toDouble())
//        val markerOptions = MarkerOptions().position(latLng).title("I Am Here!")
//        MMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        MMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17f))
//        MMap.addMarker(markerOptions)

        val bounds = LatLngBounds.builder()
        places.forEach { bounds.include(it.latLng) }



        val currentLocation = LatLng(1.3800,103.8489)
        val recycleBinLocationMock = LatLng(1.3730,103.8476)
        MMap.addMarker(MarkerOptions().position(currentLocation).title("You"))
        //Add marker for recycle bin.
        MMap.addMarker(MarkerOptions().position(recycleBinLocationMock).title("RecycleBin 1").icon(binIcon))

        MMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,17f))
        val URL = getDirectionURL(currentLocation,recycleBinLocationMock)
//        val URL = getDirectionURL(latLng,recycleBinLocationMock)

        GetDirection(URL).execute()
    }


    private fun checkLocationPermission() {
        val task = client.getLastLocation()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return

        }
        task.addOnSuccessListener { location : Location? ->
            if (location != null){
                currentLocation = location
                Log.d("current" , " ${location.longitude} ${location.latitude}")

                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?)
                supportMapFragment!!.getMapAsync(this)
            }
        }
    }

    // [END maps_android_add_map_codelab_ktx_coroutines]





    /**
     * Adds markers to the map with clustering support.
     */
    private fun addClusteredMarkers(googleMap: GoogleMap) {
        // Create the ClusterManager class and set the custom renderer
        val clusterManager = ClusterManager<Place>(this, googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                this,
                googleMap,
                clusterManager
            )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        // Add the places to the ClusterManager
        clusterManager.addItems(places)
        clusterManager.cluster()

        // Show polygon
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(googleMap, item)
            return@setOnClusterItemClickListener false
        }

        // When the camera starts moving, change the alpha value of the marker to translucent
        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        googleMap.setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that re-clustering
            // can be performed when the camera stops moving
            clusterManager.onCameraIdle()
        }
    }

    private var circle: Circle? = null

    // [START maps_android_add_map_codelab_ktx_add_circle]
    /**
     * Adds a [Circle] around the provided [item]
     */
    private fun addCircle(googleMap: GoogleMap, item: Place) {
        circle?.remove()
        circle = googleMap.addCircle {
            center(item.latLng)
            radius(1000.0)
            fillColor(ContextCompat.getColor(this@map, R.color.colorPrimaryTranslucent))
            strokeColor(ContextCompat.getColor(this@map, R.color.colorPrimary))
        }
    }
    // [END maps_android_add_map_codelab_ktx_add_circle]

    private val binIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.colorGreen)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_recycle_bin1, color)
    }

    // [START maps_android_add_map_codelab_ktx_add_markers]
    /**
     * Adds markers to the map. These markers won't be clustered.
     */
    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker {
                title(place.name)
                position(place.latLng)
                icon(binIcon)
            }
            // Set place as the tag on the marker object so it can be referenced within
            // MarkerInfoWindowAdapter
            marker?.tag = place
        }
    }
    //Route Code
    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=${GOOGLE_MAPS_API_KEY}"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void,Void,List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,MapData::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
//                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
//                    path.add(startLatLng)
//                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            MMap!!.addPolyline(lineoption)
        }
    }

    public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    fun onInfoWindowClick(marker: Marker) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Open Google Maps?")
            .setCancelable(true)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                val latitude = java.lang.String.valueOf(marker.position.latitude)
                val longitude = java.lang.String.valueOf(marker.position.longitude)

                Log.d(TAG, "bin" + latitude +longitude)

                val gmmIntentUri: Uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(bin)")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                try {
                    if (mapIntent.resolveActivity(this.getPackageManager()) != null) {
                        startActivity(mapIntent)
                    }
                } catch (e: NullPointerException) {
                    Log.e(TAG, "onClick: NullPointerException: Couldn't open map." + e.message)
                    Toast.makeText(this, "Couldn't open map", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()

        alert.show()

    }

    //
    // [END maps_android_add_map_codelab_ktx_add_markers]
}


