package com.bedu.eatapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bedu.eatapp.databinding.ActivityDetailBinding
import com.bedu.eatapp.entities.MealResponse
import com.bedu.eatapp.interfaces.GetDataService
import com.bedu.eatapp.retrofitclient.RetrofitClientInstance
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest

class DetailActivity : BaseActivity() {


    var youtubeLink = "https://www.youtube.com/watch?v=tQswcatVnuc"
    private lateinit var binding: ActivityDetailBinding

    private val CAMERA_PERMISSION_REQUEST_CODE = 123
    private val CAMERA_REQUEST_CODE = 456


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var id = intent.getStringExtra("id")
        getSpecificItem(id!!)

        binding.imgToolbarBtnBack.setOnClickListener {
            finish()
        }

        binding.btnYouTube.setOnClickListener {
            val uri: Uri =
                Uri.parse(youtubeLink)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        val btnCamera = findViewById<Button>(R.id.btnCamera)

        btnCamera.setOnClickListener {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "Se requiere permiso de cámara",
                    CAMERA_PERMISSION_REQUEST_CODE,
                    Manifest.permission.CAMERA
                )
            }
        }

    }

    fun getSpecificItem(id : String){
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getSpecificItem(id)
        call.enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {

                Glide.with(this@DetailActivity)
                    .load(response.body()!!.mealsEntity[0].strMealThumb).into(binding.imgItem)

                binding.nameCategory.text = response.body()!!.mealsEntity[0].strMeal

                var ingredient =
                    "${response.body()!!.mealsEntity[0].strIngredient1} ${response.body()!!.mealsEntity[0].strMeasure1}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient2} ${response.body()!!.mealsEntity[0].strMeasure2}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient3} ${response.body()!!.mealsEntity[0].strMeasure3}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient4} ${response.body()!!.mealsEntity[0].strMeasure4}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient5} ${response.body()!!.mealsEntity[0].strMeasure5}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient6} ${response.body()!!.mealsEntity[0].strMeasure6}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient7} ${response.body()!!.mealsEntity[0].strMeasure7}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient8} ${response.body()!!.mealsEntity[0].strMeasure8}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient9} ${response.body()!!.mealsEntity[0].strMeasure9}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient10} ${response.body()!!.mealsEntity[0].strMeasure10}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient11} ${response.body()!!.mealsEntity[0].strMeasure11}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient12} ${response.body()!!.mealsEntity[0].strMeasure12}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient13} ${response.body()!!.mealsEntity[0].strMeasure13}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient14} ${response.body()!!.mealsEntity[0].strMeasure14}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient15} ${response.body()!!.mealsEntity[0].strMeasure15}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient16} ${response.body()!!.mealsEntity[0].strMeasure16}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient17} ${response.body()!!.mealsEntity[0].strMeasure17}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient18} ${response.body()!!.mealsEntity[0].strMeasure18}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient19} ${response.body()!!.mealsEntity[0].strMeasure19}\n" +
                            "${response.body()!!.mealsEntity[0].strIngredient20} ${response.body()!!.mealsEntity[0].strMeasure20}\n"

                binding.tvIngredients.text = ingredient
                binding.tvInstructions.text = response.body()!!.mealsEntity[0].strInstructions

                if (response.body()!!.mealsEntity[0].strSource != null){
                    youtubeLink = response.body()!!.mealsEntity[0].strSource.toString()
                }else{
                    binding.btnYouTube.visibility = View.GONE
                }
            }

           override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Algo salió mal!!!", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result : ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(imageBitmap)
        }
    }



}
