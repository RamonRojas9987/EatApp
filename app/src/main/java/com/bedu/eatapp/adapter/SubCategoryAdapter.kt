package com.bedu.eatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedu.eatapp.R
import com.bedu.eatapp.databinding.ItemRvMainCategoryBinding
import com.bedu.eatapp.databinding.ItemRvSubCategoryBinding
import com.bedu.eatapp.entities.Meal
import com.bedu.eatapp.entities.MealsItems
import com.bedu.eatapp.entities.Recipes
import com.bumptech.glide.Glide

class SubCategoryAdapter: RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    var listener: SubCategoryAdapter.OnItemClickListener? = null
    var ctx : Context? = null

    private lateinit var binding: ItemRvSubCategoryBinding

    var arrSubCategory = ArrayList<MealsItems>()
    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    fun setData(arrData : List<MealsItems>){
        arrSubCategory = arrData as ArrayList<MealsItems>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_sub_category, parent, false))
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    fun setClickListener(listener1: SubCategoryAdapter.OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val currentItem = arrSubCategory[position]

        binding = ItemRvSubCategoryBinding.bind(holder.itemView)

        binding.Comida1.text = currentItem.strMeal

        Glide.with(ctx!!).load(currentItem.strMealThumb).into(binding.imgDish)

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClicked(currentItem.idMeal)
        }


    }

    interface  OnItemClickListener{
        fun onClicked(id: String)
    }

}