package com.dineshprabha.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.adapters.ReelAdapter
import com.dineshprabha.instagramclone.databinding.FragmentReelBinding
import com.dineshprabha.instagramclone.model.Reel
import com.dineshprabha.instagramclone.utils.REEL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReelFragment : Fragment() {

    private lateinit var binding: FragmentReelBinding
    lateinit var adapter:ReelAdapter
    var reelList=ArrayList<Reel>()

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
        binding = FragmentReelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReelAdapter(requireContext(), reelList)
        binding.reelViewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList = ArrayList<Reel>()
            reelList.clear()
            for (i in it.documents){
                var reel = i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
//            reelList.reverse()
            adapter.notifyDataSetChanged()
        }

    }
}