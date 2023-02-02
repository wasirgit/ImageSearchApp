package com.wasir.droid.paybackcodingchallenge.presentation.ui.details

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import coil.request.ImageRequest
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.paybackcodingchallenge.FragmentFactory
import com.wasir.droid.paybackcodingchallenge.R
import com.wasir.droid.paybackcodingchallenge.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageDetailsFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private val imageData = ImageData(
        user = "testUser",
        likes = 100,
        comments = 50,
        downloads = 200,
        tags = "Tags: testTag1, testTag2",
        largeImageURL = "https://www.gstatic.com/webp/gallery/1.jpg"
    )

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun navigateAndDisplayImageDetailsFragment() {
        val navController = mock(NavController::class.java)
        val args = ImageDetailsFragmentArgs(
            ImageData(
                user = "testUser",
                likes = 100,
                comments = 50,
                downloads = 200,
                tags = "Tags: testTag1, testTag2"
            )
        )
        val bundle = args.toBundle()
        launchFragmentInHiltContainer<ImageDetailsFragment>(
            bundle,
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun validateUiOnDetailsScreen() {
        addFragment()
        onView(withId(R.id.tvName)).check(matches(withText("Name: testUser")))
        onView(withId(R.id.tvComments)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDownloads)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTags)).check(matches(isDisplayed()))
        onView(withId(R.id.ivImage)).check(matches(isDisplayed()))
    }

    @Test
    fun imageLoadingSuccessfully() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val imageLoader = mock(ImageLoader::class.java)
        addFragment()
        // When
        val request = ImageRequest.Builder(context)
            .data(imageData.largeImageURL)
            .crossfade(true)
            .target(
                onStart = {
                    onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
                },
                onSuccess = {
                    onView(withId(R.id.ivImage)).check(matches(isDisplayed()))
                    onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
                },
                onError = {
                    onView(withId(R.id.ivImage)).check(matches(isDisplayed()))
                    onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
                }
            )
            .build()
        imageLoader.enqueue(request)
    }

    @Test
    fun imageLoadingError() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val imageLoader = mock(ImageLoader::class.java)
        imageData.largeImageURL = ""
        addFragment()
        // When
        val request = ImageRequest.Builder(context)
            .data(imageData.largeImageURL)
            .crossfade(true)
            .target(
                onStart = {
                    onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
                },
                onSuccess = {
                    onView(withId(R.id.ivImage)).check(matches(isDisplayed()))
                    onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
                },
                onError = {
                    onView(withId(R.id.ivImage)).check(matches(isDisplayed()))
                    onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
                }
            )
            .build()
        imageLoader.enqueue(request)
    }


    private fun addFragment() {
        val navController = mock(NavController::class.java)
        val args = ImageDetailsFragmentArgs(
            imageData
        )
        val bundle = args.toBundle()
        launchFragmentInHiltContainer<ImageDetailsFragment>(
            bundle,
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
    }
}