package com.example.myapplication


import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Create variables to hold the strings
    private var mFirstName: EditText? = null
    private var mMiddleName: EditText? = null
    private var mLastName: EditText? = null

    private var fName: String? = null
    private var mName: String? = null
    private var lName: String? = null

    //Create variables for the UI elements that we need to control
    private var mButtonSubmit: Button? = null
    private var mButtonCamera: Button? = null

    //Create the variable for the ImageView that holds the thumbnail  pic
    private var mIvPic: ImageView? = null
    private var isTookPicture : Boolean = false

    //Define a bitmap
    private var mThumbnailImage: Bitmap? = null
    //Define a global intent variable
    private var mDisplayIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get the buttons IDs
        mButtonCamera = findViewById(R.id.button_pic)
        mButtonSubmit = findViewById(R.id.submit_btn)

        //Say that this class itself contains the listener.
        mButtonCamera!!.setOnClickListener(this)
        mButtonSubmit!!.setOnClickListener(this)

        //Create the intent but don't start the activity yet
        mDisplayIntent = Intent(this, DisplayActivity::class.java)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.submit_btn ->{
                mFirstName = findViewById(R.id.first_edit_data)
                mMiddleName = findViewById(R.id.middle_edit_data)
                mLastName = findViewById(R.id.last_edit_data)

                fName = mFirstName!!.text.toString()
                mName = mMiddleName!!.text.toString()
                lName = mLastName!!.text.toString()

                if (fName.isNullOrBlank() || lName.isNullOrBlank()) {
                    val toast = Toast.makeText(applicationContext, "You must enter First,Last name!", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0,20)
                    toast.show()
                }
                else if(!isTookPicture)
                {
                    val toast = Toast.makeText(applicationContext, "You must take picture!", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0,60)
                    toast.show()
                }
                else
                {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("FirstName", fName)
                    intent.putExtra("MiddleName", mName)
                    intent.putExtra("LastName", lName)
                    startActivity(intent)
                }
            }
            R.id.button_pic -> {
                //The button press should open a camera
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                   try {
                        cameraActivity.launch(cameraIntent)
                    } catch (ex: ActivityNotFoundException) {
                        //Do error handling here
                    }

            }
        }
    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK) {
            val extras = result.data!!.extras
            mThumbnailImage = extras!!["data"] as Bitmap?

            //Open a file and write to it
            if (isExternalStorageWritable) {
                val filePathString = saveImage(mThumbnailImage)
                mDisplayIntent!!.putExtra("imagePath", filePathString)
                startActivity(mDisplayIntent) //explicit intent
            } else {
                Toast.makeText(this, "External storage not writable.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImage(finalBitmap: Bitmap?): String {
        val root = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fname = "Thumbnail_$timeStamp.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(this, "file saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

}