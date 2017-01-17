package com.example.chiaraercolani.treasurehunt;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Vincent RICHAUD on 08/11/2016.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
//    {
//        super.onPreferenceTreeClick(preferenceScreen, preference);
//        if (preference!=null)
//            if (preference instanceof PreferenceScreen)
//                if (((PreferenceScreen)preference).getDialog()!=null)
//                    ((PreferenceScreen)preference).getDialog().getWindow().getDecorView().setBackgroundDrawable(this.getWindow().getDecorView().getBackground().getConstantState().newDrawable());
//        return false;
//    }
}
