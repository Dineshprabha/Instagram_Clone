package com.dineshprabha.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.databinding.LayoutRvFollowItemsBinding
import com.dineshprabha.instagramclone.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FollowAdapter(var context: Context, var followList: ArrayList<User>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>(){

    inner class ViewHolder(var binding : LayoutRvFollowItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = LayoutRvFollowItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load( followList.get(position).image).placeholder(R.drawable.user1).into(holder.binding.userImage)

        holder.binding.txtFollowName.text = followList.get(position).name

    }
}