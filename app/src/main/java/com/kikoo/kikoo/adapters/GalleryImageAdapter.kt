package com.kikoo.kikoo.adapters

import com.ahamed.multiviewadapter.DataListManager
import com.ahamed.multiviewadapter.RecyclerAdapter

import com.kikoo.kikoo.models.GalleryImage

class GalleryImageAdapter : RecyclerAdapter() {

    private val galleryimageListManager: DataListManager<GalleryImage>

    init {
        this.galleryimageListManager = DataListManager<GalleryImage>(this)
        addDataManager(galleryimageListManager)

        registerBinder(GalleryImageBinder())
    }

    fun setGalleryImageList(dataList: List<GalleryImage>) {
        galleryimageListManager.set(dataList)
    }
}