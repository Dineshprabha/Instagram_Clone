package com.dineshprabha.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dineshprabha.instagramclone.LoginActivity
import com.dineshprabha.instagramclone.RegisterActivity
import com.dineshprabha.instagramclone.adapters.ViewPagerAdapter
import com.dineshprabha.instagramclone.databinding.FragmentProfileBinding
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfileIcon.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.putExtra( "MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(),"Posts")
        viewPagerAdapter.addFragments(MyReelFragment(),"Reels")
        binding.profileViewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.profileViewPager)

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.startActivity(Intent(requireActivity(), LoginActivity ::class.java))
            activity?.finish()
        }
    }


    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User = it.toObject<User>()!!
                binding.txtName.text=user.name
                binding.txtBio.text=user.email

                if (!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.imgUserPhoto)
                }
            }

    }
}