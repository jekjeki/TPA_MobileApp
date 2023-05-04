package edu.bluejack22_2.Caddo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.bluejack22_2.Caddo.Model.Product
import edu.bluejack22_2.Caddo.R

class PopularProductAdapter(private val popularProductList : ArrayList<Product>) : RecyclerView.Adapter<PopularProductAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_popular_product, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return popularProductList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = popularProductList[position]
        Glide.with(holder.popularImage).load(currentItem.productPicture).into(holder.popularImage)
        holder.popularName.text = currentItem.productName
        holder.popularPrice.text = currentItem.productPrice.toString()
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val popularImage : ImageView = itemView.findViewById(R.id.iv_image_popular_products)
        val popularName : TextView = itemView.findViewById(R.id.tv_popular_product_name)
        val popularPrice : TextView = itemView.findViewById(R.id.tv_popular_product_price)
    }
}