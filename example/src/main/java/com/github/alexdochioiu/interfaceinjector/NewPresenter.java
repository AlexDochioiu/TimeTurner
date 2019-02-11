package com.github.alexdochioiu.interfaceinjector;

import android.arch.lifecycle.Lifecycle;
import android.util.Log;

import com.github.alexdochioiu.timeturner.GlobalSurvivor;
import com.github.alexdochioiu.timeturner.SurvivorBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex Dochioiu on 30/01/2019
 */
class NewPresenter {
    private static final String TAG = "NewPresenter";

    @GlobalSurvivor
    List<String> survivingElements = new ArrayList<>();

    NewPresenter(Lifecycle lifecycle) {
        Log.e(TAG, "beforeBinding: " + survivingElements );

        SurvivorBinding.bind(this, lifecycle);
        Log.e(TAG, "afterBinding: " + survivingElements );

        survivingElements.add(UUID.randomUUID().toString());
        Log.e(TAG, "afterNewEntryBinding: " + survivingElements );
    }
}
