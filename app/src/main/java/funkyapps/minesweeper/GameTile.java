package funkyapps.minesweeper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple plain old java object representing one tile with its individual properties
 */
public class GameTile implements Parcelable {

    public boolean isRevealed = false;
    public boolean isMine = false;
    public int count = 0;


    public GameTile() {

    }
    /*
     * Ahead is implementation of Parcelable (generated code).
     * This make the class writable to disk, which is used during screen rotations
     */
    protected GameTile(Parcel in) {
        isRevealed = in.readByte() != 0;
        isMine = in.readByte() != 0;
        count = in.readInt();
    }

    public static final Creator<GameTile> CREATOR = new Creator<GameTile>() {
        @Override
        public GameTile createFromParcel(Parcel in) {
            return new GameTile(in);
        }

        @Override
        public GameTile[] newArray(int size) {
            return new GameTile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isRevealed ? 1 : 0));
        dest.writeByte((byte) (isMine ? 1 : 0));
        dest.writeInt(count);
    }
}
