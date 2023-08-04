package com.youarelaunched.challenge.testutils

import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule


open class ViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val mockkRule = MockKRule(this)

}