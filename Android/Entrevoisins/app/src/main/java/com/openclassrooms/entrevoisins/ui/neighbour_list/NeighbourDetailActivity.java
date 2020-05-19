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

public class NeighbourDetailActivity extends AppCompatActivity {

    Neighbour monVoisin;

    TextView nameTextView;
    TextView adressTextView;
    TextView phoneTextView;
    TextView aboutMeTextView;
    ImageView avatarimageview;
    boolean isfavori = false;

    private SharedPreferences Preferences;
    public static final String VOISIN = "voisin";

    public static boolean isFavori(long id, SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean("" + id, false);
    }

    public static Neighbour getNeighbourFromBundle(Bundle monbundle) {
        return new Neighbour(monbundle.getLong("id"), monbundle.getString("name"), monbundle.getString("avatarUrl"), monbundle.getString("adress"), monbundle.getString("phoneNumber"), monbundle.getString("aboutMe"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);

        Preferences = getSharedPreferences(VOISIN, MODE_PRIVATE);
        if (getIntent().getExtras() == null) {
            finish();
        } else {
            monVoisin = getNeighbourFromBundle(getIntent().getExtras());
        }
        isfavori = isFavori(monVoisin.getId(), Preferences);
        avatarimageview = findViewById(R.id.imageviewdetails);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (isfavori) {
            fab.setImageResource(R.drawable.ic_star_white_24dp);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(monVoisin.getName());
        setSupportActionBar(toolbar);

        Glide.with(this)
                .load(monVoisin.getAvatarUrl())
                .centerCrop()
                .into(avatarimageview);

        nameTextView = findViewById(R.id.name);
        nameTextView.setText(monVoisin.getName());

        adressTextView = findViewById(R.id.adress);
        adressTextView.setText(monVoisin.getAddress());

        phoneTextView = findViewById(R.id.textphone);
        phoneTextView.setText(monVoisin.getPhoneNumber());

        aboutMeTextView = findViewById(R.id.aboutMe);
        aboutMeTextView.setText(monVoisin.getAboutMe());

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
