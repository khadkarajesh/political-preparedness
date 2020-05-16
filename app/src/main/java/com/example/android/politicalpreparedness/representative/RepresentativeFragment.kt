package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.BuildConfig
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_representative.*
import java.util.Locale

class DetailFragment : Fragment() {

    companion object {
        //TODO: Add Constant for Location request
    }

    //TODO: Declare ViewModel
    private val model: RepresentativeViewModel by viewModels { RepresentativeViewModelFactory() }
    private lateinit var binding: FragmentRepresentativeBinding
    private val settingRequestCode = 101
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_representative, container, false)
        binding.viewModel = model
        binding.lifecycleOwner = this


        binding.buttonLocation.setOnClickListener {
            checkPermission()
        }

        binding.buttonSearch.setOnClickListener {
            val address = model.toFormattedString(binding.addressLine1.text.toString(),
                    binding.addressLine2.text.toString(),
                    binding.city.text.toString(),
                    binding.state.selectedItem.toString(),
                    binding.zip.toString())
            model.findRepresentatives(address)
        }

        return binding.root

        //TODO: Establish bindings

        //TODO: Define and assign Representative adapter

        //TODO: Populate Representative adapter

        //TODO: Establish button listeners for field and location search

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = RepresentativeListAdapter()
        binding.rvRepresentatives.adapter = adapter
        binding.rvRepresentatives.layoutManager = LinearLayoutManager(activity)
        model.representatives.observe(viewLifecycleOwner, Observer {
            it.let(adapter::submitList)
        })
    }

    private fun checkPermission() {
        Dexter.withActivity(activity).withPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                getLocation()
            }

            override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
            ) {
                showPermissionsRationale(token)
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                showPermissionRationale()
            }
        }).check()
    }

    fun showPermissionsRationale(token: PermissionToken?) {
        AlertDialog.Builder(activity as Context).setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setNegativeButton(
                        R.string.cancel
                ) { dialog, _ ->
                    dialog.dismiss()
                    token?.cancelPermissionRequest()
                }
                .setPositiveButton(
                        R.string.ok
                ) { dialog, _ ->
                    dialog.dismiss()
                    token?.continuePermissionRequest()
                }
                .setOnDismissListener { token?.cancelPermissionRequest() }
                .show()
    }

    private fun showPermissionRationale() {
        MaterialAlertDialogBuilder(activity)
                .setTitle(getString(R.string.permission_rationale_title))
                .setMessage(getString(R.string.permission_rationale_message))
                .setPositiveButton(getString(R.string.txt_settings)) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                    startActivityForResult(
                            Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("""package:${BuildConfig.APPLICATION_ID}""")
                            ),
                            settingRequestCode
                    )
                }
                .setNegativeButton(
                        getString(R.string.cancel)
                ) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }.show()
    }


    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val address = geoCodeLocation(location)
            binding.addressLine1.setText(address.line1)
            binding.addressLine2.setText(address.line2)
            binding.city.setText(address.city)
            binding.zip.setText(address.zip)
        }
    }
    //TODO: Get location from LocationServices
    //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}



