package com.kikoo.kikoo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.kikoo.kikoo.adapters.GalleryImageAdapter
import com.kikoo.kikoo.adapters.RestaurantGalleryAdapter
import com.kikoo.kikoo.adapters.RestaurantMenuAdapter
import com.kikoo.kikoo.data.getGallery
import com.kikoo.kikoo.data.getMenu
import com.kikoo.kikoo.data.getRestaurant
import com.kikoo.kikoo.models.GalleryImage
import com.kikoo.kikoo.models.Menu
import com.kikoo.kikoo.models.Restaurant
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {
    private lateinit var galleryAdapter: GalleryImageAdapter
    private lateinit var menuAdapter: RestaurantMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        val id = intent.getStringExtra(RESTAURANT_ID)
        getRestaurant(id, this::onRestaurantLoaded, this::onDataError)
        getGallery(id, this::onGalleryLoaded, this::onDataError)
        getMenu(id, this::onMenuLoaded, this::onDataError,3)
        ra_back_btn.setOnClickListener { finish() }
        galleryAdapter = GalleryImageAdapter()
        menuAdapter = RestaurantMenuAdapter(arrayListOf(
                Menu(name = "Chargement en cours..."),
                Menu(name = "Chargement en cours..."),
                Menu(name = "Chargement en cours...")
        ))
        ra_gallery.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RestaurantActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = galleryAdapter

        }
        ra_menu_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RestaurantActivity)
            adapter = menuAdapter

        }

    }

    private fun onGalleryLoaded(gallery: List<GalleryImage>) {
        galleryAdapter.setGalleryImageList(gallery)
    }

    private fun onDataError(err: Exception) {
        Log.e(this.localClassName, err.message)
        Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_LONG).show()
    }
    fun onMenuLoaded(menu: List<Menu>) {
        Log.d(this.localClassName, menu.toString())
        menuAdapter.swapItems(menu)
    }
    fun onRestaurantLoaded(restaurant: Restaurant) {
        ra_name.text = restaurant.name
        ra_address.text = restaurant.address
        ra_hours.text = if (restaurant.openHour == 0 && restaurant.closeHour == 24)  {
            "Toujours ouvert"
        } else {
            "Ouvert de ${restaurant.openHour}h à ${restaurant.closeHour}h"
        }

        ra_rating.text = restaurant.rating.toString()
        ra_call_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + restaurant.phoneNumber)
            startActivity(intent)
        }
        ra_menu_btn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(RESTAURANT_ID, restaurant.id)
            intent.putExtra(RESTAURANT_NAME, restaurant.name)
            startActivity(intent)
        }
        restaurant.downloadUrl({ url ->
            ra_image.load(url)
        }, {
            Toast.makeText(this, "Échec lors du chargement de l'image, veuillez vérifier votre connexion.", Toast.LENGTH_SHORT).show()
        })
    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
