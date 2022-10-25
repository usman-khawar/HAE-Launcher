package com.uk.heflauncher.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uk.heflauncher.databinding.AllAppsItemBinding
import com.uk.heflauncher.models.AppsModel

class AllAppsAdapter(var listOfApps: MutableList<AppsModel>, var allAppsInterface: AllAppsInterface) :
    RecyclerView.Adapter<AllAppsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AllAppsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AllAppsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listOfApps[position]) {
                binding.appNameTv.text = name
                binding.iconIv.setImageDrawable(icon)

                binding.root.setOnClickListener {
                    allAppsInterface.onClick(packageName!!)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfApps.size
    }
}

interface AllAppsInterface {
    fun onClick(packageName: String)
}