package com.kikoo.kikoo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.kikoo.kikoo.adapters.RestaurantMenuAdapter
import com.kikoo.kikoo.data.getMenu
import com.kikoo.kikoo.models.Menu
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var menuAdapter: RestaurantMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val id = intent.getStringExtra(RESTAURANT_ID)
        val name = intent.getStringExtra(RESTAURANT_NAME)
        menuAdapter = RestaurantMenuAdapter(arrayListOf())
        getMenu(id, this::onMenuLoaded, this::onDataError)
        ma_name.text = name
        ma_back_btn.setOnClickListener { finish() }
        ma_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MenuActivity)
            adapter = menuAdapter
        }
    }
    private fun onDataError(err: Exception) {
        Log.e(this.localClassName, err.message)
        Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_LONG).show()
    }
    fun onMenuLoaded(menu: List<Menu>) {
        Log.d(this.localClassName, menu.toString())
        menuAdapter.swapItems(menu)
    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
