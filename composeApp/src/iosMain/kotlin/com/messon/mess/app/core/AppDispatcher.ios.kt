package com.messon.mess.app.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class AppDispatchers {
    actual val main: CoroutineDispatcher = Dispatchers.Main
    actual val io: CoroutineDispatcher = Dispatchers.Default
    actual val default: CoroutineDispatcher = Dispatchers.Default
}