package dev.bulean.notwallet.ui.commons

import android.content.Context
import dev.bulean.notwallet.App

val Context.app: App
    get() = applicationContext as App