package com.usedcars.usedcars.service;

import java.util.List;

public interface SavedSearchService {
    void addSavedSearch(Long userId, String savedSearch);
    List<String> getLast10Searches(Long userId);
}
