package edu.bluejack22_2.Caddo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import edu.bluejack22_2.Caddo.Adapter.CategoryAdapter
import edu.bluejack22_2.Caddo.Adapter.PopularProductAdapter
import edu.bluejack22_2.Caddo.Model.Category
import edu.bluejack22_2.Caddo.Model.Product
import edu.bluejack22_2.Caddo.ViewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var categoryAdapter : CategoryAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var categoriesList : ArrayList<Category>
    private lateinit var imgList : ArrayList<Int>
    private lateinit var titleCategories : TextView
    private lateinit var titlePopularProduct : TextView

    private lateinit var popularProductList : ArrayList<Product>
    private lateinit var rvPopular : RecyclerView
    private lateinit var popularProductAdapter: PopularProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        var homeViewModel : HomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        var tvCurrentLogin = view.findViewById<TextView>(R.id.tv_username_login)


        homeViewModel.getCurrentUserName()
        tvCurrentLogin.text = getString(R.string.greet)+", ${homeViewModel.getName.substring(0, homeViewModel.getUserNameLength())}"
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titlePopularProduct = view.findViewById(R.id.title_popular_products)
        titleCategories = view.findViewById(R.id.title_categories)

        categoriesList = arrayListOf()
        popularProductList = arrayListOf()
        listImgData()
        retrieveCategoryData()
        getPopularProducts()

        titleCategories.text = getString(R.string.title_categories)
        titlePopularProduct.text = getString(R.string.title_popular_products)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView = view.findViewById(R.id.rv_home_fragment_categories)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        categoryAdapter = CategoryAdapter(categoriesList, imgList)
        recyclerView.adapter = categoryAdapter

        // load rv popular
        val popularLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPopular = view.findViewById(R.id.rv_popular_products)
        rvPopular.setHasFixedSize(true)
        rvPopular.layoutManager = popularLayoutManager
        popularProductAdapter = PopularProductAdapter(popularProductList)
        rvPopular.adapter = popularProductAdapter

    }

    // get category data from firebase
    private fun retrieveCategoryData(){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("categories")
            .get()
            .addOnSuccessListener { documents->
                for (document in documents){
                    categoriesList.add(document.toObject(Category::class.java))
                }
                categoryAdapter.notifyDataSetChanged()
            }
    }


    // get popular products ( selling products > 2 )
    private fun getPopularProducts(){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("products")
            .whereGreaterThan("sellingSuccess", 2)
            .get()
            .addOnSuccessListener {documents->
                for(document in documents){
                    popularProductList.add(document.toObject(Product::class.java))
                }
                popularProductAdapter.notifyDataSetChanged()
            }
    }


    // fun img data
    private fun listImgData(){
        imgList = arrayListOf(
            R.drawable.img_categories_vege,
            R.drawable.img_categories_meat,
            R.drawable.img_categories_fruit,
            R.drawable.img_categories_cake,
            R.drawable.img_categories_drink,
        )
    }

}