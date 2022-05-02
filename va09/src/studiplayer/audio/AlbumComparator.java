package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null)
            throw new NullPointerException("AlbumComparator used with null as argument");
        if (af1 instanceof TaggedFile && af2 instanceof TaggedFile)
            return ((TaggedFile) af1).getAlbum().compareTo(((TaggedFile) af2).getAlbum());
        else if (af1 instanceof TaggedFile) return 1;
        else if (af2 instanceof TaggedFile) return -1;
        return 0;
    }
}

