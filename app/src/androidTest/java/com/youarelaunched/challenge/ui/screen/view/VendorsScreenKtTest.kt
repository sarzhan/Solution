package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.youarelaunched.challenge.MainActivity
import org.junit.Rule
import org.junit.Test

class VendorsScreenKtTest{

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()


    @Test
    fun makeSearch_loadVendors(): Unit = with(rule){

        onRoot().printToLog("Ui test")
        onNodeWithText("Enter search query…")
            .performTextInput("")

        waitUntil(timeoutMillis = 3000) {
            onAllNodesWithTag("VendorItem").fetchSemanticsNodes().isNotEmpty()
        }

        onNodeWithText("Florists + Fashion")
            .assertIsDisplayed()
    }

    @Test
    fun makeBadSearch_messageDisplayed(): Unit = with(rule){

        onNodeWithText("Enter search query…")
            .performTextInput("testtest")

        waitUntil(timeoutMillis = 3000) {
            onAllNodesWithText("Sorry! No results found…").fetchSemanticsNodes().isNotEmpty()
        }
    }
}