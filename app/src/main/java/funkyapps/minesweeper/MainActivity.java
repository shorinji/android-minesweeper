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

    // Used to be able to save the game grid state
    PlayingFieldView mPlayingFieldView;

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

        mPlayingFieldView = new PlayingFieldView(this);
        rootView.addView(mPlayingFieldView);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");

        GameGrid grid = mPlayingFieldView.getGrid();

        String key = MainActivity.class.getCanonicalName() + ".grid";
        Log.d(TAG, "Saving grid state to key " + key);
        outState.putParcelable(key, grid);

        key = MainActivity.class.getCanonicalName() + ".state";
        outState.putSerializable(key, mPlayingFieldView.getState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState()");

        String key = MainActivity.class.getCanonicalName() + ".grid";
        GameGrid grid = savedInstanceState.getParcelable(key);
        mPlayingFieldView.setGrid(grid);

        key = MainActivity.class.getCanonicalName() + ".state";
        PlayingFieldView.PlayingState state =
                (PlayingFieldView.PlayingState) savedInstanceState.getSerializable(key);
        mPlayingFieldView.setState(state);
    }
}
