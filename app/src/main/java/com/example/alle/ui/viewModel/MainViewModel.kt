package com.example.alle.ui.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alle.helper.LoadingManager
import com.example.alle.helper.PhotoRetrievalHelper
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


class MainViewModel(
    private val loadingManager: LoadingManager,
    private val photoRetrievalHelper: PhotoRetrievalHelper
) : ViewModel() {
    val photos = MutableLiveData<List<Uri>>()
    private val _selectedImg = MutableLiveData<Uri>()
    val getSelectedImg get() = _selectedImg
    private val _ocrData = MutableLiveData<String>()
    val ocrValue: LiveData<String>
        get() = _ocrData
    private val _imageLabelData = MutableLiveData<List<ImageLabel>>()

    val imageLabelValue: LiveData<List<ImageLabel>> get() = _imageLabelData
    val loading get() = loadingManager.isLoading

    val error = MutableLiveData<String>()


    fun performOCR(imageUri: Uri, context: Context) {
        loadingManager.showLoading()
        println("File : $imageUri")
        val image = InputImage.fromFilePath(context, imageUri)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image).addOnSuccessListener { visionText ->
            val recognizedText = visionText.text
            _ocrData.value = recognizedText
            loadingManager.hideLoading()
            Log.d("ocr", recognizedText)
        }.addOnFailureListener { e ->
            loadingManager.hideLoading()
            e.localizedMessage?.let {
                error.postValue(it)
                Log.e("ocr", it)
            }

            e.printStackTrace()
        }
    }

    fun processImageLabels(imageUri: Uri, context: Context) {
        loadingManager.showLoading()
        val image = InputImage.fromFilePath(context, imageUri)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        labeler.process(image).addOnSuccessListener { labels ->
            _imageLabelData.value = labels
            loadingManager.hideLoading()
            Log.d("Image", labels.toString())
        }.addOnFailureListener { e ->
            // Handle image labeling failure
            loadingManager.hideLoading()
            e.localizedMessage?.let {
                error.postValue(it)
                Log.e("Image", it)
            }
            e.printStackTrace()
        }
    }

    fun getScreenshotImages(){
        photos.postValue(photoRetrievalHelper.getAllScreenshots())
    }

    fun setSelectedImg(path:Uri){
        _selectedImg.value = path
    }

    fun getAllPhotos(){
        photos.postValue(photoRetrievalHelper.getAllPhotos())
    }
}

