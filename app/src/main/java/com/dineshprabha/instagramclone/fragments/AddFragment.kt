package com.dineshprabha.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dineshprabha.instagramclone.LoginActivity
import com.dineshprabha.instagramclone.R
import com.dineshprabha.instagramclone.databinding.FragmentAddBinding
import com.dineshprabha.instagramclone.posts.PostActivity
import com.dineshprabha.instagramclone.posts.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding

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
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llAddPost.setOnClickListener {
            activity?.startActivity(Intent(requireActivity(), PostActivity ::class.java))
            activity?.finish()
        }

        binding.llUploadReel.setOnClickListener {
            activity?.startActivity(Intent(requireActivity(), ReelsActivity ::class.java))
            activity?.finish()
        }

    }


}