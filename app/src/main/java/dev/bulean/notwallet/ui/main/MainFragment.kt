package dev.bulean.notwallet.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.bulean.notwallet.R
import dev.bulean.notwallet.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainState: MainState
    private val adapter = QuotesAdapter { mainState.onQuoteClicked(it) }
    private val viewModel: MainViewModel by viewModels()

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
                    binding.error = it.error?.let(mainState::errorToString)
                }
            }
        }

        mainState.requestLocationPermission {
            viewModel.onAction()
        }
    }
}
