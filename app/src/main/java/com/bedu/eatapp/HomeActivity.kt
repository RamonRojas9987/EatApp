package com.bedu.eatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.eatapp.adapter.MainCategoryAdapter
import com.bedu.eatapp.adapter.SubCategoryAdapter
import com.bedu.eatapp.database.RecipeDatabase
import com.bedu.eatapp.databinding.ActivityHomeBinding
import com.bedu.eatapp.entities.CategoryItems
import com.bedu.eatapp.entities.MealsItems
import com.bedu.eatapp.entities.Recipes
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)




    }

    private val onClicked = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }

    }

    private val onClickedSubItem = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }

    private fun getDataFromDb(){
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strCategory)
                mainCategoryAdapter.setData(arrMainCategory)
                binding.rvMainCategory.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.rvMainCategory.adapter = mainCategoryAdapter
            }
        }
    }

    private fun getMealDataFromDb(categoryName: String){
        binding.nameCategory.text = "$categoryName Category"
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                binding.rvSubCategory.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.rvSubCategory.adapter = subCategoryAdapter
            }
        }
    }

}