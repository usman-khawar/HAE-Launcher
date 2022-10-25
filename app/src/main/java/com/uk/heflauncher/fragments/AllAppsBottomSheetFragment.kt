package com.uk.heflauncher.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uk.heflauncher.adapter.AllAppsAdapter
import com.uk.heflauncher.adapter.AllAppsInterface
import com.uk.heflauncher.databinding.FragmentAllAppsBottomSheetBinding
import com.uk.heflauncher.models.AppsModel
import com.uk.heflauncher.viewmodels.AllAppsViewModel

class AllAppsBottomSheetFragment : BottomSheetDialogFragment(), AllAppsInterface,
    View.OnClickListener {

    private lateinit var binding: FragmentAllAppsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAppsBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            setOnShowListener {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.allAppsLayout.setOnClickListener(this)

        val allAppsViewModel = AllAppsViewModel()
        allAppsViewModel.getAppList(requireActivity())
        allAppsViewModel.getAppList().observe(this) { apps -> loadRv(apps) }
    }

    private fun loadRv(apps: List<AppsModel>?) {
        binding.allAppsRv.isNestedScrollingEnabled = true
        binding.allAppsRv.layoutManager = LinearLayoutManager(requireActivity())
        binding.allAppsRv.adapter = apps?.toMutableList()?.let { AllAppsAdapter(it, this) }
    }

    override fun onClick(packageName: String) {
        val launchIntent: Intent? = requireActivity().packageManager
            .getLaunchIntentForPackage(packageName)
        requireActivity().startActivity(launchIntent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.allAppsLayout.id -> {
                dismiss()
            }
        }
    }
}