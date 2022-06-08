package dev.bulean.notwallet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: QuotesAdapter
    private lateinit var viewModel: MainViewModel

    // ViewFlipper options view
    private object Flipper {
        const val LOADING = 0
        const val CONTENT = 1
        const val ERROR = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, MainViewModelFactory(requireNotNull(this.activity).application))[MainViewModel::class.java]

        initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listItem.collect {
                    adapter.submitList(it)
                }
            }
        }

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
        Snackbar.make(binding.root, item.shortName, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleViewState(viewState: MainViewModel.ViewState) {
        when (viewState) {
            MainViewModel.ViewState(loading = true) -> showStateScreen(Flipper.LOADING)
            MainViewModel.ViewState(quotes = emptyList()) -> showStateScreen(Flipper.CONTENT)
            MainViewModel.ViewState(error = true)-> showStateScreen(Flipper.ERROR)
        }
    }

    private fun showStateScreen(flipperState: Int) {
        binding.vfMain.displayedChild = flipperState
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

