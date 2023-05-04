package edu.bluejack22_2.Caddo

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import edu.bluejack22_2.Caddo.ViewModel.OrderViewModel

class OrderActivity : AppCompatActivity() {


    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    private lateinit var ivOrder : ImageView
    private lateinit var tvOrderProductName : TextView
    private lateinit var tvOrderProductQty : TextView
    private lateinit var tvOrderTotalPrice : TextView
    private lateinit var btnFinOrder : Button
    private lateinit var btnCancelOrder : Button

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        ivOrder = findViewById(R.id.iv_order_page)
        tvOrderProductName = findViewById(R.id.tv_order_product_name)
        tvOrderProductQty = findViewById(R.id.tv_order_product_qty)
        tvOrderTotalPrice = findViewById(R.id.tv_order_product_totalPrice)
        btnFinOrder = findViewById(R.id.btn_finalize_order)
        btnCancelOrder = findViewById(R.id.btn_cancel_order)



        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)

        orderViewModel.getUserBaseTransactions()

        var getProductImage : String? = intent.getStringExtra("productImage")
        var getProductName : String? = intent.getStringExtra("productName")
        var getProductQty : Int? = intent.getIntExtra("productQty", 0)
        var getTotalPrice : Int? = intent.getIntExtra("totalPrice", 0)

        Glide.with(this).load(getProductImage).into(ivOrder)
        tvOrderProductName.text = orderViewModel.setProductName(getProductName.toString())
        tvOrderProductQty.text = orderViewModel.setOrderQty(getProductQty.toString().toInt())
        tvOrderTotalPrice.text = orderViewModel.setTotalPrice(getTotalPrice.toString().toInt())



        //fin order
        btnFinOrder.setOnClickListener {
            orderViewModel.addTransaction(getProductName.toString(), getTotalPrice?.toInt()!!, "success")
//            Toast.makeText(this,"the transaction is successful !", Toast.LENGTH_SHORT).show()
            // create notification
            createNotification("The Transaction has been successful !", "Congrats ! your transaction has been successful !")
            sendNotification("The transaction has been successful !", "congrats ! the transaction has been successful !")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // cancel order
        btnCancelOrder.setOnClickListener {
            orderViewModel.addTransaction(getProductName.toString(), getTotalPrice?.toInt()!!, "failed")
//            Toast.makeText(this, "the transaction is failed !", Toast.LENGTH_SHORT).show()
            createNotification("The transaction has been failed !", "Sorry ! Your transaction has been failed ! Please select again !")
            sendNotification("The transaction has been failed !", "sorry ! your transaction has been failed !")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


    // fun create notification for transaction
    private fun createNotification(title : String,desc : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = title
            val descriptionText = desc
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // create notification manager
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    // fun send notification
    private fun sendNotification(title:String, desc:String){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_tpa_mobile)
            .setContentTitle(title)
            .setContentText(desc)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            if (ActivityCompat.checkSelfPermission(
                    this@OrderActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }

}