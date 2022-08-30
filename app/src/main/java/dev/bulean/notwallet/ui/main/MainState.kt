package dev.bulean.notwallet.ui.main

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dev.bulean.notwallet.data.model.QuoteResult

fun Fragment.buildMainState(
    navController: NavController = findNavController()
) = MainState(navController)

class MainState(private val navController: NavController) {

    fun onQuoteClicked(quote: QuoteResult) {
        val action = MainFragmentDirections.actionMainToDetailQuote(quote)
        navController.navigate(action)
    }
}