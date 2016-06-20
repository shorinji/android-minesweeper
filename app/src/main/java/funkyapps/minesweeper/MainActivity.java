package funkyapps.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

/**
 * App entry point and only screen so far
 *
 */
public class MainActivity extends Activity {

    final static String TAG = MainActivity.class.getSimpleName();

    final String STATE_TEST_KEY = MainActivity.class.getCanonicalName() + ".testKey";


    /**
     * Called when the Activity is created before it is first displayed.
     * Render xml layout, then add our custom GameFieldView to it
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewGroup rootView = (ViewGroup)findViewById(R.id.rootLayout);

        PlayingFieldView playingFieldView = new PlayingFieldView(this);

        rootView.addView(playingFieldView);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "Saving game state");

        String testVal = "smurf";
        outState.putString(STATE_TEST_KEY, testVal);
        Log.d(TAG, "Saving [" + STATE_TEST_KEY + "] = '" + testVal + "'");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "Restoring game state");

        String testVal = savedInstanceState.getString(STATE_TEST_KEY);
        Log.d(TAG, "Restoring [" + STATE_TEST_KEY + "] = '" + testVal + "'");
    }
}
