package com.dineshprabha.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dineshprabha.instagramclone.adapters.MyPostAdapter
import com.dineshprabha.instagramclone.databinding.FragmentMyPostBinding
import com.dineshprabha.instagramclone.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class MyPostFragment : Fragment() {
    private lateinit var binding: FragmentMyPostBinding
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
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var postList = ArrayList<Post>()
        var adapter = MyPostAdapter(requireActivity(), postList)
        binding.rvMyPosts.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvMyPosts.adapter = adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            var tempList = arrayListOf<Post>()
            for (i in it.documents){
                var  post:Post = i.toObject<Post>()!!
                tempList.add(post)
                Log.i("FireStore1", gson.toJson(tempList))
            }

            postList.addAll(tempList)
            Log.i("FireStore2", gson.toJson(postList))
            adapter.notifyDataSetChanged()

        }

    }

}