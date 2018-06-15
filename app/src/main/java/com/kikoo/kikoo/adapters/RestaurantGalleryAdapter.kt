package com.kikoo.kikoo.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.kikoo.kikoo.R
import com.kikoo.kikoo.load
import com.kikoo.kikoo.models.GalleryImage

/**
 * Created by nelson on 9/5/18.
 */
class RestaurantGalleryAdapter(val imageUrls: ArrayList<GalleryImage>): RecyclerView.Adapter<RestaurantGalleryAdapter.ViewHolder>() {
    override fun getItemCount(): Int = imageUrls.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestaurantGalleryAdapter.ViewHolder {
        // create a new view
        val card = LayoutInflater.from(parent.context)
                .inflate(R.layout.gallery_card, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters
        return RestaurantGalleryAdapter.ViewHolder(card)
    }

    override fun onBindViewHolder(holder: RestaurantGalleryAdapter.ViewHolder, position: Int) {
        val url = imageUrls[position]
        holder.image.load("")
    }

    class ViewHolder(galleryCard: CardView) : RecyclerView.ViewHolder(galleryCard) {
        val image: ImageView
        init {
            image = galleryCard.findViewById(R.id.gc_image)
        }
    }
}