package ru.moondi.kotlinweather.view.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

import kotlinx.android.synthetic.main.fragment_main_maps.*
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.databinding.FragmentMainMapsBinding
import java.io.IOException

class GoogleMapsFragment : Fragment() {
    private var _binding: FragmentMainMapsBinding? = null
   private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()
    private val callback =  OnMapReadyCallback {
            googleMap ->
        map = googleMap
        val coordinatesUlyanovsk = LatLng(54.33, 48.39)
        googleMap.addMarker(
            MarkerOptions().position(coordinatesUlyanovsk).title(getString(R.string.marker_start))
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinatesUlyanovsk))
        googleMap.setOnMapLongClickListener { latLng ->
            getAddressAsync(latLng)
            addMarkerToArray(latLng)
            drawLine()

        }
        activateMyLocation(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initSearchByAdress()
    }

    private fun getAddressAsync(location:LatLng) {
        context?.let {
            val geoCoder = Geocoder(it)
            Thread {
                try {
                    val addresses =
                        geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                    textAddress.post {
                        textAddress.text = addresses[0].getAddressLine(0)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private  fun addMarkerToArray(location: LatLng){
        val marker = setMarker(location,markers.size.toString(), R.drawable.ic_map_pin)
        markers.add(marker)
    }

    private fun setMarker(
        location: LatLng,
    searchText: String,
        resourceId: Int
    ): Marker{
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )
    }

    private fun drawLine(){
        val last: Int = markers.size - 1
        if(last >= 1){
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                .add(previous, current)
                .color(Color.RED)
                    .width(5f)
            )
        }
    }

    private  fun initSearchByAdress(){
        binding.buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            Thread{
                try{
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if(addresses.size > 0){
                        goToAddress(addresses, it, searchText)
                    }
                } catch (e: IOException){
                    e.printStackTrace()
                }
        }.start()

        }
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View,
        searchText: String
    ){
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )
        view.post{
            setMarker(location, searchText, R.drawable.ic_map_pin)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                location, 15f
            ))
        }
    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            val isPermissionGranted =
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
            googleMap.isMyLocationEnabled = isPermissionGranted
            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }
    }
}