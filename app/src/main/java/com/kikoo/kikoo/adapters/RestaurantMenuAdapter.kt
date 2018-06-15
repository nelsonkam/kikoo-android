package com.kikoo.kikoo.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.kikoo.kikoo.R
import com.kikoo.kikoo.callbacks.MenuDiffCallback
import com.kikoo.kikoo.models.Menu

/**
 * Created by nelson on 9/5/18.
 */
class RestaurantMenuAdapter(val menus: ArrayList<Menu>): RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder>() {
    override fun getItemCount(): Int = menus.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestaurantMenuAdapter.ViewHolder {
        // create a new view
        val card = LayoutInflater.from(parent.context)
                .inflate(R.layout.rmenu_item, parent, false) as ConstraintLayout
        // set the view's size, margins, paddings and layout parameters
        return RestaurantMenuAdapter.ViewHolder(card)
    }

    override fun onBindViewHolder(holder: RestaurantMenuAdapter.ViewHolder, position: Int) {
        val menu = menus[position]
        holder.name.text = menu.name.trim().capitalize()
        holder.price.text = if(menu.price == 0)  "" else menu.price.toString() + "F"
        holder.description.text = menu.description.trim().capitalize()
    }
    fun swapItems(newMenus: List<Menu>) {
        // compute diffs
        val diffCallback = MenuDiffCallback(menus, newMenus)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // clear contacts and add
        this.menus.clear()
        this.menus.addAll(newMenus)

        diffResult.dispatchUpdatesTo(this) // calls adapter's notify methods after diff is computed
    }
    class ViewHolder(menuItem: ConstraintLayout) : RecyclerView.ViewHolder(menuItem) {
        val name: TextView = menuItem.findViewById(R.id.ri_name)
        val price: TextView
        val description: TextView
        init {
            price = menuItem.findViewById(R.id.ri_price)
            description = menuItem.findViewById(R.id.ri_desc)
        }
    }
}