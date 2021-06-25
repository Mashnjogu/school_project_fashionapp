package com.android.demo.chachishafashi

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.demo.chachishafashi.databinding.FragmentLoginBinding
import com.android.demo.chachishafashi.databinding.FragmentProductBinding
import com.android.demo.chachishafashi.databinding.ProductItemBinding
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductFragment : Fragment() {

   lateinit var binding: FragmentProductBinding
   lateinit var adapter: FirebaseRecyclerAdapter<ProductInfo, PViewHolder>

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Data")

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)

        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.shop -> {view?.findNavController()?.navigate(R.id.action_productFragment_to_purchaseFragment)}
                R.id.profile -> {view?.findNavController()?.navigate(R.id.action_productFragment_to_profileFragment)}
            }
            true
        }
//        productRec.setHasFixedSize(true)
        loadImages()
        return binding.root
    }

//   fun loadImages(){
//    val query: Query = myRef.limitToLast(20)
//    val options = FirebaseRecyclerOptions.Builder<ProductInfo>().setQuery(query, ProductInfo::class.java)
//            .setLifecycleOwner(this).build()
//
//        adapter = object : FirebaseRecyclerAdapter<ProductInfo, PViewHolder>(options){
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding1 = ProductItemBinding.inflate(layoutInflater, parent, false)
//            return PViewHolder(binding1)
//        }
//
//        override fun onBindViewHolder(p0: PViewHolder, p1: Int, p2: ProductInfo) {
//            p0.productText.setText(p2.text)
//            Picasso.get().load(p2.image).into(p0.productImage)
//        }
//    }
//            val productRec = binding.displayProducts
//
//    productRec.adapter = adapter
//}

    fun loadImages() = CoroutineScope(Dispatchers.IO).launch {
        val query: Query = myRef.limitToLast(20)
    val options = FirebaseRecyclerOptions.Builder<ProductInfo>().setQuery(query, ProductInfo::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<ProductInfo, PViewHolder>(options){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding1 = ProductItemBinding.inflate(layoutInflater, parent, false)
            return PViewHolder(binding1)
        }

        override fun onBindViewHolder(p0: PViewHolder, p1: Int, p2: ProductInfo) {
            p0.productText.setText(p2.text)
            Picasso.get()
                    .load(p2.image)
                    .resize(396, 307)
                    .onlyScaleDown()
                    .centerInside()
                    .into(p0.productImage)

            //item click
//            p0.productImage.setOnClickListener {
//+
//            }
        }
    }
        withContext(Dispatchers.Main){
            val productRec = binding.displayProducts
            productRec.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

class PViewHolder(binding1: ProductItemBinding): RecyclerView.ViewHolder(binding1.root){

    val productImage = binding1.product
    val productText = binding1.productText

}