package com.HealthCareSocialAppServer.service;


import com.HealthCareSocialAppServer.model.Preferences;

import java.util.List;

public interface PreferencesService {

    void addNewPreferences(Preferences preferences);

    void updatePreferences(Preferences preferences);

    void deletePreferences(Preferences preferences);

    Preferences findById(Long id);

    List<Preferences> getAllPreferences();

    Preferences findFirstByUserId(Long id);
}
