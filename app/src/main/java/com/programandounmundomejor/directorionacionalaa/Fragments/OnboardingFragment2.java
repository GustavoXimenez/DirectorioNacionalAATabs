package com.programandounmundomejor.directorionacionalaa.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programandounmundomejor.directorionacionalaa.R;

public class OnboardingFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {

        return inflater.inflate(
                R.layout.fragment_onboarding_screen2,
                container,
                false
        );

    }
}
