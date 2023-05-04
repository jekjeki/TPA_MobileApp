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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import edu.bluejack22_2.Caddo.ViewModel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etAddress : EditText
    private lateinit var etPassword : EditText

    // data for push notification
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    private lateinit var viewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.register_et_name)
        etEmail = findViewById(R.id.register_et_email)
        etAddress = findViewById(R.id.register_et_address)
        etPassword = findViewById(R.id.register_et_password)

        var btnRegister = findViewById<Button>(R.id.btn_register)



        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


        btnRegister.setOnClickListener {
            var isTrue : Boolean = true
            var isFive : Boolean = true
            var isEmailFormat : Boolean = true

            var isLenAddress : Boolean = true

            // password validation flag
            var isPasswordLen : Boolean = true
            var isUppercase : Boolean = false
            var isSpecial : Boolean = false
            var isNumber : Boolean = false

            // notification channel
            createNotifChannel()

            // validation is empty
            if(etName.text.toString().isEmpty() || etAddress.text.toString().isEmpty()
                || etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty())
            {
                Toast.makeText(this, "The field cannot empty !", Toast.LENGTH_SHORT).show()
                isTrue = false
            }
            // validation name length
            else if(etName.text.toString().length < 5){
                Toast.makeText(this, "the name length less than 5 characters",Toast.LENGTH_SHORT).show()
                isFive = false
            }
            // validation email
            else if(!(etEmail.text.toString().endsWith(".com") || etEmail.text.toString().endsWith(".co.id"))){
                Toast.makeText(this, "the email must ends with .com or .co.id", Toast.LENGTH_SHORT).show()
                isEmailFormat = false
            }
            // validation address len
            else if(etAddress.text.toString().length < 10)
            {
                Toast.makeText(this, "the address length must be more than 10 characters", Toast.LENGTH_SHORT).show()
                isLenAddress = false
            }
            // validation password len
            else if(etPassword.text.toString().length < 6)
            {
                Toast.makeText(this, "the password length must above than 6 characters", Toast.LENGTH_SHORT).show()
                isPasswordLen = false
            }


            // password validation 2
            var i = 0
            etPassword.text.toString().map {

                // check uppercase
                if(etPassword.text.get(i) in 'A'..'Z'){
                    isUppercase = true
                }

                // check contains number
                if(etPassword.text.get(i) in '0'..'9'){
                    isNumber = true
                }

                if(etPassword.text.get(i) == '!' || etPassword.text.get(i) == '@'
                    || etPassword.text.get(i) == '#')
                {
                    isSpecial = true
                }

                i++
            }

            if(!isUppercase){
                Toast.makeText(this, "the password must have uppercase", Toast.LENGTH_SHORT).show()
            }
            else if(!isNumber){
                Toast.makeText(this, "the password must have number !", Toast.LENGTH_SHORT).show()
            }
            else if(!isSpecial){
                Toast.makeText(this, "the password must contain special format !", Toast.LENGTH_SHORT).show()
            }
            else if(isTrue && isFive && isEmailFormat && isLenAddress && isPasswordLen && isUppercase && isNumber && isSpecial)
            {


                sendNotification()

                Toast.makeText(this, "${etEmail.text.toString()}", Toast.LENGTH_SHORT).show()
                viewModel.createNewUser(etEmail.text.toString(), etPassword.text.toString())
                viewModel.insertUserData(etName.text.toString(),etEmail.text.toString(),etAddress.text.toString(), etPassword.text.toString())
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }

    }

    // func create notif channel
    private fun createNotifChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name = "Register Success !"
            val descriptionText = "Congrats ! Your account has been created !"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description=descriptionText
            }

            // create notification manager
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    // send notification
    private fun sendNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_tpa_mobile)
            .setContentTitle("Your Register has been success !")
            .setContentText("Congrats ! registration has been successful !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            if (ActivityCompat.checkSelfPermission(
                    this@RegisterActivity,
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