package funkyapps.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Custom View that allows for free 2d graphics drawing, suitable for drawing a
 * playing field.
 *
 * Implements lifecycle callbacks for the view to control creation and destruction
 */
public class PlayingFieldView extends SurfaceView implements SurfaceHolder.Callback {

    final static String TAG = PlayingFieldView.class.getSimpleName();


    // number of pixels horizontally and vertically per tile
    final static int TILE_SIZE = 100;


    // Gives additional control over drawing
    SurfaceHolder mHolder;


    // Number of tiles that will fit in one direction on screen
    // The smallest value is used for both axes to make a square playing field
    int mNumTiles = -1;

    // our custom game board data structure
    GameGrid mGrid;

    // destination frame used when drawing
    Rect mDestRectangle;

    public PlayingFieldView(Context context) {
        super(context);

        mDestRectangle = new Rect();

        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    private void createNewPlayingField(int numTiles) {
        mNumTiles = numTiles;

        // creates a new random playing field
        mGrid = new GameGrid(mNumTiles);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Rect frame = holder.getSurfaceFrame();
        int width = frame.width();
        int height = frame.height();

        int numTiles;
        if (width > height) { // landscape
            numTiles = (int)Math.floor(height / TILE_SIZE);
        } else { // portrait
            numTiles = (int)Math.floor(width / TILE_SIZE);
        }

        createNewPlayingField(numTiles);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        Log.d(TAG, "surfaceChanged() new width = " + width + ", height = " + height);

        Canvas c = holder.lockCanvas();

        Rect r = new Rect(0, 0, width, height);
        Paint p = new Paint();
        p.setColor(Color.LTGRAY);
        c.drawRect(r, p);

        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}
