package com.dineshprabha.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.adapters.FollowAdapter
import com.dineshprabha.instagramclone.adapters.PostAdapter
import com.dineshprabha.instagramclone.databinding.FragmentHomeBinding
import com.dineshprabha.instagramclone.model.Post
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.FOLLOW
import com.dineshprabha.instagramclone.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter:PostAdapter

    private var followList = ArrayList<User>()
    private lateinit var followAdapter : FollowAdapter

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PostAdapter(requireContext(), postList)
        binding.rvHomePosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomePosts.adapter = adapter

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var  tempList = ArrayList<Post>()
            postList.clear()
            for (i in it.documents){
                var post:Post = i.toObject<Post>()!!
                tempList.add(post)

            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        followAdapter = FollowAdapter(requireContext(), followList)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFollow.adapter = followAdapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get().addOnSuccessListener {
            var  tempList = ArrayList<User>()
            followList.clear()
            for (i in it.documents){
                var user:User = i.toObject<User>()!!
                tempList.add(user)
            }

            followList.addAll(tempList)
            followAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}