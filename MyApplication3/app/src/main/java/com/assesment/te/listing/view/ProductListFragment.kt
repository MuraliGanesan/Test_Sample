package com.assesment.te.listing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.assesment.tavant.databinding.FragmentProductListBinding
import com.assesment.te.listing.view.adapter.ProductAdapter
import com.assesment.te.listing.viewmodel.ProductViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by activityViewModels()

    private val productAdapter by lazy { ProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = productAdapter
        productViewModel.getProducts()
        observeData()
        with(productAdapter) {
            onItemClick = { productData ->
                productViewModel.getFavouriteList()
                findNavController().navigate(
                    ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                        productData
                    )
                )
            }
            onFavouriteIconClick = { productData ->
                if (productData.isFavourite) {
                    productViewModel.removeFavourite(productData)
                } else {
                    productViewModel.addFavourite(productData)
                }
            }
        }
    }

    private fun observeData() {
        with(binding) {
            productViewModel.productList.observe(viewLifecycleOwner) { productState ->
                when (productState) {
                    is ProductViewModel.ProductListState.Loading -> {
                        progressBar.isVisible = true
                        recyclerView.isVisible = false
                        txtNoDataFound.isVisible = false
                    }

                    is ProductViewModel.ProductListState.Success -> {
                        productAdapter.submitList(productState.productList)
                        progressBar.isVisible = false
                        recyclerView.isVisible = true
                        txtNoDataFound.isVisible = false
                    }

                    is ProductViewModel.ProductListState.Failure -> {
                        progressBar.isVisible = false
                        recyclerView.isVisible = false
                        txtNoDataFound.isVisible = true
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}