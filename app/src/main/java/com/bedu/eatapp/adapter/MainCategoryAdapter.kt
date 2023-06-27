package com.bedu.eatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.eatapp.R
import com.bedu.eatapp.databinding.ItemRvMainCategoryBinding
import com.bedu.eatapp.entities.CategoryItems
import com.bedu.eatapp.entities.Recipes
import com.bumptech.glide.Glide

class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    private lateinit var binding: ItemRvMainCategoryBinding

    var listener: OnItemClickListener? = null
    var ctx : Context? = null
    var arrMainCategory = ArrayList<CategoryItems>()
    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    fun setData(arrData : List<CategoryItems>){
        arrMainCategory = arrData as ArrayList<CategoryItems>
    }

    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false))
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val currentItem = arrMainCategory[position]

        binding = ItemRvMainCategoryBinding.bind(holder.itemView)

        binding.Comida1.text = currentItem.strCategory

        Glide.with(ctx!!).load(currentItem.strCategoryThumb).into(binding.imgDish)
            holder.itemView.rootView.setOnClickListener {
                listener!!.onClicked(currentItem.strCategory)
            }
    }

    interface  OnItemClickListener{
        fun onClicked(categoryName: String)
    }

}