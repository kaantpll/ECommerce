package com.example.ecommerce.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.ecommerce.R
import com.example.ecommerce.activities.AddProductActivity
import com.example.ecommerce.databinding.FragmentDashboardBinding
import com.example.ecommerce.databinding.FragmentProductsBinding



class ProductsFragment : Fragment() {

    private var _binding : FragmentProductsBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       _binding= FragmentProductsBinding.inflate(inflater,container, false)
        val view = binding.root

        setHasOptionsMenu(true)




        return view
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addProduct -> {
               val intent = Intent(requireContext(),AddProductActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {


        val item = menu.findItem(R.id.cart)
        val item2 =menu.findItem(R.id.searchIcon)
        item2.isVisible = false
        item.isVisible = false



        super.onPrepareOptionsMenu(menu)
    }




}