package com.uk.heflauncher.viewmodels

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uk.heflauncher.models.AppsModel

class AllAppsViewModel : ViewModel() {
    private val appList: MutableLiveData<List<AppsModel>> = MutableLiveData<List<AppsModel>>()

    fun getAppList(requireActivity: FragmentActivity) {
        val packageManager = requireActivity.packageManager
        val apps: MutableList<AppsModel> = ArrayList()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = packageManager.queryIntentActivities(intent, 0)
        for (ri in allApps) {
            if (ri.activityInfo.packageName != requireActivity.packageName) {
                val app = AppsModel()
                app.name = (ri.loadLabel(packageManager))
                app.packageName = (ri.activityInfo.packageName)
                app.icon = (ri.activityInfo.loadIcon(packageManager))
                apps.add(app)
            }
        }
        appList.postValue(apps)
    }

    fun getAppList(): LiveData<List<AppsModel>> {
        return appList
    }
}