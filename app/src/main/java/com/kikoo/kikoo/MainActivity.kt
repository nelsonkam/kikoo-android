package com.kikoo.kikoo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.kikoo.kikoo.adapters.RestaurantListAdapter
import com.kikoo.kikoo.adapters.RestaurantMenuAdapter
import com.kikoo.kikoo.data.getAllRestaurants
import com.kikoo.kikoo.data.sendFeedback
import com.kikoo.kikoo.data.sendSearchTerm
import com.kikoo.kikoo.models.Menu
import com.kikoo.kikoo.models.Restaurant
import kotlinx.android.synthetic.main.activity_main.*
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import java.util.*
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import android.R.attr.versionName
import android.content.pm.PackageInfo
import com.kikoo.kikoo.data.getLatestVersion


class MainActivity : AppCompatActivity(), RatingDialogListener {
    override fun onNegativeButtonClicked() {

    }

    override fun onNeutralButtonClicked() {

    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {
        sendFeedback(rate, comment) {
            Toast.makeText(this, "Merci pour le feedback !", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var listAdapter: RestaurantListAdapter
    private lateinit var searchMenuAdapter: RestaurantMenuAdapter
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listAdapter = RestaurantListAdapter(this, ::onRestaurantClick, arrayListOf())
        searchMenuAdapter = RestaurantMenuAdapter(arrayListOf(
                Menu(name = "Chargement en cours..."),
                Menu(name = "Chargement en cours..."),
                Menu(name = "Chargement en cours...")
        ))
        getLatestVersion { version ->
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            if (pInfo.versionName != version) {
                startActivity(Intent(this, UpdateActivity::class.java))
            }
        }


        getAllRestaurants(::onDataLoaded, ::onDataError)
        restaurant_list.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@MainActivity)

            // specify an viewAdapter (see also next example)
            adapter = listAdapter

        }
        search_restaurant_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
        search_menu_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = searchMenuAdapter
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oops !")
        builder.setMessage("La recherche sera bientôt disponible. Notre équipe travaille activement dessus.")
        builder.setPositiveButton("Cool", {dialogInterface, i -> })
        dialog = builder.create()
        searchInput.setOnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                showDialog()
                sendSearchTerm(searchInput.text.toString())
                true
            }
            false
        }
        search_btn.setOnClickListener {
            showDialog()
            sendSearchTerm(searchInput.text.toString())
        }
        ma_rate.setOnClickListener {
            showRating()
        }

    }
    private fun showDialog() {
        dialog.show()
    }
    private fun showRating() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Envoyer")
                .setNegativeButtonText("Annuler")
                .setNeutralButtonText("Plus tard")
//                .setNoteDescriptions(Arrays.asList("Médiocre", "Pas bon", "Pas mal", "Très bien", "Excellent !!!"))
                .setDefaultRating(0)
                .setNumberOfStars(5)
                .setTitle("Donnez nous votre avis sur l'application")
                .setDescription("Votre avis nous permettra d'améliorer l'application")
                .setStarColor(R.color.colorAccent)
                .setHint("Laissez nous un message ...")
                .setCommentBackgroundColor(R.color.divider)
                .create(this@MainActivity)
                .show()
    }
    private fun onDataError(err: Exception) {
        Log.e(this.localClassName, err.message)
        Toast.makeText(this, "Une erreure est survenue", Toast.LENGTH_LONG).show()
    }
    private fun onDataLoaded(data: List<Restaurant>) {
        listAdapter.swapItems(data)
    }
    private fun onRestaurantClick(position: Int) {
        val restaurant = listAdapter.restaurants[position]
        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra(RESTAURANT_ID, restaurant.id)
        startActivity(intent)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}

