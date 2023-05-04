package edu.bluejack22_2.Caddo.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.bluejack22_2.Caddo.DetailActivity
import edu.bluejack22_2.Caddo.DetailFragment
import edu.bluejack22_2.Caddo.Model.Product
import edu.bluejack22_2.Caddo.R

class AllProductAdapter(private val allProductList : ArrayList<Product>, private val allProductId : ArrayList<String>):RecyclerView.Adapter<AllProductAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_all_products, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allProductList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = allProductList[position]
        val currentId = allProductId[position]
        Glide.with(holder.imageProductPage).load(currentItem.productPicture).into(holder.imageProductPage)
        holder.nameProductPage.text = currentItem.productName
        holder.priceProductPage.text = currentItem.productPrice.toString()

        holder.itemView.setOnClickListener {
            var intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("itemId", currentId)
            intent.putExtra("itemName", currentItem.productName)
            intent.putExtra("itemImage", currentItem.productPicture)
            intent.putExtra("itemDesc", currentItem.productDescription)
            intent.putExtra("itemCategory", currentItem.productCategory)
            intent.putExtra("itemPrice", currentItem.productPrice)
            intent.putExtra("itemQty", currentItem.productQty)
            intent.putExtra("itemStatus", currentItem.productStatus)
            intent.putExtra("itemSellingSuccess", currentItem.sellingSuccess)
            holder.itemView.context.startActivity(intent)


        }
    }


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imageProductPage : ImageView = itemView.findViewById(R.id.iv_image_products_page)
        val nameProductPage : TextView = itemView.findViewById(R.id.tv_product_page_name)
        val priceProductPage : TextView = itemView.findViewById(R.id.tv_product_page_price)
    }
}