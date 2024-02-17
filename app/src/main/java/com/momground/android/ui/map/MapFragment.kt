package com.momground.android.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.momground.android.R
import com.momground.android.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback, LocationListener, View.OnClickListener {
    private lateinit var mMap: GoogleMap
    private var pathPoints = mutableListOf<LatLng>()
    private var totalDistance = 0f
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mapViewModel =
            ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // val textView: TextView = binding.textHome
        mapViewModel.text.observe(viewLifecycleOwner) {
            // textView.text = it
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //GoogleMap Setting
    override fun onMapReady(googleMap: GoogleMap) {
        val seoul = LatLng(37.554891, 126.970814)

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL // default 노말 생략 가능

        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            // 현재 위치로 카메라 이동
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 19f))
                }
            }

            locationListener = LocationListener { location ->
                val userLocation = LatLng(location.latitude, location.longitude)
                mMap.clear()
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 19f))
            }
            val locationRequest = LocationRequest.create()?.apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult ?: return
                    for (location in locationResult.locations){
                        updateLocationOnMap(location)
                    }
                }
            }
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.requestLocationUpdates(locationRequest!!, locationCallback, null)
            }

        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
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