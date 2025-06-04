package vn.thanhdz.nangmua.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.R
import vn.thanhdz.nangmua.data.database.LocRoomDatabase
import vn.thanhdz.nangmua.data.model.Loc
import vn.thanhdz.nangmua.data.repository.DateTimeConverter
import vn.thanhdz.nangmua.data.repository.UpLoadImg
import vn.thanhdz.nangmua.databinding.ActivityMainBinding
import vn.thanhdz.nangmua.ui.adapter.Forecast12hAdapter
import vn.thanhdz.nangmua.ui.adapter.Forecast5dAdapter
import vn.thanhdz.nangmua.ui.viewmodel.Forecast12hViewModel
import vn.thanhdz.nangmua.ui.viewmodel.Forecast5dViewModel
import vn.thanhdz.nangmua.ui.viewmodel.HomeViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var forecast12hViewModel: Forecast12hViewModel
    private lateinit var forecast5dViewModel: Forecast5dViewModel
    private lateinit var forecast12hAdapter: Forecast12hAdapter
    private lateinit var forecast5dAdapter: Forecast5dAdapter

    private val calendar = Calendar.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var locDb: LocRoomDatabase

    private var locationId = 353412
    private var location = "Hà Nội"
    private var country = "Việt Nam"

    private val handler = Handler(Looper.getMainLooper())
    private val refreshInterval = 60 * 1000L
    private val refreshRunnable = object : Runnable {
        override fun run() {
            if (isOnline()) {
                loadWeatherData(locationId)
            } else {
                binding.noInternetImg.visibility = View.VISIBLE
            }
            handler.postDelayed(this, refreshInterval)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        handler.post(refreshRunnable)
        binding.favoriteList.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteLocationActivity::class.java))
        }

        if (isOnline()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            binding.noInternetImg.visibility = View.GONE

            forecast5dViewModel.weather.observe(this@MainActivity) {
                if (it != null) {
                    forecast5dAdapter.updateData(it)
                }
            }

            forecast12hViewModel.weather.observe(this@MainActivity) {
                if (it != null) {
                    forecast12hAdapter.update(it)
                }
            }
            setup()

            binding.apply {
                locationId = intent.getIntExtra("SEARCH_KEY", 0)
                location = intent?.getStringExtra("SEARCH_NAME").toString()
                country = intent?.getStringExtra("SEARCH_COUNTRY").toString()

                if (locationId == 0 || location.isEmpty()) {
                    locationId = intent.getIntExtra("FAVORITE_KEY", 0)
                    location = intent?.getStringExtra("FAVORITE_NAME").toString()
                    country = intent?.getStringExtra("FAVORITE_COUNTRY").toString()

                    if (locationId == 0 || location.isEmpty()) {
                        // Lấy địa điểm đầu tiên
                        getFirstLocation()

                    } else {
                        checkLocation()
                        loadWeatherData(locationId)
                    }
                } else {
                    checkLocation()
                    loadWeatherData(locationId)
                }


                searchLocation.setOnClickListener {
                    startActivity(Intent(this@MainActivity, LocationListActivity::class.java))
                }

                addLocation.setOnClickListener {
                    addLoc()
                }

                rvForecast5Days.adapter = forecast5dAdapter
                rvForecast12Hours.adapter = forecast12hAdapter
            }
        } else {
            locationId = intent.getIntExtra("SEARCH_KEY", 0)
            location = intent?.getStringExtra("SEARCH_NAME").toString()
            country = intent?.getStringExtra("SEARCH_COUNTRY").toString()

            if (locationId == 0 || location.isEmpty()) {
                getFirstLocationOffline()
            } else {
                checkLocation()
            }
            checkLocation()
            binding.addLocation.setOnClickListener {
                addLoc()
            }
            binding.noInternetImg.visibility = View.VISIBLE
            Toast.makeText(this, "Hãy kết nối internet!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locDb = LocRoomDatabase.getDataBase(binding.root.context)
        homeViewModel = HomeViewModel()
        forecast12hViewModel = Forecast12hViewModel()
        forecast5dViewModel = Forecast5dViewModel()
        forecast5dAdapter = Forecast5dAdapter(mutableListOf())
        forecast12hAdapter = Forecast12hAdapter(mutableListOf())
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) > 18
    }

    private fun setDynamicallyWallpaper(icon: Int): Int {
        return when (icon) {
            1, 2, 3, 4, 5, 6, 7, 8, 11 -> R.drawable.background_sun
            12, 13, 14, 15, 16, 17, 18 -> R.drawable.background_rain
            else -> {
                0
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setup() {
        binding.apply {
            homeViewModel.currentWeather.observe(this@MainActivity) {
                if (it != null) {
                    tvLocation.text = location
                    currentLayout.visibility = View.VISIBLE
                    val drawable = if (isNightNow()) R.drawable.background_night
                    else {
                        setDynamicallyWallpaper(it.weatherIcon)
                    }
                    imgBg.setImageResource(drawable)
                    tvTempCurrent.text = "${it.temperature.metric.value.toInt()}°"
                    tvWeatherText.text = it.weatherText
                    tvTimeAtLocation.text = DateTimeConverter().toDateTime(it.dateTime)
                    tvUvIndex.text = it.uVIndex.toString()
                    tvUvText.text = it.uVIndexText
                    tvPrecipitation.text =
                        "${it.precipitationSummary.precipitation.metric.value} ${it.precipitationSummary.precipitation.metric.unit}"

                    tvRealTemp.text = "${it.realFeelTemperature.metric.value.toInt()}°"
                    tvWindSpeed.text = "${it.wind.speed.metric.value.toInt()} km/h"
                    tvRelativeHumidity.text = "${it.relativeHumidity} %"

                    val divider =
                        DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)

                    rvForecast5Days.addItemDecoration(divider)
                    currentScrollView.overScrollMode = View.OVER_SCROLL_NEVER
                    weatherIconImg.setImageResource(UpLoadImg().load(it.weatherIcon))
                }
            }
        }
    }


    private fun loadWeatherData(locationId: Int) {
        homeViewModel.loadCurrentWeatherData(locationId)
        forecast5dViewModel.loadForecast5dData(locationId)
        forecast12hViewModel.loadForecast12hData(locationId)
    }


    private fun checkLocation() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val loc = locDb.locDao().find(locationId)
                loc?.let { // Sử dụng safe call và let để tránh null
                    if (it.key > 0) {
                        binding.addLocation.visibility = View.GONE
                    } else {
                        binding.addLocation.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                binding.addLocation.visibility = View.VISIBLE
            }
        }
    }


    private fun getFirstLocation() {
        CoroutineScope(Dispatchers.IO).launch {
            val loc = locDb.locDao().getFirstLocation()
            if (loc != null && loc.key > 0) {
                locationId = loc.key
                location = loc.locationName
                binding.tvLocation.text = location
            } else {
                checkLocationPermissionAndFetch()
            }
            checkLocation()
            if (isOnline()) {
                loadWeatherData(locationId)
            }
        }
    }

    private fun getFirstLocationOffline() {
        CoroutineScope(Dispatchers.IO).launch {
            val loc = locDb.locDao().getFirstLocation()
            if (loc != null && loc.key > 0) {
                locationId = loc.key
                location = loc.locationName
                binding.tvLocation.text = location
            } else {
                setDefaultLocation()
            }
            checkLocation()
        }
    }

    private fun setDefaultLocation() {
        locationId = 353412
        location = "Hà Nội"
        country = "Việt Nam"
        binding.tvLocation.text = location
    }

    private fun addLoc() {
        binding.addLocation.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            locDb.locDao().insert(Loc(key = locationId, locationName = location, country = country))
        }
    }


    override fun onDestroy() {
        handler.removeCallbacks(refreshRunnable)
        super.onDestroy()
    }


    private fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            ) {
                return true
            }
        }
        return false
    }

    private fun checkLocationPermissionAndFetch() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            fetchDeviceLocation()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchDeviceLocation()
        } else {
            Toast.makeText(this, "Cần cấp quyền vị trí để dùng chức năng này", Toast.LENGTH_SHORT)
                .show()
        }
    }


    @SuppressLint("MissingPermission")
    private fun fetchDeviceLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude

                // Gọi API để lấy locationKey từ lat/lon
                getLocationKeyFromLatLon(lat, lon)
            } else {
                setDefaultLocation()
                Toast.makeText(this, "Không thể lấy vị trí thiết bị", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocationKeyFromLatLon(lat: Double, lon: Double) {
        homeViewModel.locaion.observe(this) {
            if (it != null) {
                location = it.localizedName
                locationId = it.key.toInt()
                country = it.country.localizedName
                checkLocation()
                loadWeatherData(it.key.toInt())
            }
        }
        homeViewModel.getLocationKey(lat, lon)
    }
}
