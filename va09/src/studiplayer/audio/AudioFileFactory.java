package studiplayer.audio;

public class AudioFileFactory {
    public static AudioFile getInstance(String pathname) throws NotPlayableException {
        if (pathname.toLowerCase().endsWith(".wav"))
            return new WavFile(pathname);
        else if (pathname.toLowerCase().endsWith(".ogg") || pathname.toLowerCase().endsWith(".mp3"))
            return new TaggedFile(pathname);
        else
            throw new NotPlayableException(pathname, "Unknown file extension");
    }
}

