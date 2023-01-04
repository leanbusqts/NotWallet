package dev.bulean.notwallet.ui.detail_quote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.bulean.notwallet.R
import dev.bulean.notwallet.databinding.FragmentDetailQuoteBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailQuoteFragment : Fragment(R.layout.fragment_detail_quote) {

    private val safeArgs: DetailQuoteFragmentArgs by navArgs()

    private val viewModel: DetailQuoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailQuoteBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.quote != null) {
                        binding.quote = state.quote
                    }
                }
            }
        }
    }
}
