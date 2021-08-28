package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.model.Product
import org.w3c.dom.Text

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    class MyViewHolder(var view : View) : RecyclerView.ViewHolder(view)

    private val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerViewDiff = AsyncListDiffer(this,diffUtil)

    var productListAdapter : List<Product>
    get() = recyclerViewDiff.currentList
    set(value) = recyclerViewDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_cart_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val cardTitle = holder.view.findViewById<TextView>(R.id.productTitleCard)
        val cardDescription = holder.view.findViewById<TextView>(R.id.productDescription)
        val discountValue = holder.view.findViewById<TextView>(R.id.cardDiscountValue)
        var productNewValue = holder.view.findViewById<TextView>(R.id.productNewValue)
        val productOldValue = holder.view.findViewById<TextView>(R.id.productOldValue)
        val productImage = holder.view.findViewById<ImageView>(R.id.productImage)

        val currentProduct = recyclerViewDiff.currentList[position]
        val list = productListAdapter[position]

//        productNewValue = ((list.price.toInt() * list.discountValue.toInt()) / 100) as TextView

        holder.view.apply {
            cardTitle.text = "${list.title}"
           // cardDescription.text="${list.description}"
            discountValue.text = "${list.discountValue+"% OFF"}"
           // productOldValue.text = "${list.price}"
            Glide.with(this).load(list.image).into(productImage)

        }

    }

    override fun getItemCount(): Int {
       return productListAdapter.size
    }
}