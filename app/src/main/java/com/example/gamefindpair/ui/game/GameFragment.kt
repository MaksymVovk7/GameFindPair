package com.example.gamefindpair.ui.game

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gamefindpair.data.AppDatabase
import com.example.gamefindpair.data.model.GameResult
import com.example.gamefindpair.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    @Inject lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = GameFragmentArgs.fromBundle(requireArguments())
        val cardCount = args.cardCount

        val factory = GameViewModelFactory(cardCount)
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]

        binding.recyclerView.layoutManager = GridLayoutManager(context, if (cardCount == 10) 4 else 5)
        adapter = CardAdapter(emptyList()) { card -> viewModel.onCardClicked(card) }
        binding.recyclerView.adapter = adapter

        viewModel.cards.observe(viewLifecycleOwner) { adapter.updateCards(it) }
        viewModel.time.observe(viewLifecycleOwner) { binding.timerTextView.text = "Час: ${it / 1000}s" }
        viewModel.gameFinished.observe(viewLifecycleOwner) {
            if (it) showGameOverDialog()
        }

    }

    private fun showGameOverDialog() {
        val time = viewModel.getElapsedTime()
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = formatter.format(Date())

        val result = GameResult(0,time, viewModel.cards.value?.size ?: 0, date)
        lifecycleScope.launch {
            appDatabase.gameResultDao().insert(result)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Гру завершено!")
            .setMessage("Час: ${time / 1000}с\nГрати ще?")
            .setPositiveButton("Так") { _, _ -> viewModel.resetGame() }
            .setNegativeButton("Меню") {
                    _, _ -> findNavController().navigate(GameFragmentDirections.actionGameToMenu())
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}