
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.model.NeighbourFavori;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.SelectViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static junit.framework.TestCase.assertTrue;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * On vérifie que les détails soient lancés après un clique
     */
    @Test
    public void myNeighboursList_clickAction_shouldcontainsdetails() {
        // Etant donné que : on a une liste de voisins
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
        // Quand : on clique sur un voisin
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new SelectViewAction()));
        // Alors : on a les détails du voisin
        onView(ViewMatchers.withId(R.id.imageviewdetails))
                .check(matches(isDisplayed()));
    }

    /**
     * On vérifie que le TextView indique le nom de l'utilisateur lors du démarrage de l'écran
     * <p>
     * test vérifiant qu’au démarrage de ce nouvel écran, le TextView indiquant que
     * le nom de l’utilisateur en question est bien rempli
     */
    @Test
    public void myNeighbour_TextView_shouldcontainsnameuser_nameshouldmatches() {
        String nomDuVoisin = "Jack";
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new SelectViewAction()));
        // Alors : on a les détails du voisin
        onView(ViewMatchers.withId(R.id.name)).check(matches(withText(nomDuVoisin)));
    }
    /**
     * On vérifie que l'onglet Favori n'affiche que les voisins marqués comme favoris'
     */
    @Test
    public void neighbourInFavoriteTabShouldBeInFavorites() throws Exception {
        //Given on met Caroline dans les Favoris
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new SelectViewAction()));
        //When on clique sur l'onglet Favori
        onView(ViewMatchers.withId(R.id.fab))
                .perform(click());
        onView(ViewMatchers.withId(R.id.fab))
                .perform(pressBack());
        try {
            mActivityRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.recreate();
                }
            });
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        onView(ViewMatchers.withText(R.string.tab_favorites_title))
                .perform(click());
        //Then on voit Caroline dans l'onglet Favori
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(withItemCount(1));

    }

}
