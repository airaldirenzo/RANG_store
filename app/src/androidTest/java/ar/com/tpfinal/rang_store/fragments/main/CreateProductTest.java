package ar.com.tpfinal.rang_store.fragments.main;

import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.filters.LargeTest;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.com.tpfinal.rang_store.LoginActivity;
import ar.com.tpfinal.rang_store.R;


/*
    Para poder correr el test se debe estar deslogeado de la aplicacion
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateProductTest extends TestCase {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTest() {
        onView(withId(R.id.editTextEmailAddress)).perform(typeText("nicolasgoux2000@gmail.com"));
        onView(withId(R.id.editTextPassword)).perform(typeText("asd123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonIniciarSesion)).perform(click());
        SystemClock.sleep(3000);
    }

    @Test
    public void createProductTest() {
        onView(withId(R.id.floatingCreateProductActionButton)).perform(click());
        onView(withId(R.id.editTextProductTitle)).perform(typeText("Nuevo Producto Test"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editTextProductDescription)).perform(typeText("Producto creado para testear") );
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editTextProductPrice)).perform(typeText("4000.0") );
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.categoriesSpinner)).perform(click());
        onView(withText("otros")).perform(click());
        onView(withId(R.id.createProductButton)).perform(click());
        SystemClock.sleep(2000);
    }

    @Test
    public void logOutTest() {
        onView(withId(R.id.drawerLayout)).perform(actionOpenDrawer());
        SystemClock.sleep(500);
        onView(withId(R.id.drawerLogOut)).perform(click());
    }

    public static ViewAction actionOpenDrawer() {
        return new ViewAction() {
            @Override public org.hamcrest.Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override public String getDescription() {
                return "open drawer";
            }

            @Override public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).openDrawer(GravityCompat.START);
            }
        };
    }
}