package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null)
            throw new NullPointerException("AlbumComparator used with null as argument");
        return (int) (af1.getRawDuration() - af2.getRawDuration());
    }
}

