package dev.bulean.notwallet.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.bulean.notwallet.R
import dev.bulean.notwallet.data.repository.QuoteRepository
import dev.bulean.notwallet.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainState: MainState
    private val adapter = QuotesAdapter {
        mainState.onQuoteClicked(it)
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(QuoteRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()

        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.loading = it.loading
                    binding.quotes = it.quotes
                }
            }
        }
    }
}
