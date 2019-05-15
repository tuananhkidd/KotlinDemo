package com.example.kotlinapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_splass.*

class SplassActivity : AppCompatActivity(), ImageAdapter.OnClickAddItem {
    var uris: ArrayList<Image?> = ArrayList()
    var imageAdapter: ImageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splass)

        uris.add(null)
        imageAdapter = ImageAdapter(this, uris, this)
        rcv_test.adapter = imageAdapter

        btn_show.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog_test)
        dialog.setCancelable(true)
//        val params = dialog.window!!.attributes
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
        dialog.show()

    }

    override fun onClickAddButton() {
        requestPermission()
    }

    override fun onCancleImage(position: Int) {
        uris.removeAt(position)
        imageAdapter!!.notifyItemRemoved(position)
    }

    fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            openGallery()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openGallery()
                } else {
                    Toast.makeText(this, "Permission Denied!!", Toast.LENGTH_SHORT).show();
                }
                return
            }
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                if (resultCode == Activity.RESULT_OK) {
                    var oldSize = uris.size - 1
                    uris.add(oldSize, Image(data?.data!!))
                    imageAdapter?.notifyItemInserted(oldSize)
                }
            }
        }
    }

    companion object {
        val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000
        val REQUEST_SELECT_IMAGE_IN_ALBUM = 1001

    }
}
