package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class NeighbourFavoriteTest {
    private static final String PREFS_NAME = "prefs";
    private static final String KEY_PREF = "KEY_PREF";
    private SharedPreferences sharedPreferences;

    @Before
    public void before() {
        Context context = InstrumentationRegistry.getTargetContext();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Test
    public void put_and_get_preference() throws Exception {
        //WHEN
        long carolineId = 12L;
        sharedPreferences.edit().putBoolean(carolineId+"", true).apply();

        //THEN
        List<Neighbour> favoriteNeighbours = NeighbourFavori.getNeigbourFavori(context);

        //THEN  Verify that the received data is correct.
        boolean containsCarolineId = false;
        for(Neighbour nb : favoriteNeighbours){
            if(nb.getId()==carolineId) {
                containsCarolineId = true;
            }
        }
        assertTrue(containsCarolineId);
    }

    @After
    public void after() {
        sharedPreferences.edit().putString(KEY_PREF, null).apply();
    }
}