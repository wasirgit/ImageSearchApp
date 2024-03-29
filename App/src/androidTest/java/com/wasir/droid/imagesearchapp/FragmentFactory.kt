package com.wasir.droid.imagesearchapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.wasir.droid.imagesearchapp.presentation.ui.details.ImageDetailsFragment
import com.wasir.droid.imagesearchapp.presentation.ui.search.ImageSearchFragment
import javax.inject.Inject

class FragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImageSearchFragment::class.java.name -> ImageSearchFragment()
            ImageDetailsFragment::class.java.name -> ImageDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}