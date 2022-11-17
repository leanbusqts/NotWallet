package dev.bulean.notwallet.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dev.bulean.notwallet.R
import dev.bulean.notwallet.model.database.Quote
import dev.bulean.notwallet.model.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController()
) = MainState(context, scope, navController)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController
    ) {

    fun onQuoteClicked(quote: Quote) {
        val action = MainFragmentDirections.actionMainToDetailQuote(quote.shortName)
        navController.navigate(action)
    }

    fun request(value: (Boolean) -> Unit) {
        scope.launch {
            val result = true
            value(result)
        }
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}