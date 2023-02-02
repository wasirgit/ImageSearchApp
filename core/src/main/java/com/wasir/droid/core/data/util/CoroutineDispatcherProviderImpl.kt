package com.wasir.droid.core.data.util


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatcherProviderImpl : DispatchersProvider {
    override fun getMain(): CoroutineDispatcher = Dispatchers.Main
    override fun getIO(): CoroutineDispatcher = Dispatchers.IO
    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default
}