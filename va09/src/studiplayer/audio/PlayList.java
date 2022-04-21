package studiplayer.audio;

// java imports are ridiculous
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class PlayList extends LinkedList<AudioFile> {
    private int                   current;
    private boolean               randomOrder;
    private LinkedList<AudioFile> ordered; // i tried it with the shuffled list being an attribute but the unit tests didn't like it. so now the ordered list ist the attribute ¯\_(._.)/¯

    public PlayList() {
        super();
        this.current = 0;
        this.ordered = new LinkedList<AudioFile>();
    }

    public PlayList(String pathname) {
        this();
        this.loadFromM3U(pathname);
    }

    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public AudioFile getCurrentAudioFile() {
        if (this.current == 0)
            Collections.shuffle(this);

        if (this.current < 0 || this.current >= this.size())
            return null;
        if (!this.randomOrder)
            return this.ordered.get(this.current);
        return this.get(this.current);
    }

    public void changeCurrent() {
        if (this.current < 0 || this.current >= this.size() - 1) {
            this.current = 0;
            if (this.randomOrder)
                Collections.shuffle(this);
        }
        else
            ++this.current;
    }

    public void setRandomOrder(boolean randomOrder) {
        this.randomOrder = randomOrder;
        Collections.shuffle(this);
    }

    public boolean add(AudioFile a) {
        boolean res = super.add(a);
        this.ordered.add(a);
        return res;
    }

    public void saveAsM3U(String pathname) {
        FileWriter writer = null;
        String linesep = System.getProperty("line.separator");
        try {
            writer = new FileWriter(pathname);
            writer.write("# m3u file created by " + System.getProperty("user.name") + linesep);
            writer.write("# m3u file created on " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault()).format(Instant.now()) + linesep + linesep);
            for (AudioFile af : this)
                writer.write(af.getPathname() + linesep);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write file " + pathname + ": " + e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }

    public void loadFromM3U(String pathname) {
        this.clear();
        Scanner scanner = null;
        String line;
        try {
            scanner = new Scanner(new File(pathname));
            int i = 1;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!(line.startsWith("#") || line.isBlank())) {
                    try {
                        this.add(AudioFileFactory.getInstance(line));
                    } catch(NotPlayableException e) {
                        e.printStackTrace();
                    }
                }
                ++i;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                scanner.close();
            } catch (Exception e) {}
        }
    }

    public String toString(Object o) {
        if (this.randomOrder)
            return super.toString();
        return this.ordered.toString();
    }
}

