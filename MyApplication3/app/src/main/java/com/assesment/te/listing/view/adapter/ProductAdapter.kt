package com.assesment.te.listing.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assesment.tavant.R
import com.assesment.tavant.databinding.ItemProductListBinding

import com.assesment.te.listing.data.model.ProductData
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProductAdapter @Inject constructor() :
    ListAdapter<ProductData, ProductAdapter.ProductItemViewHolder>(
        ProductListDiffUtil()
    ) {
    var onItemClick: ((ProductData) -> Unit)? = null
    var onFavouriteIconClick: ((ProductData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding = ItemProductListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ProductItemViewHolder(
        private val binding: ItemProductListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productData: ProductData) {
            with(binding) {
                txtProductTitle.text = productData.title
                txtProductDescription.text = productData.description
                txtProductCategory.text =
                    txtProductCategory.context.getString(R.string.category, productData.category)
                ratingBar.rating = productData.rating.rate.toFloat()
                txtProductPrice.text = productData.price.toString()
                Glide.with(itemView.context).load(productData.image)
                    .error(R.drawable.ic_launcher_background).into(imgProductResult)
                productContainer.setOnClickListener {
                    onItemClick?.invoke(productData)
                }
                imgFavourite.setOnClickListener {
                    onFavouriteIconClick?.invoke(productData)
                }
                Log.e("TAG","----->"+productData.isFavourite)
                if(productData.isFavourite){
                    imgFavourite.setImageResource(R.drawable.baseline_favorite_24)
                }else{
                    imgFavourite.setImageResource(R.drawable.baseline_favorite_gray_24)
                }

            }
        }
    }


    class ProductListDiffUtil : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(
            oldItem: ProductData,
            newItem: ProductData
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ProductData,
            newItem: ProductData
        ): Boolean =
            oldItem == newItem
    }
}