package com.kikoo.kikoo

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by nelson on 9/5/18.
 */
val RESTAURANT_ID = "RESTAURANT_ID"
val RESTAURANT_NAME = "RESTAURANT_NAME"

fun ImageView.load(url: String, debug: Boolean = false) {
    if (debug) {
        Picasso.get().apply {
            isLoggingEnabled = true
            load(url).into(this@load)
        }
        return
    }
    Picasso.get().load(url).into(this)
}