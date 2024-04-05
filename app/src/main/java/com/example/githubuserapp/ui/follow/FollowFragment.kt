package com.example.githubuserapp.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.UserFollowResponseItem
import com.example.githubuserapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var _binding: FragmentFollowBinding
    private val binding get() = _binding
    private var position = 0
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        val followViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]
        followViewModel.user.observe(viewLifecycleOwner) {
            setUserData(it)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (position == 1) {
            followViewModel.showFollowers(username)
        } else {
            followViewModel.showFollowing(username)
        }
    }

    private fun setUserData(items: List<UserFollowResponseItem>?) {
        val adapter = FollowAdapter()
        adapter.submitList(items)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"

        const val TAG = "FollowFragment"
    }

}