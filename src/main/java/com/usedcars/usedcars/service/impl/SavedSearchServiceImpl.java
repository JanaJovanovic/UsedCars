package com.usedcars.usedcars.service.impl;

import com.usedcars.usedcars.repository.SavedSearchRepository;
import com.usedcars.usedcars.service.SavedSearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SavedSearchServiceImpl implements SavedSearchService {

    private final SavedSearchRepository savedSearchRepository;
    @Override
    @Transactional
    public void addSavedSearch(Long userId, String savedSearch) {
        savedSearchRepository.addSavedSearch(userId, savedSearch);
    }

    @Override
    public List<String> getLast10Searches(Long userId) {
        return savedSearchRepository.findTop10ByUserIdOrderByidDesc(userId);
    }

}
