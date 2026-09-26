package com.nendo.argosy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Argosy-Launcher-ABC - Way One Compatible
 * ONE change only: Carousel Alphabetical A-Z support
 * This file is additive and does NOT modify upstream files,
 * so GitHub's Sync Fork button will keep working.
 *
 * Owner: david.elkins.71
 * Repo: github.com/david.elkins.71/Argosy-Launcher-ABC
 */
object AbcCarouselHelper {
    // The A-Z list for the carousel strip
    val ALPHABET = listOf("#") + ('A'..'Z').map { it.toString() }

    /**
     * Sort any list of games/items A-Z by name.
     * Use this in HomeViewModel / HomeScreen later in Step 3
     */
    fun <T> sortAZ(items: List<T>, nameSelector: (T) -> String): List<T> {
        return items.sortedBy { nameSelector(it).lowercase() }
    }

    /**
     * Find the first index for a letter.
     * Returns 0 for # or A, or the index of first item starting with that letter.
     */
    fun <T> findIndexForLetter(
        sortedItems: List<T>,
        letter: String,
        nameSelector: (T) -> String
    ): Int {
        if (letter == "#") return 0
        val target = letter.first()
        val index = sortedItems.indexOfFirst { 
            nameSelector(it).trim().firstOrNull()?.uppercaseChar() == target 
        }
        return if (index == -1) 0 else index
    }
}

@Composable
fun AbcCarouselStrip(
    selectedLetter: String,
    onLetterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val letters = remember { AbcCarouselHelper.ALPHABET }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(letters) { letter ->
            val isSelected = letter == selectedLetter
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                    .clickable { onLetterSelected(letter) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = letter,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Extension to quickly wire into existing Carousel LazyRow state
// In Step 3 you will call: val abcIndex = AbcCarouselHelper.findIndexForLetter(games, letter) { it.name }
// and then lazyListState.animateScrollToItem(abcIndex)
