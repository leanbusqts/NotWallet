package dev.bulean.notwallet.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.bulean.notwallet.R
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.data.repository.QuoteRepository
import dev.bulean.notwallet.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(QuoteRepository())
    }

    private val adapter = QuotesAdapter { viewModel.onQuoteClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { binding.handleViewState(it) }
            }
        }
    }

    private fun FragmentMainBinding.handleViewState(viewState: MainViewModel.ViewState) {
        progress.visibility = if (viewState.loading) View.VISIBLE else View.GONE
        viewState.quotes?.let(adapter::submitList)
        viewState.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(quote: QuoteResult) {
        val action = MainFragmentDirections.actionMainToDetailQuote(quote)
        findNavController().navigate(action)
    }
}
