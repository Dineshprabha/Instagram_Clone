package com.dineshprabha.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.databinding.LayoutReelsDisplayBinding
import com.dineshprabha.instagramclone.model.Reel
import com.squareup.picasso.Picasso

class ReelAdapter(var context: Context, var reelList: ArrayList<Reel>):
    RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: LayoutReelsDisplayBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = LayoutReelsDisplayBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.user1).into(holder.binding.imgUserPhoto)

        holder.binding.etCaption.setText(reelList.get(position).caption)
        holder.binding.vvReel.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.vvReel.setOnPreparedListener {
            holder.binding.reelProgressBar.visibility = View.GONE
            holder.binding.vvReel.start()
        }
    }

}