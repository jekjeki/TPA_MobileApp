package edu.bluejack22_2.Caddo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firestore.v1.StructuredQuery.Order
import edu.bluejack22_2.Caddo.ViewModel.DetailViewModel
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {

    private lateinit var ivDetail : ImageView
    private lateinit var tvProductName : TextView
    private lateinit var tvProductDesc : TextView
    private lateinit var tvProductCategory : TextView
    private lateinit var tvProductPrice : TextView
    private lateinit var tvProductQty : TextView
    private lateinit var tvProductStatus : TextView

    private lateinit var etBuyQty : EditText
    private lateinit var buttonOrder : Button

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val getProductId : String? = intent.getStringExtra("itemId")
        val getImageView : String? = intent.getStringExtra("itemImage")
        val getProductName : String? = intent.getStringExtra("itemName")
        val getProductDesc : String? = intent.getStringExtra("itemDesc")
        val getProductCategory : String? = intent.getStringExtra("itemCategory")
        val getProductPrice : Int? = intent.getIntExtra("itemPrice", 0)
        val getProductQty : Int? = intent.getIntExtra("itemQty", 0)
        val getProductStatus : String? = intent.getStringExtra("itemStatus")
        val getSellingSuccess : Int? = intent.getIntExtra("itemSellingSuccess", 0)

        ivDetail = findViewById(R.id.iv_detail_image)
        tvProductName = findViewById(R.id.tv_detail_ProductName)
        tvProductDesc = findViewById(R.id.tv_detail_productDescription)
        tvProductCategory = findViewById(R.id.tv_detail_productCategory)
        tvProductPrice = findViewById(R.id.tv_detail_productPrice)
        tvProductQty = findViewById(R.id.tv_detail_productQty)
        tvProductStatus = findViewById(R.id.tv_detail_productStatus)
        etBuyQty = findViewById(R.id.et_buy_quantity)
        buttonOrder = findViewById(R.id.btnOrder)

        // view model with detail page
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        Glide.with(this).load(getImageView).into(ivDetail)

        tvProductName.text = detailViewModel.setProductName(getProductName.toString())
        tvProductDesc.text = detailViewModel.setProductDesc(getProductDesc.toString())
        tvProductCategory.text = detailViewModel.setProductCategory(getProductCategory.toString())
        tvProductPrice.text = detailViewModel.setProductPrice(getProductPrice.toString())
        tvProductQty.text = detailViewModel.setProductQty(getProductQty.toString())
        tvProductStatus.text = detailViewModel.setProductStatus(getProductStatus.toString())

        // btn order clicked
        buttonOrder.setOnClickListener {
            if(etBuyQty.text.isEmpty()){
                Toast.makeText(this, "the quantity must be filled !", Toast.LENGTH_SHORT).show()
            }
            else{

                var flag : Int = 0

                var i = 0
                etBuyQty.text.map {
                    if(etBuyQty.text.get(i) in 'A'..'Z'){
                        flag++
                    }
                    else if(etBuyQty.text.get(i) in 'a'..'z'){
                        flag++
                    }


                    i++
                }

                if(flag != 0)
                {
                    Toast.makeText(this, "the input quantity must valid !", Toast.LENGTH_SHORT).show()
                }
                else if(etBuyQty.text.toString().toInt() > getProductQty.toString().toInt()){
                    Toast.makeText(this, "the input quantity cannot greater than product quantity !", Toast.LENGTH_SHORT).show()
                }
                else{
                    detailViewModel.addProductToOrder(getProductName.toString(), getProductPrice?.toInt()!!, if(getProductQty.toString().toInt() < 5)  "Worst" else getProductStatus.toString() ,etBuyQty.text.toString().toInt())
                    detailViewModel.updateProductQty(getProductId.toString(), getProductCategory.toString(), getProductDesc.toString(), getProductName.toString(), getImageView.toString(), getProductPrice.toInt(), getProductQty?.toInt()!!, if(getProductQty.toString().toInt() < 5)  "Worst" else getProductStatus.toString() , getSellingSuccess?.toInt()!!)
                    val intent = Intent(this, OrderActivity::class.java)
                    intent.putExtra("productImage", getImageView.toString())
                    intent.putExtra("productName", getProductName.toString())
                    intent.putExtra("productQty", etBuyQty.text.toString().toInt())
                    intent.putExtra("totalPrice", etBuyQty.text.toString().toInt() * getProductPrice.toInt())
                    startActivity(intent)
                }

            }
        }

    }
}