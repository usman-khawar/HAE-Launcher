package com.uk.heflauncher

import android.os.BatteryManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.uk.heflauncher.databinding.ActivityMainBinding
import com.uk.heflauncher.fragments.AllAppsBottomSheetFragment
import com.uk.heflauncher.viewmodels.MainViewModel


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    private fun initialize() {
        binding.allAppsBt.setOnClickListener(this)

        val mainViewModel = MainViewModel()
        mainViewModel.batteryReceiver(this)
        mainViewModel.getWeather()
        mainViewModel.getBattery().observe(this) { batteryInfo ->
            if (batteryInfo.status == BatteryManager.BATTERY_STATUS_CHARGING ||
                batteryInfo.status == BatteryManager.BATTERY_STATUS_FULL
            ) binding.batterLevelTv.text = "Charging " + batteryInfo.level + " %" else
                binding.batterLevelTv.text = "${batteryInfo.level}" + " %"
        }

        mainViewModel.getWeatherData().observe(this) { weather ->
            binding.cityTv.text = weather.city
            binding.countryTv.text = weather.country
            binding.tempTv.text = weather.temperature.toString()
            binding.descTv.text = weather.description
        }

        mainViewModel.getProgress().observe(this) {
            if (it)
                binding.progressBar.visibility = VISIBLE else
                binding.progressBar.visibility = GONE
        }

        mainViewModel.getError().observe(this) {
            if (it)
                binding.somethingWentWrongTv.visibility = VISIBLE else
                binding.somethingWentWrongTv.visibility = GONE
        }

    }

    override fun onBackPressed() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.allAppsBt.id -> {
                AllAppsBottomSheetFragment().show(supportFragmentManager, "all_apps")
            }
        }
    }
}