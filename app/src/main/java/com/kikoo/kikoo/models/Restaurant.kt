package com.kikoo.kikoo.models

import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception


/**
 * Created by nelson on 8/5/18.
 */
data class Restaurant(
        var id: String = "",
        var name: String = "",
        var shortAddress: String = "",
        var address: String = "",
        var rating: Double = 0.0,
        var price: Int = 0,
        var coverImage: String = "",
        var openHour: Int = 0,
        var closeHour: Int = 0,
        var phoneNumber: String = ""
) {
    fun downloadUrl(onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val ref = storage.reference.child(coverImage)
        ref.downloadUrl.addOnSuccessListener { uri ->
            onSuccess(uri.toString())
        }.addOnFailureListener(onError)
    }
}