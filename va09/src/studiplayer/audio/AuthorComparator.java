package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null)
            throw new NullPointerException("AuthorComparator used with null as argument");
        return af1.getAuthor().compareTo(af2.getAuthor());
    }
}

