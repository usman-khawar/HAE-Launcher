package com.uk.heflauncher.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.MutableLiveData
import com.uk.heflauncher.models.BatteryModel


class BatteryReceiver(private val context: Context) {

    fun registerBatteryReceiver(batteryLevel: MutableLiveData<BatteryModel>) {
        val batteryInfo = BatteryModel()
        val batteryLevelReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val rawlevel = intent.getIntExtra("level", -1)
                val scale = intent.getIntExtra("scale", -1)
                var level = -1
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                if (rawlevel >= 0 && scale > 0) {
                    level = rawlevel * 100 / scale
                }
                batteryInfo.level =level
                batteryInfo.status=status
                batteryLevel.value = batteryInfo
            }
        }
        val batteryLevelFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryLevelReceiver, batteryLevelFilter)
    }
}
