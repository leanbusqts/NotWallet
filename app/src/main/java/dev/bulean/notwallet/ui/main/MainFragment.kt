package dev.bulean.notwallet.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bulean.notwallet.R
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: QuotesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, MainViewModelFactory(requireNotNull(this.activity).application))[MainViewModel::class.java]

        initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::handleViewState)
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = QuotesAdapter { item ->
            navigateToDetail(item)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this.activity)

        binding.recycler.adapter = adapter
    }

    private fun navigateToDetail(item: QuoteResult) {
        findNavController().navigate(R.id.action_mainFragment_to_detailQuoteFragment)
    }

    private fun handleViewState(viewState: MainViewModel.ViewState) {
        binding.pbMain.visibility = if (viewState.loading) View.VISIBLE else View.GONE
        viewState.quotes?.let(adapter::submitList)
        binding.error.visibility = if (viewState.error) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

