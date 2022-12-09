package dev.bulean.notwallet.ui.detail_quote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import dev.bulean.notwallet.R
import dev.bulean.notwallet.databinding.FragmentDetailQuoteBinding
import dev.bulean.notwallet.data.QuoteRepository
import dev.bulean.notwallet.framework.database.QuoteRoomDataSource
import dev.bulean.notwallet.framework.server.QuoteServerDataSource
import dev.bulean.notwallet.ui.commons.app
import dev.bulean.notwallet.usecases.FindQuoteByShortnameUseCase
import kotlinx.coroutines.launch

class DetailQuoteFragment : Fragment(R.layout.fragment_detail_quote) {

    private val safeArgs: DetailQuoteFragmentArgs by navArgs()

    private val viewModel: DetailQuoteViewModel by viewModels {
        val localDataSource = QuoteRoomDataSource(requireActivity().app.database.quoteDao())
        val remoteDataSource = QuoteServerDataSource()
        val repository = QuoteRepository(
            localDataSource,
            remoteDataSource
        )
        DetailQuoteViewModelFactory(
            safeArgs.quoteName,
            FindQuoteByShortnameUseCase(repository)
        )
    }

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
