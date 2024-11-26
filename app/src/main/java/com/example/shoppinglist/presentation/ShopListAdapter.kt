package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.itemName)
        val tvItemCount: TextView = view.findViewById(R.id.itemCount)
    }


    val shopItemList = listOf<ShopItem>()



//    override fun getItemViewType(position: Int): Int {
//        val shopItem = shopItemList.get(position)
//       if (position == VIEW_TYPE_ENABLED) {
//
//       }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val backgroundResId = 0

        val view = LayoutInflater.from(parent.context).inflate(backgroundResId,
            parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItemList.get(position)

        holder.tvItemName.text = shopItem.name

        holder.tvItemCount.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener{
             true
        }

    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }


    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_NOT_ENABLED = 101
    }

}