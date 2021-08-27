package com.example.ecommerce.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentDashboardBinding
import com.synnapps.carouselview.ImageListener


class DashboardFragment : Fragment() {

    private val images = arrayListOf(R.drawable.n11,R.drawable.t,R.drawable.he)

    private var _binding : FragmentDashboardBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

          _binding = FragmentDashboardBinding.inflate(inflater, container, false)
          val view = binding.root

        setHasOptionsMenu(true)

        setCarousel()

        return view;

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