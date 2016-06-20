package funkyapps.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * App entry point and only screen so far
 *
 */
public class MainActivity extends Activity {

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
}
