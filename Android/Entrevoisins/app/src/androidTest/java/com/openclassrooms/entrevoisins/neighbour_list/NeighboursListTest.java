
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.SelectViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;


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
     */
    @Test
    public void myNeighbour_TextView_shouldcontainsnameuser_whenscreenstart() {
        // Etant donné que : on a un voisin
        onView(ViewMatchers.withId(R.id.activity_utilisateur));
        // Quand : on a une TextView
        onView(ViewMatchers.withId(R.id.app_bar));
        // Alors : on a le nom de l'utilisateur dans la TextView
        onView(ViewMatchers.withId(R.id.app_bar))
                .check(VoisinName);
    }

    /**
     * On vérifie que l'onglet Favori n'affiche que les voisins marqués comme favoris'
     */
    @Test
    public void myNeighbourListFavori_shouldonlycontainsneighboursfavoris() {
        // Etant donné que : on a une liste de voisins favoris
        onView(ViewMatchers.withId(R.id.list_neighbours_favoris));
        // Quand : on a un voisin non favori

        // Alors : le voisin non favori n'apparaît pas dans la liste de favoris

    }



}