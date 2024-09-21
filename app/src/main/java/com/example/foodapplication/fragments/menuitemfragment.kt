package com.example.foodapplication.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.Model.Items
import com.example.foodapplication.Model.MenuItems
import com.example.foodapplication.R
import com.example.foodapplication.activity.HomeActivity
import com.example.foodapplication.adapter.ItemAdapters
import com.example.foodapplication.adapter.MenuAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.io.IOException

class menuitemfragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var itemrecyclerview: RecyclerView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var loctv: TextView
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var imageView: ImageView
    private lateinit var search:ImageView
    private lateinit var spvalue:TextView

    private val sharedfile = "FoodAppSharedPref"


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu_item_frament,container,false)

        val sharedvalue :SharedPreferences = requireContext().getSharedPreferences(sharedfile,Context.MODE_PRIVATE)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        loctv = view.findViewById(R.id.textView13)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
             ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
                fetchLocation()
        }
//        val sharedid = sharedvalue.getInt("id_key",1)
//        val sharedemail = sharedvalue.getString("Emailsp","0")
//        val sharedpass = sharedvalue.getString("Passsp","12345")
//        spvalue = view.findViewById(R.id.textView16)
//
//        if (sharedemail != null) {
//            if(sharedemail.isEmpty()){
//                Toast.makeText(requireContext(),"Default Value",Toast.LENGTH_SHORT).show()
//                spvalue.text = "Hello $sharedemail"
//            }
//            else{
//                spvalue.text ="Hello  $sharedemail"
//            }
//        }

        search = view.findViewById(R.id.searchicon)
        search.setOnClickListener {
            val fragment = SearchFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main,fragment)
                .addToBackStack(null)
                .commit()
        }

        imageView = view.findViewById(R.id.imageView7)
        imageView.setOnClickListener {
            val fragment = NotificationFragment()
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main,fragment)
                .commit()
        }

        // Initialize the RecyclerViews
        val menuItems = listOf(
            MenuItems("Burger", R.mipmap.burger_foreground),
            MenuItems("Burger2", R.mipmap.burger2_foreground),
            MenuItems("Pizza", R.mipmap.pizza_foreground),
            MenuItems("Juice", R.mipmap.juice_foreground)
        )
        recyclerView = view.findViewById(R.id.menu_items)
        recyclerView.adapter = MenuAdapter(menuItems)

        val items = listOf(
            Items("Ordinary Burgers", "4.9", R.drawable.basess, "100",  "₹ 19283", R.drawable.like),
            Items("Burger With Meat", "4.5", R.drawable.bas2, "190", "₹ 18823", R.drawable.like),
            Items("Tomato Burgers", "3.9", R.drawable.base3, "140", "₹ 12839", R.drawable.like),
            Items("Cheese Burgers", "4.9", R.drawable.base4, "180", "₹ 23223", R.drawable.like),
            Items("Sandwich Burgers", "2.9", R.drawable.basess, "200", "₹ 11223", R.drawable.like)
        )
        itemrecyclerview = view.findViewById(R.id.itemrec)
        itemrecyclerview.adapter = ItemAdapters(items){item ->
            val fragment = ScrollingFragment()
            val args = Bundle().apply {
                putString("itemName",item.name)
                putString("itemRating",item.rating)
                putString("itemPrice",item.price)
                putString("itemMetre",item.meter)
                putInt("itemImage",item.mimg)
                putInt("itemLike",item.like)
            }
            fragment.arguments = args

            parentFragmentManager.beginTransaction()
                .replace(R.id.main,fragment)
                .addToBackStack(null)
                .commit()
        }

        return view

    }
    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val long = location.longitude
                    getAddressFromLocation(lat, long)
                } else {
                    Toast.makeText(requireContext(), "Cannot get location", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireContext())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                val district = address.subAdminArea ?: "Unknown District"
                loctv.text = "$district"
            } else {
                loctv.text = "Address not found"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Geocoder service not available", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch location
                fetchLocation()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

