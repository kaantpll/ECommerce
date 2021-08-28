package com.example.ecommerce.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.adapter.ProductAdapter
import com.example.ecommerce.databinding.FragmentDashboardBinding
import com.example.ecommerce.model.Product
import com.example.ecommerce.utilities.constant.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.launch


class DashboardFragment : Fragment() {

    private val images = arrayListOf(R.drawable.n11,R.drawable.t,R.drawable.he)

    private var _binding : FragmentDashboardBinding? = null;
    private val binding get() = _binding!!


    private lateinit var db :FirebaseFirestore
    private lateinit var auth : FirebaseFirestore

    private lateinit var productAdapter : ProductAdapter
    private lateinit var productList : ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

          _binding = FragmentDashboardBinding.inflate(inflater, container, false)
          val view = binding.root

        db = FirebaseFirestore.getInstance()
        auth = FirebaseFirestore.getInstance()

        productList = ArrayList<Product>()
        productAdapter = ProductAdapter()
        setHasOptionsMenu(true)
        setCarousel()

        getDataFromRemote()

        setRecyclerView()

        return view;

    }

    private fun setRecyclerView() {
        binding.productRv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.productRv.adapter = productAdapter
        productAdapter.notifyDataSetChanged()
    }

    private fun getDataFromRemote() {
        db.collection(Constants.PRODUCT_COLLECTION_NAME).get().addOnCompleteListener { task->
            if(task.isSuccessful){
              for(document in task.result!!){
                 // Log.d("TAG",document.id+"=>"+document.data)
                  val productTitle = document.getString("productTitle") as String
                  val productDescription = document.getString("productDescription") as String
                  val discountValue = document.getString("discountValue") as String
                  val imageUrl = document.getString("imageUrl") as String
                  val price = document.getString("price") as String
                  val stock = document.getString("stock")  as String
                  val userId = document.getString("user_id") as String

                  val product = Product(productDescription,imageUrl,price,stock,productTitle,userId,discountValue)
                  productList.add(product)

                  lifecycleScope.launch {
                      productAdapter.productListAdapter = productList
                  }
              }
            }
        }
    }



    private fun setCarousel(){
        binding.carouselView.pageCount = images.size
        var imageListener = ImageListener{position, imageView -> imageView.setImageResource(images[position]) }

        binding.carouselView.setImageListener(imageListener)

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}