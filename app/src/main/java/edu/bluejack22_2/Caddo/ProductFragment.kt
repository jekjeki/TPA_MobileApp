package edu.bluejack22_2.Caddo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_2.Caddo.Adapter.AllProductAdapter
import edu.bluejack22_2.Caddo.Model.Product


class ProductFragment : Fragment() {

    private lateinit var rvAllProducts : RecyclerView
    private lateinit var productsList : ArrayList<Product>
    private lateinit var productsIdList : ArrayList<String>
    private lateinit var allProductAdapter: AllProductAdapter
    private lateinit var titleListProducts : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleListProducts = view.findViewById(R.id.title_list_products)
        titleListProducts.text = getString(R.string.title_list_of_products)

        productsList = arrayListOf()
        productsIdList = arrayListOf()

        getAllProducts()
        getAllProductId()

        rvAllProducts = view.findViewById(R.id.rv_list_all_products)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvAllProducts.setHasFixedSize(true)
        rvAllProducts.layoutManager = linearLayoutManager
        allProductAdapter = AllProductAdapter(productsList, productsIdList)
        rvAllProducts.adapter = allProductAdapter

    }

    // get all products data
    private fun getAllProducts(){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("products")
            .get()
            .addOnSuccessListener {documents->
                for(document in documents){
                    productsList.add(document.toObject(Product::class.java))
                }
                allProductAdapter.notifyDataSetChanged()
            }
    }

    private fun getAllProductId(){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("products")
            .get()
            .addOnSuccessListener {documents->
                for(document in documents){
                    productsIdList.add(document.id)
                }
            }
    }

}