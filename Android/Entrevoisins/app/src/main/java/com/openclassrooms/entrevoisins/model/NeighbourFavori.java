package com.openclassrooms.entrevoisins.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailActivity.VOISIN;


public class NeighbourFavori {

    //TODO: récupérer la liste des favoris

    public static List<Neighbour> getNeighbourFavori(Context context) {
        List favoris = new ArrayList();

        // TODO: ajouter tous les voisins dans les favoris

        DummyNeighbourApiService Api = new DummyNeighbourApiService();
        for (Neighbour neighbour : Api.getNeighbours()){
            if (isFavori(neighbour.getId(), context)) {
                favoris.add(neighbour);
            }
        }
        return favoris;
    }

    public static boolean isFavori(long id, Context context) {
        SharedPreferences Preferences;
        Preferences = context.getSharedPreferences(VOISIN, MODE_PRIVATE);
        boolean isfavori = Preferences.getBoolean("" + id, false);
        return isfavori;
    }

}
