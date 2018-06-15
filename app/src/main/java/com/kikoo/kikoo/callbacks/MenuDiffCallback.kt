package com.kikoo.kikoo.callbacks

import android.support.v7.util.DiffUtil
import com.kikoo.kikoo.models.Menu
import com.kikoo.kikoo.models.Restaurant

/**
 * Created by nelson on 17/5/18.
 */
class MenuDiffCallback(val oldList: List<Menu>, val newList: List<Menu>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // add a unique ID property on Contact and expose a getId() method
        return oldList[oldItemPosition].id === newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}