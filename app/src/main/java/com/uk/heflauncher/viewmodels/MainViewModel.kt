package com.uk.heflauncher.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uk.heflauncher.models.BatteryModel
import com.uk.heflauncher.models.WeatherModel
import com.uk.heflauncher.receiver.BatteryReceiver
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer


@OptIn(DelicateCoroutinesApi::class)
class MainViewModel : ViewModel() {
    private val batteryLevel: MutableLiveData<BatteryModel> = MutableLiveData<BatteryModel>()
    private val isError: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val weatherData: MutableLiveData<WeatherModel> = MutableLiveData<WeatherModel>()
    private val cityList = listOf("beijing", "berlin", "cardiff", "edinburgh", "london")

    fun batteryReceiver(context: Context) {
        val batteryReceiver = BatteryReceiver(context)
        batteryReceiver.registerBatteryReceiver(batteryLevel)
    }

    fun getBattery(): MutableLiveData<BatteryModel> {
        return batteryLevel
    }

    fun getWeather() {
        isError.postValue(false)
        var position = 0
        GlobalScope.launch(Dispatchers.IO) {
            fixedRateTimer("timer", false, 0L, 5000) {
                try {
                    isLoading.postValue(true)
                    val con =
                        URL("https://weather.bfsah.com/${cityList[position]}").openConnection() as HttpURLConnection
                    val weatherModel = WeatherModel()
                    val jsonObject = JSONObject(readString(con.inputStream))
                    weatherModel.city = jsonObject.getString("city")
                    weatherModel.country = jsonObject.getString("country")
                    weatherModel.temperature = jsonObject.getInt("temperature")
                    weatherModel.description = jsonObject.getString("description")
                    weatherData.postValue(weatherModel)
                    isLoading.postValue(false)

                    position += 1
                    if (position == cityList.size) position = 0
                } catch (e: Exception) {
                    isError.postValue(true)
                }
            }
        }
    }

    fun getWeatherData(): LiveData<WeatherModel> {
        return weatherData
    }

    fun getProgress(): LiveData<Boolean> {
        return isLoading
    }

    fun getError(): LiveData<Boolean> {
        return isError
    }

    fun readString(inputStream: InputStream): String {
        return Scanner(inputStream).useDelimiter("\\A").next()
    }
}