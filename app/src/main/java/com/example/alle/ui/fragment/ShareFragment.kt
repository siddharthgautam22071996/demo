package com.example.alle.ui.fragment

import android.Manifest
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alle.R
import com.example.alle.helper.PermissionsHandler
import com.example.alle.ui.dialogs.LoadingDialog
import com.example.alle.ui.adapter.ImageAdapter
import com.example.alle.ui.viewModel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class ShareFragment : Fragment(R.layout.share_fragment) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fullImageView: ImageView
    private lateinit var bt: Button
    private val viewModel: MainViewModel by sharedViewModel()
    private val loadingDialog: LoadingDialog by inject()
    private val permissionsHandler: PermissionsHandler by inject {
        parametersOf(requireActivity().activityResultRegistry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(permissionsHandler)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        fullImageView = view.findViewById(R.id.fullImageView)
        checkPermission()
        setUpObserve()
//        viewModel.getScreenshotImages()
    }

    private fun checkPermission() {
        permissionsHandler.requestPermission(activity = requireActivity(),
            permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            onGranted = {
                viewModel.getAllPhotos()
            })
    }

    private fun setUpObserve() {
        viewModel.error.observe(requireActivity(), ::showError)
        viewModel.loading.observe(requireActivity(), ::showHideLoading)
        viewModel.photos.observe(requireActivity(), ::setStripeImageView)
    }

    private fun showHideLoading(isLoading: Boolean) {
        if (isLoading) {
            loadingDialog.show(requireActivity().supportFragmentManager, "Loading")
        } else {
            loadingDialog.dismiss()
        }
    }

    private fun showError(error: String) {
        if (error.isEmpty().not()) showTextPopup(error)
    }


    override fun onDestroy() {
        lifecycle.removeObserver(permissionsHandler)
        super.onDestroy()

    }

    private fun setStripeImageView(photos: List<Uri>) {
        println("\nDasfladsnlmfadnfv  : ${photos.size}")
        val imageAdapter = ImageAdapter({
            showFullImagePreview(it)
        }, requireContext(), photos)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imageAdapter
    }


    private fun showTextPopup(text: String) {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.popup)
        val textView: TextView = dialog.findViewById(R.id.popupTextView)
        val title: TextView = dialog.findViewById(R.id.title)
        title.text = "ERROR"
        textView.text = text
        dialog.show()
    }

    private fun showFullImagePreview(imageUrl: Uri) {
        Glide.with(this).load(imageUrl).into(fullImageView)
        viewModel.setSelectedImg(imageUrl)
        viewModel.processImageLabels(imageUrl, requireContext())
        viewModel.performOCR(imageUrl, requireContext())
        fullImageView.visibility = View.VISIBLE
    }


}