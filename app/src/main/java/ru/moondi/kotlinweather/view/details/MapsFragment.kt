package ru.moondi.kotlinweather.view.details

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import ru.moondi.kotlinweather.R
import ru.moondi.kotlinweather.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
   private val binding get() = _binding!!
    private val callback = OnMapReadyCallback { googleMap ->

        val coordinatesUlyanovsk = LatLng(54.18, 48.24)
        googleMap.addMarker(MarkerOptions().position(coordinatesUlyanovsk).title("Marker in Ulyanovsk"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinatesUlyanovsk))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}