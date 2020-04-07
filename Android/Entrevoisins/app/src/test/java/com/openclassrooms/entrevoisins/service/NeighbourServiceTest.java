package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.model.NeighbourFavori;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailActivity;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void favoriteNeighbourShouldBeInFavorites() {
        //Given
        Neighbour jean = new Neighbour(0L,"Jean","url","address", "0102030405","RAS");
        List<Neighbour> voisinsFavoris = new ArrayList<>();
        //When
        voisinsFavoris.add(jean);
        //Then
        assertTrue(NeighbourDetailActivity.isFavori(jean,voisinsFavoris));
    }

    @Test
    public void neighbourShouldNotBeInFavorites() {
        //Given
        Neighbour jean = new Neighbour(0L,"Jean","url","address", "0102030405","RAS");
        List<Neighbour> voisinsFavoris = new ArrayList<>();
        //When

        //Then
        assertFalse(NeighbourDetailActivity.isFavori(jean,voisinsFavoris));
    }

}
