package com.dineshprabha.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.databinding.LayoutSearchItemBinding
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.FOLLOW
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchAdapter(var context: Context, var userList: ArrayList<User>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : LayoutSearchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var isFollow = false


        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.user1) .into(holder.binding.userImage)
        holder.binding.tvName.text = userList.get(position).name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).whereEqualTo("email", userList.get(position).email)
            .get().addOnSuccessListener {
                if (it.documents.size == 0){
                    isFollow = false
                }
                else{
                    holder.binding.btnFollow.text = "UnFollow"
                    isFollow = true
                }
            }

        holder.binding.btnFollow.setOnClickListener {
            if (!isFollow){
                Firebase.firestore.collection(Firebase.auth.currentUser?.uid + FOLLOW).document()
                    .set(userList.get(position))
                holder.binding.btnFollow.text = "UnFollow"
                isFollow = true
            }
            else{
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).whereEqualTo("email", userList.get(position).email)
                    .get().addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document(it.documents.get(0).id).delete()
                        holder.binding.btnFollow.text = "Follow"
                        isFollow =false
                    }

            }

        }

    }
}