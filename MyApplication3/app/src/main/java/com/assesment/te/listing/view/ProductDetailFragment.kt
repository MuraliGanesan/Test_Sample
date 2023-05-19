package com.assesment.te.listing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.assesment.tavant.R
import com.assesment.tavant.databinding.FragmentProductDetailBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            txtProductTitle.text = args.productData.title
            txtProductDescription.text = args.productData.description
            txtProductCategory.text = args.productData.category
            ratingBar.rating = args.productData.rating.rate.toFloat()
//            Glide.with(imgProduct.context).load(args.productData.image)
//                .error(R.drawable.ic_launcher_background).into(imgProduct)
            btnFavourite.setOnClickListener {
                findNavController().navigate(R.id.action_ProductDetailFragment_to_ProductFavoriteList)
            }
        }
//        binding.textviewSecond.text = args.productData.description
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_ProductDetailFragment_to_ProductListFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}