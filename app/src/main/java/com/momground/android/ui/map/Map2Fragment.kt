package com.momground.android.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
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
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.momground.android.R
import com.momground.android.data.User
import com.momground.android.databinding.FragmentMap2Binding

class Map2Fragment: Fragment(), OnMapReadyCallback, LocationListener, View.OnClickListener {
    private lateinit var mMap: GoogleMap
    private var pathPoints = mutableListOf<LatLng>()
    private var totalDistance = 0f
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private var _binding: FragmentMap2Binding? = null
    private val binding get() = _binding!!
    val CALL_PHONE_PERMISSION_REQUEST_CODE = 102

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        _binding = FragmentMap2Binding.inflate(inflater, container, false)
        val root: View = binding.root
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // val textView: TextView = binding.textHome
        fragmentViewModel.text.observe(viewLifecycleOwner) {
            // textView.text = it
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.call.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여된 경우 전화를 걸 수 있음
                makePhoneCall()
            } else {
                // 권한이 부여되지 않은 경우 사용자에게 권한을 요청
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_PHONE_PERMISSION_REQUEST_CODE)
            }
        }

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = false

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
                        val userLocation = LatLng(location.latitude, location.longitude)
                        googleMap.addMarker(MarkerOptions().position(userLocation).title("Inha University Hospital"))
                        // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 20f))
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 19f))
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.buttonCurrent ->{
                updateCurrentLocation()
            }
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
        if(User.driving) {
            if (pathPoints.isNotEmpty()) {
                val lastLocation = pathPoints.last()
                totalDistance += (calculateDistance(lastLocation, newLatLng))
            }
            pathPoints.add(newLatLng)
            drawPath()
            showTotalDistance()
        }
        if(!User.driving){
            removePath()
            pathPoints = mutableListOf()
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 19f))
    }

    private fun calculateDistance(from: LatLng, to: LatLng): Float {
        val result = FloatArray(1)
        Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, result)
        return result[0]
    }

    private fun drawPath() {
        val polylineOptions = PolylineOptions()
            .addAll(pathPoints)
            .color(ContextCompat.getColor(requireContext(), R.color.orange))
            .jointType(JointType.ROUND)
            .width(7f)
        mMap.addPolyline(polylineOptions)
    }
    private fun removePath(){
        mMap.clear()
    }

    private fun showTotalDistance() {
        val distanceInKm = totalDistance / 10000
        // User.drivingDistance = distanceInKm
    }

    private fun makePhoneCall(){
        val phoneNumber = getString(R.string.sample_number)
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    private fun addMark(latitude: Double, longitude: Double){
        // 위치 지정
        val location = LatLng(latitude, longitude)
        // 마커 생성
        val markerOptions = MarkerOptions().position(location).title("인하대 병원").snippet("인하대학교 부속 병원 응급실입니다")
        // 마커 추가
        mMap.addMarker(markerOptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}