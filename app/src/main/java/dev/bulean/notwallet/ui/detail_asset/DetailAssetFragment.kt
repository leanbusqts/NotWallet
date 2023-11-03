package dev.bulean.notwallet.ui.detail_asset

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
import dev.bulean.notwallet.databinding.FragmentDetailAssetBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailAssetFragment : Fragment(R.layout.fragment_detail_asset) {

    private val safeArgs: DetailAssetFragmentArgs by navArgs()

    private val viewModel: DetailAssetViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailAssetBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.asset != null) {
                        binding.asset = state.asset
                    }
                }
            }
        }
    }
}
