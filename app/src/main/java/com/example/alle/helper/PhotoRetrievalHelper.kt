package com.example.alle.helper

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

class PhotoRetrievalHelper(private val context: Context) {

    fun getAllPhotos(): List<Uri> {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)

        val cursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        val photos = mutableListOf<Uri>()

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val imageUri = Uri.withAppendedPath(uri, it.getLong(idColumn).toString())
                photos.add(imageUri)
            }
        }

        println("Dasfladsnlmfadnfv eec : $photos")

        return photos
    }

    fun getAllScreenshots(): List<Uri> {
        val screenshots = mutableListOf<Uri>()

        val contentResolver: ContentResolver = context.contentResolver
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_ADDED
        )

        val selection = "${MediaStore.Images.Media.DATA} like ? "
        val selectionArgs = arrayOf("%/Screenshots/%")

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val path = it.getString(dataColumn)
                Log.d("Screenshot Path", path)

                screenshots.add(contentUri)
            }
        }

        return screenshots
    }
}
