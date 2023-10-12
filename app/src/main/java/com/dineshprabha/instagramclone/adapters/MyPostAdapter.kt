package com.dineshprabha.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dineshprabha.instagramclone.databinding.LayoutMyPostItemsBinding
import com.dineshprabha.instagramclone.model.Post
import com.squareup.picasso.Picasso

class MyPostAdapter (var context: Context,  var postList: ArrayList<Post>) :
    RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {


    inner class ViewHolder(var binding: LayoutMyPostItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = LayoutMyPostItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Picasso.get().load(postList.get(position).postUrl).into(holder.binding.imageMyPost)
    }
}