package com.dineshprabha.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dineshprabha.instagramclone.adapters.MyPostAdapter
import com.dineshprabha.instagramclone.adapters.MyReelAdapter
import com.dineshprabha.instagramclone.databinding.FragmentMyReelBinding
import com.dineshprabha.instagramclone.model.Post
import com.dineshprabha.instagramclone.model.Reel
import com.dineshprabha.instagramclone.utils.REEL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MyReelFragment : Fragment() {

    private lateinit var binding: FragmentMyReelBinding
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var reelList = ArrayList<Reel>()
        var adapter = MyReelAdapter(requireActivity(), reelList)
        binding.rvMyReels.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMyReels.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get().addOnSuccessListener {
            var tempList = arrayListOf<Reel>()
            for (i in it.documents){
                var  reel: Reel = i.toObject<Reel>()!!
                tempList.add(reel)
                Log.i("FireStore1", gson.toJson(tempList))
            }

            reelList.addAll(tempList)
            Log.i("FireStore2", gson.toJson(reelList))
            adapter.notifyDataSetChanged()

        }
    }
}