package com.nendo.argosy.ui.components

// Way One Compatible - ONE change only: Alphabetical A-Z support
// Owner david.elkins.71
object AbcCarouselHelper {
    fun <T> sortAZ(list: List<T>, getName: (T) -> String): List<T> {
        return list.sortedBy { getName(it).lowercase() }
    }
    
    fun <T> findIndexForLetter(list: List<T>, letter: Char, getName: (T) -> String): Int {
        return list.indexOfFirst { 
            getName(it).firstOrNull()?.uppercaseChar() == letter.uppercaseChar() 
        }.coerceAtLeast(0)
    }
}
