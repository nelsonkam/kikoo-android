package com.kikoo.kikoo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ahamed.multiviewadapter.BaseViewHolder
import com.ahamed.multiviewadapter.ItemBinder
import com.kikoo.kikoo.R
import com.kikoo.kikoo.load
import com.kikoo.kikoo.models.GalleryImage

class GalleryImageBinder : ItemBinder<GalleryImage, GalleryImageBinder.ViewHolder>() {

    override fun create(layoutInflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.gallery_card, parent, false))
    }

    override fun canBindData(item: Any): Boolean {
        return item is GalleryImage
    }

    override fun bind(holder: ViewHolder, item: GalleryImage) {
        item.downloadUrl({ url ->
            holder.image.load(url)
        }, { e ->
            Log.d(this.javaClass.name, e.message)
        })
    }

    class ViewHolder(itemView: View) : BaseViewHolder<GalleryImage>(itemView) {
        val image: ImageView
        init {
            image = itemView.findViewById(R.id.gc_image)
        }
    }
}