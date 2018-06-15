package com.kikoo.kikoo.models

import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception

/**
 * Created by nelson on 14/6/18.
 */
data class GalleryImage(val id: String = "", val path: String = "") {
    fun downloadUrl(onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val ref = storage.reference.child(path)
        ref.downloadUrl.addOnSuccessListener { uri ->
            onSuccess(uri.toString())
        }.addOnFailureListener(onError)
    }
}