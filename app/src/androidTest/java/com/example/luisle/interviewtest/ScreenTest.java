package com.example.luisle.interviewtest;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.luisle.interviewtest.utils.AppUtils;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by LuisLe on 7/2/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new
            ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void init() {
//        for (int i = 0; i < 6; i ++) {
//            addPlace();
//        }
    }

    @Test
    public void clickFabAddPlace_OpenAddEditUI() throws Exception {
        // Perform click on fab add
        onView(withId(R.id.fabListFrag_AddPlace))
                .perform(click());
        // Check Image View is displayed
        onView(withId(R.id.imgAddEditFrag)).check(matches(isDisplayed()));

    }

    @Test
    public void clickPlaceOnRecyclerView_OpenDetailUI() throws Exception {

        // Perform click on recyclerview item
        onView(withId(R.id.rcvListFrag_Places))
                .perform(click());

        onView(isRoot()).perform(waitFor(2000));

        onView(withId(R.id.imgDetailFrag)).check(matches(isDisplayed()));
    }

    @Test
    public void clickFabEditPlace_OpenAddEditUI() throws Exception {

        // Open Place's Detail UI
        onView(withId(R.id.rcvListFrag_Places))
                .perform(click());

        onView(isRoot()).perform(waitFor(2000));

        // Check Image View is displayed
        onView(withId(R.id.imgDetailFrag)).check(matches(isDisplayed()));
        // Perform click on Fab Edit
        onView(withId(R.id.ibtnDetailFrag_Edit))
                .perform(click());

        onView(isRoot()).perform(waitFor(1000));
        // Check Image View is displayed
        onView(withId(R.id.imgAddEditFrag)).check(matches(isDisplayed()));
    }

    @Test
    public void clickFabEditPlace_OpenAddEditUI_Edit() throws Exception {
        editPlace();

        onView(withId(R.id.edtDetailFrag_PlaceName)).check(matches(withText("Starbucks Coffee")));

    }

    @Test
    public void clickFabDeletePlace_Delete() {
        // Perform click on recyclerview item
        onView(withId(R.id.rcvListFrag_Places))
                .perform(click());

        onView(isRoot()).perform(waitFor(2000));

        onView(withId(R.id.edtDetailFrag_PlaceName)).check(matches(isDisplayed()));

        // Get alert dialog message
        String message = getResourceString(R.string.txt_delete_alert_dialog)
                + " '"
                + getText(withId(R.id.edtDetailFrag_PlaceName)) + "'";


        onView(withId(R.id.ibtnDetailFrag_Delete)).perform(click());

        onView(isRoot()).perform(waitFor(500));

        onView(withText(message)).check(matches(isDisplayed()));

        // Perform click Yes
        onView(withId(android.R.id.button1)).perform(click());

        onView(isRoot()).perform(waitFor(500));

        onView(withId(R.id.rcvListFrag_Places)).check(matches(isDisplayed()));
    }

    @Test
    public void clickFabDeletePlace_NoDelete() {

        // Perform click on recyclerview item
        onView(withId(R.id.rcvListFrag_Places))
                .perform(click());

        onView(isRoot()).perform(waitFor(2000));

        onView(withId(R.id.edtDetailFrag_PlaceName)).check(matches(isDisplayed()));

        // Get alert dialog message
        String message = getResourceString(R.string.txt_delete_alert_dialog)
                + " '"
                + getText(withId(R.id.edtDetailFrag_PlaceName)) + "'";


        onView(withId(R.id.ibtnDetailFrag_Delete)).perform(click());

        onView(isRoot()).perform(waitFor(500));

        onView(withText(message)).check(matches(isDisplayed()));

        // Perform click No
        onView(withId(android.R.id.button2)).perform(click());

        onView(isRoot()).perform(waitFor(500));

        onView(withId(R.id.imgDetailFrag)).check(matches(isDisplayed()));
    }

    @Test
    public void clickSaveWithoutFillingData() {
        // Click on the add task button
        onView(withId(R.id.fabListFrag_AddPlace)).perform(click());

        // Save the place
        onView(withId(R.id.btnAddEditFrag_Save)).perform(click());

        String error = getResourceString(R.string.error_field_can_not_be_empty);

        onView(withId(R.id.edtAddEditFrag_PlaceName)).check(matches(hasErrorText(error)));
        onView(withId(R.id.edtAddEditFrag_PlaceAddress)).check(matches(hasErrorText(error)));
        onView(withId(R.id.edtAddEditFrag_PlaceDescription)).check(matches(hasErrorText(error)));
    }

    @Test
    public void orientationChangeToLandscape() {
        TestUtils.rotateToLandscape(mainActivityActivityTestRule.getActivity());
        onView(isRoot()).perform(waitFor(5000));
        onView(withId(R.id.rcvListFrag_Places)).check(matches(isDisplayed()));

        if (AppUtils.deviceIsTabletAndInLandscape(mainActivityActivityTestRule.getActivity())) {

            onView(withId(R.id.imgAddEditFrag)).check(matches(isDisplayed()));
        }

    }

    @Test
    public void clickFabGetDirection() {
        // Perform click on recyclerview item
        onView(withId(R.id.rcvListFrag_Places))
                .perform(click());

        onView(isRoot()).perform(waitFor(2000));

        onView(withId(R.id.edtDetailFrag_PlaceName)).check(matches(isDisplayed()));

        onView(withId(R.id.ibtnDetailFrag_Direction)).perform(click());

        onView(isRoot()).perform(waitFor(500));

        allowPermissionsIfNeeded();

        onView(isRoot()).perform(waitFor(500));

        onView(withId(R.id.mvMapFrag_Map)).check(matches(isDisplayed()));

    }

    private void addPlace() {

        // Click on the add task button
        onView(withId(R.id.fabListFrag_AddPlace)).perform(click());

        // Add new place with PlaceName, PlaceAddress, PlaceDescription
        onView(withId(R.id.edtAddEditFrag_PlaceName)).perform(typeText("Du Mien Coffee"),
                closeSoftKeyboard()); // Type new place name
        onView(withId(R.id.edtAddEditFrag_PlaceAddress)).perform(typeText("Go Vap"),
                closeSoftKeyboard()); // Type new place address and close the keyboard
        onView(withId(R.id.edtAddEditFrag_PlaceDescription)).perform(typeText("Lots of trees"),
                closeSoftKeyboard()); // Type new place description and close the keyboard

        // Save the place
        onView(withId(R.id.btnAddEditFrag_Save)).perform(click());
    }

    private void editPlace() {
        // Open Place's Detail UI
        onView(withId(R.id.rcvListFrag_Places))
                .perform(click());

        onView(isRoot()).perform(waitFor(2000));

        // Check Image View is displayed
        onView(withId(R.id.imgDetailFrag)).check(matches(isDisplayed()));
        // Perform click on Fab Edit
        onView(withId(R.id.ibtnDetailFrag_Edit))
                .perform(click());

        onView(isRoot()).perform(waitFor(1000));
        // Check Image View is displayed
        onView(withId(R.id.imgAddEditFrag)).check(matches(isDisplayed()));

        // Add new place with PlaceName, PlaceAddress, PlaceDescription
        onView(withId(R.id.edtAddEditFrag_PlaceName)).perform(clearText());
        onView(withId(R.id.edtAddEditFrag_PlaceName)).perform(typeText("Starbucks Coffee"),
                closeSoftKeyboard()); // Type new place name

        onView(withId(R.id.edtAddEditFrag_PlaceAddress)).perform(clearText());
        onView(withId(R.id.edtAddEditFrag_PlaceAddress)).perform(typeText("Quan 1"),
                closeSoftKeyboard()); // Type new place address and close the keyboard

        onView(withId(R.id.edtAddEditFrag_PlaceDescription)).perform(clearText());
        onView(withId(R.id.edtAddEditFrag_PlaceDescription)).perform(typeText("Luxury"),
                closeSoftKeyboard()); // Type new place description and close the keyboard

        // Save the place
        onView(withId(R.id.btnAddEditFrag_Save)).perform(click());

        onView(isRoot()).perform(waitFor(1000));
    }

    private static void allowPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = device.findObject(new UiSelector().text("Allow"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    Log.e("UiObjectNotFound", e.getMessage());
                }
            }
        }
    }

    // Get String.xml resource
    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }

    // Wait for something
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    // Get text from EditText or TextView
    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

}
