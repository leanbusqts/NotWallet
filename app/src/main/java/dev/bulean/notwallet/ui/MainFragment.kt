package dev.bulean.notwallet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: QuotesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, MainViewModelFactory(requireNotNull(this.activity).application))[MainViewModel::class.java]

        initRecyclerView()

        binding.btnAction.setOnClickListener { viewModel.onAction() }

        viewModel.listItem.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

