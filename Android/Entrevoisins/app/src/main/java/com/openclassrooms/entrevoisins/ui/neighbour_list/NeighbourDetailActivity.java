package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.model.NeighbourFavori;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;

import java.util.ArrayList;
import java.util.List;

public class NeighbourDetailActivity extends AppCompatActivity {

    Neighbour monVoisin;
    Long monVoisinID;
    String monVoisinName;
    String monVoisinAvatarUrl;
    String monVoisinAdress;
    String monVoisinPhoneNumber;
    String monVoisinAboutMe;

    TextView name;
    TextView adress;
    TextView phone;
    TextView aboutMe;
    boolean isfavori = false;

    private SharedPreferences Preferences;
    public static final String VOISIN = "voisin";

    public static boolean isFavori(long id, SharedPreferences sharedPreferences) {
        boolean isfavori = sharedPreferences.getBoolean("" + id, false);
        return isfavori;
    }

    public static Neighbour getNeighbourFromBundle(Bundle b) {
        Neighbour neighbour = new Neighbour(b.getLong("id"), b.getString("name"), b.getString("avatarUrl"), b.getString("adress"), b.getString("phoneNumber"), b.getString("aboutMe"));
        return neighbour;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);

        Preferences = getSharedPreferences(VOISIN, MODE_PRIVATE);

        Bundle b = getIntent().getExtras();
        monVoisin = getNeighbourFromBundle(b);

        isfavori = isFavori(monVoisin.getId(), Preferences);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (isfavori) {
            fab.setImageResource(R.drawable.ic_star_white_24dp);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(monVoisin.getName());
        setSupportActionBar(toolbar);

        ImageView avatarimageview = findViewById(R.id.imageviewdetails);

        Glide.with(this)
                .load(monVoisinAvatarUrl)
                .centerCrop()
                .into(avatarimageview);

        name = findViewById(R.id.name);
        name.setText(monVoisin.getName());

        adress = findViewById(R.id.adress);
        adress.setText(monVoisin.getAddress());

        phone = findViewById(R.id.textphone);
        phone.setText(monVoisin.getPhoneNumber());

        aboutMe = findViewById(R.id.aboutMe);
        aboutMe.setText(monVoisin.getAboutMe());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isfavori) {
                    isfavori = false;
                    fab.setImageResource(R.drawable.ic_star_border_white_24dp);
                    Preferences.edit().putBoolean("" + monVoisin.getId(), false).apply();
                } else {
                    isfavori = true;
                    fab.setImageResource(R.drawable.ic_star_white_24dp);
                    Preferences.edit().putBoolean("" + monVoisin.getId(), true).apply();
                }
            }
        });
    }
}
