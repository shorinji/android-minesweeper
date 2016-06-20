package funkyapps.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Custom View that allows for free 2d graphics drawing, suitable for drawing a
 * playing field.
 *
 * Implements lifecycle callbacks for the view to control creation and destruction
 */
public class PlayingFieldView extends SurfaceView implements SurfaceHolder.Callback {

    // Holds background color
    Paint mPaint;

    // Used to gain control over drawing
    SurfaceHolder mHolder;

    // HashMap<Integer, Bitmap> mTileBitmaps;

    public PlayingFieldView(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setColor(0xFFDDDDDD); // nice light grey for now

        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    /**
     * Redraws the game field. Only called as needed
     *
     * @param holder SurfaceHolder
     */
    void redrawGameField(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();

        Rect rect = holder.getSurfaceFrame();
        canvas.drawRect(rect, mPaint);

        holder.unlockCanvasAndPost(canvas);
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        redrawGameField(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        redrawGameField(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}
