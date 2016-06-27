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

    // Used in logging
    final static String TAG = MainActivity.class.getSimpleName();


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

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "Restoring game state");

    }
}
