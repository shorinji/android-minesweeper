package funkyapps.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.util.Log;
import android.view.MotionEvent;
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

    public enum PlayingState {
        PLAYING,
        WON,
        GAME_OVER
    }

    // number of pixels horizontally and vertically per tile
    final static int TILE_SIZE = 100;


    // Gives additional control over drawing
    SurfaceHolder mHolder;

    PlayingState mState;


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


    public PlayingState getState() {
        return mState;
    }

    public void setState(PlayingState state) {
        mState = state;
    }


    private void createNewPlayingField() {
        Log.d(TAG, "createNewPlayingField()");

        // creates a new random playing field
        mGrid = new GameGrid(mNumTiles);
        mState = PlayingState.PLAYING;
    }

    /**
     * Redraws the game field. Only called as needed
     *
     * @param holder SurfaceHolder
     */
    void redrawGameField(SurfaceHolder holder) {
        //mHolder = holder;
        if(mState != PlayingState.PLAYING) {
            Log.d(TAG, "redrawGameField() state = " + mState);
        }

        if(mNumTiles < 0) {
            return;
        }

        if (mState == PlayingState.GAME_OVER) {
            printGameOver();
            return;
        } else if (mState == PlayingState.WON) {
            printWin();
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

    public GameGrid getGrid() {
        return mGrid;
    }

    public void setGrid(GameGrid grid) {
        mGrid = grid;

        if(mNumTiles > 0) {
            redrawGameField(mHolder);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Rect frame = holder.getSurfaceFrame();
        int width = frame.width();
        int height = frame.height();

        if (width > height) { // landscape
            mNumTiles = (int)Math.floor(height / TILE_SIZE);
        } else { // portrait
            mNumTiles = (int)Math.floor(width / TILE_SIZE);
        }

        if (mGrid == null) {
            createNewPlayingField();
        }
        // else mGrid was restored from saved state
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged() new width = " + width + ", height = " + height);

        redrawGameField(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        //Log.d(TAG, "onTouchEvent(DOWN) state = " + mState);

        if(mState == PlayingState.GAME_OVER) {
            // start over
            createNewPlayingField();

        } else { // mState == PLAYING

            // convert from pixel position to tile index
            int x = (int) Math.floor(event.getX() / TILE_SIZE);
            int y = (int) Math.floor(event.getY() / TILE_SIZE);

            handleClick(x, y);
        }

        redrawGameField(mHolder);

        return true;
    }

    // quick and dirty game over screen
    private void printGameOver() {
        Canvas c = getHolder().lockCanvas();

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.RED);
        p.setTextSize(TILE_SIZE);
        c.drawText("GAME OVER", TILE_SIZE, (mNumTiles / 2) * TILE_SIZE, p);

        getHolder().unlockCanvasAndPost(c);
    }

    // quick and dirty win screen
    private void printWin() {
        Canvas c = getHolder().lockCanvas();

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.GREEN);
        p.setTextSize(TILE_SIZE);
        c.drawText("YOU WIN!", TILE_SIZE, (mNumTiles / 2) * TILE_SIZE, p);

        getHolder().unlockCanvasAndPost(c);
    }

    public void handleClick(int x, int y) {
        mState = mGrid.revealTile(x, y);
    }

}
