package com.usedcars.usedcars.controller;

import com.usedcars.usedcars.auth.AuthenticationService;
import com.usedcars.usedcars.service.SavedSearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/savedSearch")
@AllArgsConstructor
public class SavedSearchController {
    private final SavedSearchService savedSearchService;
    private final AuthenticationService authenticationService;

    @GetMapping
    List<String> getLast10Searches(HttpServletRequest httpServletRequest) throws IOException {
        Long userId = authenticationService.getUser(httpServletRequest).getId();
        return savedSearchService.getLast10Searches(userId);
    }

    @PostMapping
    void addSavedSearch(HttpServletRequest httpServletRequest,@RequestBody String savedSearch) throws IOException {
        Long userId = authenticationService.getUser(httpServletRequest).getId();
        savedSearch = savedSearch.replace("%5B","?");
        savedSearch = savedSearch.replace("%5D","&");
        savedSearch = savedSearch.replace("%28","(");
        savedSearch = savedSearch.replace("%29",")");

//        savedSearch = savedSearch.substring(12);
        savedSearchService.addSavedSearch(userId,savedSearch);
    }
}
