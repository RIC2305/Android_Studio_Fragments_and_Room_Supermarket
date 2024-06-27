package com.example.pr_tarea3iglesiascostasroi.viewmodels;
import androidx.preference.PreferenceFragmentCompat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pr_tarea3iglesiascostasroi.R;


public class PreferenceFragment extends PreferenceFragmentCompat {


 public static final String TAG = "SettingsFragment";
    public PreferenceFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState,String rootKey) {
        addPreferencesFromResource(R.xml.settings);

    }

}