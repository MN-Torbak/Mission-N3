package com.openclassrooms.entrevoisins.ui.neighbour_list;

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

    public static boolean isFavori(Neighbour voisin, List<Neighbour> voisinsFavoris) {
        return voisinsFavoris.contains(voisin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);

        Preferences = getSharedPreferences(VOISIN, MODE_PRIVATE);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            monVoisinID = b.getLong("id");
            monVoisinName = b.getString("name");
            monVoisinAvatarUrl = b.getString("avatarUrl");
            monVoisinAdress = b.getString("adress");
            monVoisinPhoneNumber = b.getString("phoneNumber");
            monVoisinAboutMe = b.getString("aboutMe");
        }

        monVoisin = new Neighbour(monVoisinID, monVoisinName, monVoisinAvatarUrl, monVoisinAdress, monVoisinPhoneNumber, monVoisinAboutMe);

        isfavori = isFavori(monVoisin, NeighbourFavori.getNeighbourFavori(this));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (isfavori) {
            fab.setImageResource(R.drawable.ic_star_white_24dp);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(monVoisinName);
        setSupportActionBar(toolbar);

        ImageView avatarimageview = findViewById(R.id.imageviewdetails);

        Glide.with(this)
                .load(monVoisinAvatarUrl)
                .centerCrop()
                .into(avatarimageview);

        name = findViewById(R.id.name);
        name.setText(monVoisinName);

        adress = findViewById(R.id.adress);
        adress.setText(monVoisinAdress);

        phone = findViewById(R.id.textphone);
        phone.setText(monVoisinPhoneNumber);

        aboutMe = findViewById(R.id.aboutMe);
        aboutMe.setText(monVoisinAboutMe);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isfavori) {
                    isfavori = false;
                    fab.setImageResource(R.drawable.ic_star_border_white_24dp);
                    Preferences.edit().putBoolean("" + monVoisinID, false).apply();
                } else {
                    isfavori = true;
                    fab.setImageResource(R.drawable.ic_star_white_24dp);
                    Preferences.edit().putBoolean("" + monVoisinID, true).apply();
                }
            }
        });
    }
}
