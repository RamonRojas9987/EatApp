package com.bedu.eatapp

import com.bedu.eatapp.entities.Category
import com.bedu.eatapp.interfaces.GetDataService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Call

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class SplashActivityTest{
    @Mock
    private lateinit var mockService: GetDataService

    @Mock
    private lateinit var mockCall: Call<Category>

    private lateinit var splashActivity: SplashActivity

    @Before
    fun setup(){
        splashActivity = SplashActivity()
    }

    @Test
    fun testGetCategories(){
        val activity = Robolectric.buildActivity(SplashActivity::class.java).create().get()

    }
}