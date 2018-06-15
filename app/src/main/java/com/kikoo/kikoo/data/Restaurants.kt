package com.kikoo.kikoo.data

import android.support.annotation.NonNull
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.kikoo.kikoo.models.GalleryImage
import com.kikoo.kikoo.models.Menu
import com.kikoo.kikoo.models.Restaurant
import java.lang.Exception


/**
 * Created by nelson on 16/5/18.
 */



fun getAllRestaurants(onSuccess: (List<Restaurant>) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("restaurants")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.toObjects(Restaurant::class.java)
                    onSuccess(result)
                } else {
                    onError(task.exception ?: Exception("Failed to load all restaurants"))
                }
            }
}
fun getRestaurant(id: String, onSuccess: (Restaurant) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("restaurants").document(id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        onSuccess(document.toObject(Restaurant::class.java)!!)
                    } else {
                        onError(Exception("Couldn't find restaurant with id: $id"))
                    }
                } else {
                    onError(task.exception ?: Exception("Could not load restaurant data."))
                }
            }

}

fun getGallery(id: String, onSuccess: (List<GalleryImage>) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("restaurants")
        .document(id)
        .collection("gallery")
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result.toObjects(GalleryImage::class.java)
                onSuccess(result)
            } else {
                onError(task.exception ?: Exception("Failed to load all restaurants"))
            }
        }
}

fun getMenu(id: String, onSuccess: (List<Menu>) -> Unit, onError: (Exception) -> Unit, limit: Long = 0)  {
    val db = FirebaseFirestore.getInstance()
    var query = db.collection("restaurants")
            .document(id).collection("menu")
            .orderBy("name")

    query = if (limit != 0L) query.limit(limit) else query

    query.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.toObjects(Menu::class.java)
                    onSuccess(result)
                } else {
                    onError(task.exception ?: Exception("Failed to load all restaurants"))
                }
            }
}
fun sendSearchTerm(term: String) {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("search-terms").document()
    doc.set(mapOf("term" to term))
}
fun sendFeedback(rate: Int, comment: String, onSucess: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("feedback").document()
    doc.set(mapOf("rating" to rate, "comment" to comment, "id" to doc.id)).addOnSuccessListener {

    }
}
