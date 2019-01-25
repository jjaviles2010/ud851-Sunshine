package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
        // Do step 9 within onCreatePreference
        // COMPLETED (9) Set the preference summary on each preference that isn't a CheckBoxPreference
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int countPref = prefScreen.getPreferenceCount();

        for(int i = 0; i<countPref; i++){
            Preference preference = prefScreen.getPreference(i);
            if(!(preference instanceof CheckBoxPreference)){
                String stringValue = sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,stringValue);
            }
        }
    }

    // COMPLETED (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

    private void setPreferenceSummary(Preference preference, Object object){
       String key = preference.getKey();
       String stringValue = object.toString();

       if(preference instanceof ListPreference){
           ListPreference listPreference = (ListPreference) preference;
           int prefIndex = listPreference.findIndexOfValue(stringValue);
           if(prefIndex >= 0){
               preference.setSummary(listPreference.getEntries()[prefIndex]);
           }
       }else {
           preference.setSummary(stringValue);
       }
    }

    // COMPLETED (10) Implement OnSharedPreferenceChangeListener from SettingsFragment
    // COMPLETED (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = getPreferenceScreen().findPreference(key);
        if(preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
            }
        }
    }


    // COMPLETED (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

    // COMPLETED (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart


    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
