package com.example.alle.helper

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.getUriFromFile(file: File): Uri {
    val authority = "$packageName.provider"
    return FileProvider.getUriForFile(this, authority, file)
}
