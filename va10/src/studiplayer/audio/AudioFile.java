package studiplayer.audio;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AudioFile {
    protected String author;
    private   String filename;
    private   String path;
    protected String title;
    protected long   duration;

    public static final Pattern FILENAME_PATTERN = Pattern.compile("^(?:\\s*(.*[^\\s]|)\\s+-\\s+)?(?:([^.\\s]*(?:\\s*\\.*[^.\\s]*)*?|))?(?:\\s*\\.[^.]*?)?\\s*$");

    public AudioFile() { }

    public AudioFile(String p) throws NotPlayableException {
        this.parsePathname(p);
        if (!(new File(this.path)).canRead())
            throw new NotPlayableException(this.path, "Invalid path");
        this.parseFilename(this.filename);
    }

    public abstract String getFormattedDuration();
    public abstract String getFormattedPosition();
    public abstract void play() throws NotPlayableException;
    public abstract void stop();
    public abstract void togglePause();
    public abstract String[] fields();

    public void parsePathname(String p) {
        char sep = System.getProperty("file.separator").charAt(0);
        String replace = sep == '\\' ? "\\\\" : sep + "";
        this.path = p.replaceAll("[/\\\\]+", replace);
        this.filename = this.path.substring(this.path.lastIndexOf(sep) + 1);
        if (!isAHorribleOperatingSystem())
            this.path = this.path.replaceFirst("^([a-zA-Z]):" + sep, sep + "$1" + sep);
    }

    public void parseFilename(String f) {
        // regex magic go brrr
        Matcher matcher = FILENAME_PATTERN.matcher(f);
        if (matcher.find()) {
            this.author = matcher.group(1);
            this.title = matcher.group(2);
        }
        if (this.author == null) this.author = "";
        if (this.title == null) this.title = "";
    }

    public String getPathname() {
        return this.path;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }
    
    public long getRawDuration() {
        return this.duration;
    }

    public String toString() {
        if (this.author.isEmpty())
            return this.title;
        return this.author + " - " + this.title + " - " + this.getFormattedDuration();
    }

    /**
     * isAHorribleOperatingSystem() - returns true if the user is running a
     * horrible operating system (i.e. Windows). Hoewever, if the user is
     * running a good operating system (such as Linux), this method will return
     * false.
     */
    private static boolean isAHorribleOperatingSystem() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }
}

