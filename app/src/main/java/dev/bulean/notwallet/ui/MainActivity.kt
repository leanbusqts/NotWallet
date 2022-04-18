package dev.bulean.notwallet.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.bulean.notwallet.data.model.QuoteResult
import dev.bulean.notwallet.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: QuotesAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAction.setOnClickListener { viewModel.onAction() }

        initRecyclerView()

        viewModel.listItem.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        adapter = QuotesAdapter { item ->
            navigateToDetail(item)
        }
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        binding.recycler.adapter = adapter
    }

    private fun navigateToDetail(item: QuoteResult) {
        Snackbar.make(binding.root, item.shortName, Snackbar.LENGTH_SHORT)
    }
}