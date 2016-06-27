package funkyapps.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    // single drawable image containing all various tile types
    Bitmap mSpritemap;

    // our custom game board data structure
    GameGrid mGrid;

    // destination frame used when drawing
    Rect mDestRectangle;

    public PlayingFieldView(Context context) {
        super(context);

        mDestRectangle = new Rect();

        mHolder = getHolder();
        mHolder.addCallback(this);

        mSpritemap = BitmapFactory.decodeResource(getResources(), R.drawable.spritemap);
    }


    private void createNewPlayingField(int numTiles) {
        mNumTiles = numTiles;

        // creates a new random playing field
        mGrid = new GameGrid(mNumTiles);
    }

    /**
     * Redraws the game field. Only called as needed
     *
     * @param holder SurfaceHolder
     */
    void redrawGameField(SurfaceHolder holder) {

        if(mNumTiles < 0) {
            return;
        }

        Canvas canvas = holder.lockCanvas();

        // position to draw tile to
        mDestRectangle.left = 0;
        mDestRectangle.top = 0;
        mDestRectangle.right = TILE_SIZE - 1;
        mDestRectangle.bottom = TILE_SIZE - 1;

        // loop over playing field
        for(int y = 0 ; y < mNumTiles ; y++) {
            for(int x = 0 ; x < mNumTiles ; x++) {

                Rect srcRectangle = mGrid.getSourceRectangleForTile(x, y);
                canvas.drawBitmap(mSpritemap, srcRectangle, mDestRectangle, null);

                // move one step to the right
                mDestRectangle.offset(TILE_SIZE, 0);
            }
            // move to start of next row
            mDestRectangle.offsetTo(0, (y + 1) * TILE_SIZE);
        }

        // draw to screen
        holder.unlockCanvasAndPost(canvas);
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

        redrawGameField(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}
