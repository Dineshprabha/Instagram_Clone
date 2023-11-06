package com.dineshprabha.instagramclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dineshprabha.instagramclone.adapters.SearchAdapter
import com.dineshprabha.instagramclone.databinding.FragmentSearchBinding
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var adapter:SearchAdapter
    var userList = ArrayList<User>()


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
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearch.layoutManager=LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), userList)
        binding.rvSearch.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var temmList = ArrayList<User>()
            userList.clear()
            for (i in it.documents){
                if (!i.id.toString().equals(Firebase.auth.currentUser?.uid.toString())) {
                    var user: User = i.toObject<User>()!!
                    temmList.add(user)
                }
            }
            userList.addAll(temmList)
            adapter.notifyDataSetChanged()

        }

        binding.searchView.setOnClickListener {

            var text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get()
                .addOnSuccessListener {

                    var temmList = ArrayList<User>()
                    userList.clear()
                    if (!it.isEmpty){
                        for (i in it.documents){
                            if (!i.id.toString().equals(Firebase.auth.currentUser?.uid.toString())) {
                                var user: User = i.toObject<User>()!!
                                temmList.add(user)
                            }
                        }
                    }
                    userList.addAll(temmList)
                    adapter.notifyDataSetChanged()

                }
        }


    }

}