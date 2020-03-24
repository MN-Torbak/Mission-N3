package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.UtilisateurActivity.VOISIN;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private SharedPreferences preferences;
    private boolean onestdanslefragmentfavori;


    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(boolean fragmentFavori) {
        Bundle b = new Bundle();
        b.putBoolean("FragmentFavori", fragmentFavori);
        NeighbourFragment fragment = new NeighbourFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onestdanslefragmentfavori = getArguments().getBoolean("FragmentFavori");
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(onestdanslefragmentfavori){
            view = inflater.inflate(R.layout.fragment_favorite_neighbour_list, container, false);
        }else{
            view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        }
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        mNeighbours = mApiService.getNeighbours();
        List<Neighbour> voisinFavoris = new ArrayList<>();
        preferences = getActivity().getSharedPreferences(VOISIN, MODE_PRIVATE);
        for (Neighbour voisinListé : mNeighbours) {
            boolean isfavori = false;
            isfavori = preferences.getBoolean("" + voisinListé.getId(), false);
            if (isfavori) {
                voisinFavoris.add(voisinListé);
            }
        }

        if(onestdanslefragmentfavori){
            mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(voisinFavoris));
        }
        else {
            mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }
}
