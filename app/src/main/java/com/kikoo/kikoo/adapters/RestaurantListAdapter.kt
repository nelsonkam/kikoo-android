package com.kikoo.kikoo.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kikoo.kikoo.R
import com.kikoo.kikoo.load
import com.kikoo.kikoo.models.Restaurant
import java.util.*
import android.text.method.TextKeyListener.clear
import android.support.v7.util.DiffUtil
import android.util.Log
import com.kikoo.kikoo.callbacks.RestaurantDiffCallback


/**
 * Created by nelson on 8/5/18.
 */
class RestaurantListAdapter(val ctx: Context, val clickListener: (Int) -> Unit, val restaurants: ArrayList<Restaurant>)
    : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestaurantListAdapter.ViewHolder {
        // create a new view
        val card = LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_card, parent, false) as CardView
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(card, clickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val restaurant = restaurants[position]
        holder.name.text = restaurant.name
        holder.shortAddress.text = restaurant.shortAddress
        holder.rating.text = restaurant.rating.toString()
        holder.pricing.text = "$".repeat(restaurant.price)

        val calendar = GregorianCalendar.getInstance()
        calendar.time = Date()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val isOpened = if (restaurant.closeHour > restaurant.openHour) {
            currentHour >= restaurant.openHour && currentHour < restaurant.closeHour
        } else {
            (currentHour >= restaurant.openHour && currentHour < 24)
                    || (currentHour >= 0 && currentHour < restaurant.closeHour)
        }

        if (isOpened) {
            holder.isOpened.text = "Ouvert"
            holder.isOpened.setTextColor(ctx.resources.getColor(R.color.openedColor))
        } else {
            holder.isOpened.text = "Fermé"
            holder.isOpened.setTextColor(ctx.resources.getColor(R.color.closedColor))
        }
        restaurant.downloadUrl({ url ->
            holder.cover.load(url)
        }, { e ->
            Log.d(this.javaClass.name, e.message)
        })

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = restaurants.size

    fun swapItems(newRestaurants: List<Restaurant>) {
        // compute diffs
        val diffCallback = RestaurantDiffCallback(restaurants, newRestaurants)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // clear contacts and add
        this.restaurants.clear()
        this.restaurants.addAll(newRestaurants)

        diffResult.dispatchUpdatesTo(this) // calls adapter's notify methods after diff is computed
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(restaurantCard: CardView, clickListener: (Int) -> Unit) : RecyclerView.ViewHolder(restaurantCard) {
        val name: TextView
        val rating: TextView
        val shortAddress: TextView
        val pricing: TextView
        val isOpened: TextView
        val cover: ImageView
        init {
            name = restaurantCard.findViewById(R.id.rc_name)
            rating = restaurantCard.findViewById(R.id.rc_rating)
            pricing = restaurantCard.findViewById(R.id.rc_pricing)
            isOpened = restaurantCard.findViewById(R.id.rc_isopened)
            shortAddress = restaurantCard.findViewById(R.id.rc_shortAddress)
            cover = restaurantCard.findViewById(R.id.rc_coverImage)
            restaurantCard.setOnClickListener {
                clickListener(adapterPosition)
            }
        }
    }
}