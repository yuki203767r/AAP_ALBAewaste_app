package com.google.codelabs.buildyourfirstmap

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.ByteArrayOutputStream


class Registration : AppCompatActivity() {

    private lateinit var button : Button
    private lateinit var imageView : ImageView
    private lateinit var myDB: MyDBAdapter
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        myDB = MyDBAdapter(this)
        button = findViewById(R.id.btn_pick_img)
        imageView = findViewById(R.id.img_save)


        var actionBar = getSupportActionBar()

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        button.setOnClickListener {
            pickImageGallery()
        }


        signin.setOnClickListener {
            signin.setTextColor(Color.parseColor("#FFFFFF"))
            signin.setBackgroundColor(Color.parseColor("#FF2729C3"))
            signup.setTextColor(Color.parseColor("#FF2729C3"))
            signup.setBackgroundResource(R.drawable.bordershape)
            circleImageView.setImageResource(R.drawable.sigin_boy_img)
            forgot_password.visibility = View.VISIBLE
            img_save.visibility = View.GONE
            btn_pick_img.visibility = View.GONE
            signup_btn.visibility = View.GONE
            signin_btn.visibility = View.VISIBLE

        }

        signin_btn.setOnClickListener {
            //Toast.makeText(this, "this works", Toast.LENGTH_SHORT).show()
            //if (myDB!!.checkUserLogin(username.text.toString(), password.text.toString())) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
            /* } else {
                 Toast.makeText(
                     applicationContext,
                     "Wrong Username or Password",
                     Toast.LENGTH_LONG
                 ).show()
             }*/
        }

        signup.setOnClickListener {
            signup.setTextColor(Color.parseColor("#FFFFFF"))
            signup.setBackgroundColor(Color.parseColor("#FF2729C3"))
            signin.setTextColor(Color.parseColor("#FF2729C3"))
            signin.setBackgroundResource(R.drawable.bordershape)
            circleImageView.setImageResource(R.drawable.sigup_boy_img)
            forgot_password.visibility = View.INVISIBLE
            img_save.visibility = View.VISIBLE
            btn_pick_img.visibility = View.VISIBLE
            signup_btn.visibility = View.VISIBLE
            signin_btn.visibility = View.GONE


            signup_btn.setOnClickListener {
                if (username.text.isNullOrEmpty()){
                    username.error = "field empty"
                }
                if (password.text.isNullOrEmpty()){
                    password.error = "field empty"
                }
                else {
                    insertDataToDatabase()
                }
            }

        }
    }


    private fun insertDataToDatabase(){
        val usern = username.text.toString()
        val passw = password.text.toString()
        val pfp =(img_save.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        pfp.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()

        val user = UserDisplays(usern, passw, image)
        var addUser = user.getname()
        var addPass = user.getpass()
        var addPfp = user.getpfp()

        val mc = MyUsers.ourInstance
        mc.addToDatabase(addUser,addPass,addPfp, applicationContext)
        Toast.makeText(this, "Successfully registered!", Toast.LENGTH_LONG ).show()

    }


    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data?.data)
        }
    }

}