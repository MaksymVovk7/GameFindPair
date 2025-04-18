package com.example.gamefindpair.ui.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamefindpair.databinding.FragmentLeaderboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding: FragmentLeaderboardBinding
        get() = _binding ?: throw RuntimeException("FragmentLeaderboardBinding == null")

    private val viewModel: LeaderboardViewModel by viewModels()
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LeaderboardAdapter(emptyList())
        binding.leaderboardList.layoutManager = LinearLayoutManager(context)
        binding.leaderboardList.adapter = adapter

        viewModel.results.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}