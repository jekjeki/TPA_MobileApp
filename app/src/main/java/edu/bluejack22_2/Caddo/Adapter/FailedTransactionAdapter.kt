package edu.bluejack22_2.Caddo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import edu.bluejack22_2.Caddo.Model.Transaction
import edu.bluejack22_2.Caddo.R

class FailedTransactionAdapter(private val failedTransactionList : ArrayList<Transaction>) : RecyclerView.Adapter<FailedTransactionAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_failed_transaction, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return failedTransactionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = failedTransactionList[position]
        holder.trProductName.text = currentItem.productName
        holder.trTotalPrice.text = currentItem.totalPrice.toString()
        holder.trProductStatus.text = currentItem.transactionStatus
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val trProductName : TextView = itemView.findViewById(R.id.tv_trFailed_productName)
        val trTotalPrice : TextView = itemView.findViewById(R.id.tv_trFailed_productPrice)
        val trProductStatus : TextView = itemView.findViewById(R.id.tv_trFailed_productStatus)
    }
}