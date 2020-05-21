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
    @Mock
    private Bundle mockBundle;
    @Mock
    private SharedPreferences mockSharedPreferences;

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
    public void neighbourTestingID() {
        //When
        createmockBundle();
        //Then
        assertEquals(NeighbourDetailActivity.getNeighbourFromBundle(mockBundle).getId(), 1L);
    }

    @Test
    public void neighbourTestingName() {
        //When
        createmockBundle();
        //Then
        assertEquals(NeighbourDetailActivity.getNeighbourFromBundle(mockBundle).getName(), "Jean");
    }

    @Test
    public void neighbourTestingAvatarUrl() {
        //When
        createmockBundle();
        //Then
        assertEquals(NeighbourDetailActivity.getNeighbourFromBundle(mockBundle).getAvatarUrl(), "un avatar");
    }

    @Test
    public void neighbourTestingAdress() {
        //When
        createmockBundle();
        //Then
        assertEquals(NeighbourDetailActivity.getNeighbourFromBundle(mockBundle).getAddress(), "10 rue du poulet");
    }

    @Test
    public void neighbourTestingPhoneNumber() {
        //When
        createmockBundle();
        //Then
        assertEquals(NeighbourDetailActivity.getNeighbourFromBundle(mockBundle).getPhoneNumber(), "0102030405");
    }

    @Test
    public void neighbourTestingAboutMe() {
        //When
        createmockBundle();
        //Then
        assertEquals(NeighbourDetailActivity.getNeighbourFromBundle(mockBundle).getAboutMe(), "salut c'est moi");
    }

    public void createmockBundle() {
        when(mockBundle.getLong("id")).thenReturn(1L);
        when(mockBundle.getString("name")).thenReturn("Jean");
        when(mockBundle.getString("avatarUrl")).thenReturn("un avatar");
        when(mockBundle.getString("adress")).thenReturn("10 rue du poulet");
        when(mockBundle.getString("phoneNumber")).thenReturn("0102030405");
        when(mockBundle.getString("aboutMe")).thenReturn("salut c'est moi");
    }

    public void createmockSharedPreferences() {

        when(mockSharedPreferences.getBoolean("1", false)).thenReturn(false);
        when(mockSharedPreferences.getBoolean("2", false)).thenReturn(true);

    }

    @Test
    public void NeighbourIsFavori() {
        //Given
        Neighbour Favori = new Neighbour(2, "Favori", "url", "address", "0102030405", "RAS", false);
        //When
        createmockSharedPreferences();
        //Then
        assertTrue(NeighbourDetailActivity.isFavori(Favori.getId(), mockSharedPreferences));
    }

    @Test
    public void NeighbourIsNotFavori() {
        //Given
        Neighbour Favori = new Neighbour(1, "Favori", "url", "address", "0102030405", "RAS",true);
        //When
        createmockSharedPreferences();
        //Then
        assertFalse(NeighbourDetailActivity.isFavori(Favori.getId(), mockSharedPreferences));
    }

    @Test
    public void NeighbourListFavori() {
        //When
        createmockSharedPreferences();
        //Then
        assertEquals(1, NeighbourFragment.getNeighboursFavori().size());
    }
}


