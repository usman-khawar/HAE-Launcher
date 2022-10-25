package com.uk.heflauncher.models

import android.graphics.drawable.Drawable

class AppsModel {
    var packageName: String? = null
    var name: CharSequence? = null
    var icon: Drawable? = null

    override fun toString(): String {
        return "AppInfo(packageName=$packageName, name=$name, icon=$icon)"
    }
}