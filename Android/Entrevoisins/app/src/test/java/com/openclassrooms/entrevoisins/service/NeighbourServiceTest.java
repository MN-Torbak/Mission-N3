package com.openclassrooms.entrevoisins.service;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit test on Neighbour service
 */
@RunWith(MockitoJUnitRunner.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void setFavoriteInService() {
        //Given
        Neighbour favori = service.getNeighbours().get(0);
        //When
        service.setFavoriteInService(true, favori);
        //Then
        assertTrue(service.getNeighbours().get(0).isFavorite());
    }

    @Test
    public void setNoFavoriteInService() {
        //Given
        Neighbour favori = service.getNeighbours().get(0);
        //When
        service.setFavoriteInService(false, favori);
        //Then
        assertFalse(service.getNeighbours().get(0).isFavorite());
    }

    @Test
    public void NeighbourIsFavori() {
        //Given
        Neighbour favori = service.getNeighbours().get(0);
        //When
        favori.setFavorite(true);
        //Then
        assertTrue(favori.isFavorite());
    }

    @Test
    public void NeighbourIsNotFavori() {
        //Given
        Neighbour notFavori = service.getNeighbours().get(0);
        //When
        notFavori.setFavorite(false);
        //Then
        assertFalse(notFavori.isFavorite());
    }

    @Test
    public void NeighbourListFavori() {
        //Given
        Neighbour favori = service.getNeighbours().get(0);
        //When
        favori.setFavorite(true);
        //Then
        assertTrue(service.getNeighboursFavori().contains(favori));
    }
}


