package com.example.detailslist.characters.data.cashe;

class BadgeCache(
    private var hasActiveFilters: Boolean = false
) {
    fun setFiltersActivity(isActive: Boolean) {
        hasActiveFilters = isActive
    }

    fun hasActiveFilters(): Boolean = hasActiveFilters
}
