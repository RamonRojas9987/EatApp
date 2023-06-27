package com.bedu.eatapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bedu.eatapp.entities.Category
import com.bedu.eatapp.entities.CategoryItems
import com.bedu.eatapp.entities.MealsItems
import com.bedu.eatapp.entities.Recipes

@Dao
interface RecipieDao {

    @Query("Select * from categoryitems Order BY id DESC")
    suspend fun getAllCategory(): List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: MealsItems?)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

    @Query("Select * from MealItems Where categoryName = :categoryName Order BY id DESC")
    suspend fun getSpecificMealList(categoryName: String): List<MealsItems>

}