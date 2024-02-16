package com.momground.android.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.momground.android.R
import com.momground.android.databinding.FragmentHomeBinding
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : Fragment(), OnMapReadyCallback, LocationListener, View.OnClickListener {
    private lateinit var mMap: GoogleMap
    private var pathPoints = mutableListOf<LatLng>()
    private var totalDistance = 0f
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // val textView: TextView = binding.textHome
        mapViewModel.text.observe(viewLifecycleOwner) {
            // textView.text = it
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    @SuppressLint("MissingPermission")
    private fun updateCurrentLocation() {
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        location?.let {
            val currentLatLng = LatLng(it.latitude, it.longitude)
        }
    }

    override fun onClick(view: View?) {
        when(view){
        }
    }

    override fun onLocationChanged(location: Location) {
        val newLatLng = LatLng(location.latitude, location.longitude)
        pathPoints.add(newLatLng)
        drawPath()
        showTotalDistance()
    }

    private fun updateLocationOnMap(location: Location) {
        val newLatLng = LatLng(location.latitude, location.longitude)
    }

    private fun calculateDistance(from: LatLng, to: LatLng): Float {
        val result = FloatArray(1)
        Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, result)
        return result[0]
    }

    private fun drawPath() {
        val polylineOptions = PolylineOptions()
            .addAll(pathPoints)
            .color(resources.getColor(R.color.orange))
            .jointType(JointType.ROUND)
            .width(7f)
        mMap.addPolyline(polylineOptions)
    }
    private fun removePath(){
        mMap.clear()
    }
    private fun showTotalDistance() {
        val distanceInKm = totalDistance / 10000
        // User.moveDistance = distanceInKm
    }
}