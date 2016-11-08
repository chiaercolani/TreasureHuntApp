package com.example.chiaraercolani.treasurehunt;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Vincent RICHAUD on 08/11/2016.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
