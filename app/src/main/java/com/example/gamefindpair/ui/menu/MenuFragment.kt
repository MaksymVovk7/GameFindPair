package com.example.gamefindpair.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gamefindpair.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonMode10.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuToGame(10)
            )
        }

        binding.buttonMode20.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuToGame(20)
            )
        }

        binding.buttonLeaderboard.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuToLeaderboard()
            )
        }

        binding.buttonExit.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}