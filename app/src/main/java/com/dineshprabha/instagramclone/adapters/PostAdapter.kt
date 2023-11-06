package com.dineshprabha.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.databinding.LayoutRvHomePostsBinding
import com.dineshprabha.instagramclone.model.Post
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class PostAdapter(var context: Context, var postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>(){

    inner class MyHolder(var binding : LayoutRvHomePostsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = LayoutRvHomePostsBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get()
                .addOnSuccessListener {
                    var  user = it.toObject<User>()
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.user1).into(holder.binding.imgUserPhoto)
                    holder.binding.txtName.text = user.name

                }

            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.txtTime.text = text

        }catch (e:Exception){
            holder.binding.txtTime.text = ""
        }

        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.imgHomePost)


        holder.binding.etCaption.text = postList.get(position).caption

        holder.binding.imgLike.setOnClickListener {
            var like:Boolean= true

            if (like){
                holder.binding.imgLike.setImageResource(R.drawable.heart_red)
                like = false
            }else{
                holder.binding.imgLike.setImageResource(R.drawable.heart)
            }

        }

        holder.binding.imgSend.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, postList.get(position).postUrl)
            context.startActivity(i)
        }

    }
}