package dev.bulean.notwallet.ui.detail_quote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.bulean.notwallet.databinding.FragmentDetailQuoteBinding

class DetailQuoteFragment : Fragment() {

    private var _binding: FragmentDetailQuoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailQuoteBinding.inflate(inflater, container, false)



        return binding.root
    }

}