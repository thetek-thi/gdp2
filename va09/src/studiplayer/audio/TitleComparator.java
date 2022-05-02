package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null)
            throw new NullPointerException("TitleComparator used with null as argument");
        return af1.getTitle().compareTo(af2.getTitle());
    }
}

