package com.bedu.eatapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bedu.eatapp.dao.RecipieDao
import com.bedu.eatapp.entities.Category
import com.bedu.eatapp.entities.CategoryItems
import com.bedu.eatapp.entities.Meal
import com.bedu.eatapp.entities.MealsItems
import com.bedu.eatapp.entities.Recipes
import com.bedu.eatapp.entities.converter.CategoryListConverter
import com.bedu.eatapp.entities.converter.MealListConverter

@Database(entities = [Recipes::class, CategoryItems::class, Category::class, Meal::class, MealsItems::class], version = 2, exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{
        var recipesDatabase:RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if (recipesDatabase == null){
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao(): RecipieDao

}