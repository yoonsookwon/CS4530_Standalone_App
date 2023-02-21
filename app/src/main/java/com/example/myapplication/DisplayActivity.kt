package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class DisplayActivity : AppCompatActivity(), View.OnClickListener  {

    var mIvThumbnail: ImageView? = null

    //Create variables for the UI elements that we need to control
    private var mButtonSubmit: Button? = null
    private var mButtonCamera: Button? = null

    //Create variables to hold the strings
    private var mFirstName: EditText? = null
    private var mMiddleName: EditText? = null
    private var mLastName: EditText? = null

    private var fName: String? = null
    private var mName: String? = null
    private var lName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get the buttons IDs
        mButtonCamera = findViewById(R.id.button_pic)
        mButtonSubmit = findViewById(R.id.submit_btn)

        //Say that this class itself contains the listener.
        mButtonCamera!!.setOnClickListener(this)
        mButtonSubmit!!.setOnClickListener(this)

        //Get the image view
        mIvThumbnail = findViewById<View>(R.id.imageView_pic) as ImageView

        //Get the starter intent
        val receivedIntent = intent

        //Set the image view
        val imagePath = receivedIntent.getStringExtra("imagePath")
        val thumbnailImage = BitmapFactory.decodeFile(imagePath)
        if (thumbnailImage != null) {
            mIvThumbnail!!.setImageBitmap(thumbnailImage)
        }
    }

    override fun onClick(view: View)
    {
        when (view.id) {
            R.id.submit_btn -> {
                mFirstName = findViewById(R.id.first_edit_data)
                mMiddleName = findViewById(R.id.middle_edit_data)
                mLastName = findViewById(R.id.last_edit_data)

                fName = mFirstName!!.text.toString()
                mName = mMiddleName!!.text.toString()
                lName = mLastName!!.text.toString()

                if (fName.isNullOrBlank() || lName.isNullOrBlank()) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "You must enter First,Last name!",
                        Toast.LENGTH_LONG
                    )
                    toast.setGravity(Gravity.CENTER, 0, 20)
                    toast.show()
                }
                else {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("FirstName", fName)
                    intent.putExtra("MiddleName", mName)
                    intent.putExtra("LastName", lName)
                    startActivity(intent)
                }
            }
        }
    }
}