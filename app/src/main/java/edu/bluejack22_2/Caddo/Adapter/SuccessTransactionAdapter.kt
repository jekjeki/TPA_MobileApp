package edu.bluejack22_2.Caddo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_2.Caddo.Model.Transaction
import edu.bluejack22_2.Caddo.R

class SuccessTransactionAdapter(private val successTransactionList : ArrayList<Transaction>) : RecyclerView.Adapter<SuccessTransactionAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_success_transaction, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return successTransactionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = successTransactionList[position]
        holder.tvTrSuccessProductName.text = currentItem.productName
        holder.tvTrSuccessProductPrice.text = currentItem.totalPrice.toString()
        holder.tvTrSuccessProductStatus.text = currentItem.transactionStatus.toString()
    }


    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val tvTrSuccessProductName : TextView = itemView.findViewById(R.id.tv_trSuccess_productName)
        val tvTrSuccessProductPrice : TextView = itemView.findViewById(R.id.tv_trSuccess_productPrice)
        val tvTrSuccessProductStatus : TextView = itemView.findViewById(R.id.tv_trSuccess_productStatus)
    }

}