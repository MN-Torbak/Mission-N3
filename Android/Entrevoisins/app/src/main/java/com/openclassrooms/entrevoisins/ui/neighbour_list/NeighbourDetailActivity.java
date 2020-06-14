package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NeighbourDetailActivity extends AppCompatActivity {

    Neighbour monVoisin;

    @BindView(R.id.name)
    TextView nameTextView;
    @BindView(R.id.adress)
    TextView adressTextView;
    @BindView(R.id.textphone)
    TextView phoneTextView;
    @BindView(R.id.aboutMe)
    TextView aboutMeTextView;
    @BindView(R.id.imageviewdetails)
    ImageView avatarImageView;
    @BindView((R.id.toolbar))
    Toolbar titleToolbar;
    @BindView(R.id.fab)
    FloatingActionButton favoriteFab;

    boolean isFavorite = false;

    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        if (getIntent().getExtras() == null) {
            finish();
        } else {
            monVoisin = getIntent().getExtras().getParcelable("neighbour");
        }

        isFavorite = monVoisin.isFavorite();
        if (isFavorite) {
            favoriteFab.setImageResource(R.drawable.ic_star_white_24dp);
        }

        titleToolbar.setTitle(monVoisin.getName());
        setSupportActionBar(titleToolbar);

        Glide.with(this)
                .load(monVoisin.getAvatarUrl())
                .centerCrop()
                .into(avatarImageView);

        nameTextView.setText(monVoisin.getName());
        adressTextView.setText(monVoisin.getAddress());
        phoneTextView.setText(monVoisin.getPhoneNumber());
        aboutMeTextView.setText(monVoisin.getAboutMe());
    }

    @OnClick(R.id.fab)
    public void submit(View view) {
        if (isFavorite) {
            isFavorite = false;
            favoriteFab.setImageResource(R.drawable.ic_star_border_white_24dp);
            //setFavoriteInService(false, monVoisin, mApiService.getNeighbours());
            mApiService.setFavoriteInService(false, monVoisin);
        } else {
            isFavorite = true;
            favoriteFab.setImageResource(R.drawable.ic_star_white_24dp);
            //setFavoriteInService(true, monVoisin, mApiService.getNeighbours());
            mApiService.setFavoriteInService(true, monVoisin);
        }
    }
    //public static void setFavoriteInService(boolean isFavorite, Neighbour neighbour, List<Neighbour> neighbours) {
    //    for (Neighbour listNeighbour : neighbours) {
    //        if (neighbour.getId() == listNeighbour.getId()) {
    //            listNeighbour.setFavorite(isFavorite);
    //        }
    //    }
    //}
}
