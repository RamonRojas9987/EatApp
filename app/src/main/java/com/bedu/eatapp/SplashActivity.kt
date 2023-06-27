package com.bedu.eatapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bedu.eatapp.database.RecipeDatabase
import com.bedu.eatapp.databinding.ActivitySplashBinding
import com.bedu.eatapp.entities.Category
import com.bedu.eatapp.entities.Meal
import com.bedu.eatapp.entities.MealsItems
import com.bedu.eatapp.interfaces.GetDataService
import com.bedu.eatapp.retrofitclient.RetrofitClientInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks {
    private var REAd_STORAGE_PERM = 123


    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        readStorageTask()

        binding.btnGetStarted.setOnClickListener {
            var intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
            finish()
        }
    }



    fun getCategories(){
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category>{
            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                for (arr in response.body()!!.categorieitems!!){
                    getMeal(arr.strCategory)
                }
                insertDataIntoRoomDb(response.body())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(this@SplashActivity, "Algó salio mal!!!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getMeal(categoryName: String){
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal>{
            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                insertMealDataIntoRoomDb(categoryName,response.body())
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                binding.loader.visibility = View.INVISIBLE
                Toast.makeText(this@SplashActivity, "Algó salio mal!!!", Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun insertDataIntoRoomDb(category: Category?){

        launch {
            this.let {
                for (arr in category!!.categorieitems!!) {
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertCategory(arr)
                }
            }
        }
    }

    fun insertMealDataIntoRoomDb(categoryName: String ,meal: Meal?){

        launch {
            this.let {
                for (arr in meal!!.mealsItem!!){
                    var mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb

                    )
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData", arr.toString())
                }

                binding.btnGetStarted.visibility = View.VISIBLE

            }
        }

    }

    fun clearDataBase(){
            launch {
                this.let {
                    RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
                }
            }
        }

    private fun hasReadStoragePermission():Boolean{
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask(){
        if (hasReadStoragePermission()){
            clearDataBase()
            getCategories()
    }else{
            EasyPermissions.requestPermissions(
                this,
                "Está aplicación necesita acceder al almacenamiento",
                REAd_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
    }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

}