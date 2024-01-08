package com.example.alle.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alle.R
import com.example.alle.ui.adapter.CollectionAdapter
import com.example.alle.ui.dialogs.CollectionCreateBottomSheet
import com.example.alle.ui.viewModel.MainViewModel
import com.google.mlkit.vision.label.ImageLabel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InfoFragment : Fragment(R.layout.info_fragment) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var description: AppCompatTextView
    private lateinit var inputAddNotes: AppCompatEditText
    private lateinit var selectedImg: AppCompatImageView
    private lateinit var editbutton: AppCompatTextView
    private val viewModel: MainViewModel by sharedViewModel()
    private val bottomSheetFragment = CollectionCreateBottomSheet()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        description = view.findViewById(R.id.subheadingDes)
        inputAddNotes = view.findViewById(R.id.etAddNotes)
        selectedImg = view.findViewById(R.id.selectedImage)
        editbutton = view.findViewById(R.id.btEdit)
        editbutton.setOnClickListener {
            showBottomSheet()
        }
        setObserve()
    }
    private fun setObserve(){
        viewModel.imageLabelValue.observe(viewLifecycleOwner,::showImageLabel)
        viewModel.ocrValue.observe(viewLifecycleOwner,::showTextLabel)
        viewModel.getSelectedImg.observe(viewLifecycleOwner,::setSelectedPhoto)
    }
    private fun setSelectedPhoto(uri: Uri?) {
        uri.let {
            selectedImg.visibility = View.VISIBLE
            Glide.with(this).load(it).into(selectedImg)
        }

    }


    private fun showImageLabel(imageLabels:List<ImageLabel>){
        showCollects(imageLabels)

    }

    private fun showTextLabel(textLabel:String){
        setDescriptions(textLabel)

    }
    private fun showCollects(collections: List<ImageLabel>) {
        val imageAdapter = CollectionAdapter(requireContext(), collections)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imageAdapter
    }

    private fun setDescriptions(ocr: String) {
        description.text = ocr
    }

    private fun showBottomSheet() {
        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
    }
}