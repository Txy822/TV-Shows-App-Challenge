package com.tes.android.projects.tvshowsapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tes.android.projects.tvshowsapp.ui.MainActivity
import com.tes.android.projects.tvshowsapp.ui.TvShows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowEndToEndTest {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp(){
        composeRule.activity.setContent {
                TvShows()
        }
    }
    @Test
    fun homeShowListingScreenTest(){
        composeRule.onNodeWithText("Home", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Home").performClick()
        composeRule.onNode(hasText("TV Maze ShowsEvent"))
        composeRule.onNode(hasText("List of Shows"))
        composeRule.onNode(hasText("Top Rated Shows"))
        composeRule.onNode(hasText("More"))
    }

    @Test
    fun moreSectionToListScreenTest(){
        composeRule.onAllNodes(isRoot())[54]
        composeRule.onNodeWithText("Home", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Home").performClick()
        composeRule.onAllNodesWithText("More", useUnmergedTree = true)
            .assertCountEquals(2)
        composeRule.onAllNodesWithText("More", useUnmergedTree = true)[0].performClick()
        composeRule.onNode(hasText("List of Shows"))
    }

    @Test
    fun favoriteShowScreenTest(){
        composeRule.onNodeWithText("Favorite", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Favorite").performClick()
        composeRule.onNode(hasText("Favorite Tv ShowsEvent"))
    }
    @Test
    fun showSearchScreenTest(){
        composeRule.onNodeWithText("Search", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Search").performClick()
        composeRule.onNode(hasText("TV Maze ShowsEvent"))
    }

    @Test
    fun settingScreenTest(){
        composeRule.onNodeWithText("Setting", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Setting").performClick()
        composeRule.onNode(hasText("Setting"))
        composeRule.onNodeWithText("About the App", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Version 1.0", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun checkSearchInputTest() {
        composeRule.onNodeWithText("Search", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("Search").performClick()
        composeRule.onNodeWithText("Search...")
            .performTextInput("Bitten")
    }
}