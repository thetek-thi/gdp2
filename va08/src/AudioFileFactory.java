public class AudioFileFactory {
    public static AudioFile getInstance(String pathname) {
        if (pathname.toLowerCase().endsWith(".wav"))
            return new WavFile(pathname);
        else if (pathname.toLowerCase().endsWith(".ogg") || pathname.toLowerCase().endsWith(".mp3"))
            return new TaggedFile(pathname);
        else
            throw new RuntimeException("Unknown suffix for audiofile: \"" + pathname + "\"");
    }
}

