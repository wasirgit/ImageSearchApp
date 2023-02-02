package com.wasir.droid.paybackcodingchallenge.presentation.ui.search

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.filters.MediumTest
import com.wasir.droid.paybackcodingchallenge.FragmentFactory
import com.wasir.droid.paybackcodingchallenge.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageSearchFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun navigateAndDisplayImageDetailsFragment() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ImageSearchFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun search_with_typing() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ImageSearchFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
        typeSearchViewText("fruits")
    }


    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Type something"
            }

            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, true)
                uiController?.loopMainThreadForAtLeast(5000)
                uiController?.loopMainThreadUntilIdle()
            }
        }
    }
}