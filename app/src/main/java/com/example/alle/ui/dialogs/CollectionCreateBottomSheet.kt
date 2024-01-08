package com.example.alle.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.alle.R
import com.example.alle.ui.adapter.CollectionBottomSheetAdapter
import com.example.alle.ui.adapter.CreateListAdapter
import com.example.alle.ui.viewModel.MainViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.mlkit.vision.label.ImageLabel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CollectionCreateBottomSheet : BottomSheetDialogFragment() {
    private lateinit var inputList: RecyclerView
    private lateinit var createList: RecyclerView
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var scollview: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MaterialBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scollview = view.findViewById(R.id.scroll)
        inputList = view.findViewById(R.id.recyclerView1)
        createList = view.findViewById(R.id.recyclerView)
        scollview.isNestedScrollingEnabled = true
        setObserve()
    }

    private fun setObserve() {
        viewModel.imageLabelValue.observe(viewLifecycleOwner, ::setInputBoxAdapter)
    }

    private fun setInputBoxAdapter(list: List<ImageLabel>) {
        setUpCreateRecyclerView(list)
        val imageAdapter = CollectionBottomSheetAdapter(requireContext(), list) {

        }
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        inputList.layoutManager = flexboxLayoutManager
        inputList.adapter = imageAdapter
    }

    private fun setUpCreateRecyclerView(list: List<ImageLabel>){
        val imageAdapter = CreateListAdapter({},requireContext(), list)
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        createList.layoutManager = flexboxLayoutManager
        createList.adapter = imageAdapter
    }
}