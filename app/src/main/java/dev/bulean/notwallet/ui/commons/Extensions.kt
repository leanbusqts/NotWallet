package dev.bulean.notwallet.ui.commons

import android.content.Context
import androidx.fragment.app.Fragment
import dev.bulean.notwallet.App

val Context.app: App get() = applicationContext as App

val Fragment.app: App get() = requireContext().app
