package edu.bluejack22_2.Caddo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import edu.bluejack22_2.Caddo.Model.Category
import edu.bluejack22_2.Caddo.R

class CategoryAdapter(private val categoryList : ArrayList<Category>, private val imageCategoryList : ArrayList<Int>) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = categoryList[position]
        val currentImg = imageCategoryList[position]
        holder.categoryImage.setImageResource(currentImg)
        holder.categoryName.text = currentItem.categoryName
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage : ImageView = itemView.findViewById(R.id.iv_category)
        val categoryName : TextView = itemView.findViewById(R.id.tv_list_item_category_name)
    }

}