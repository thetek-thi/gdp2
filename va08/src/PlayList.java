import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

public class PlayList extends LinkedList<AudioFile> {
    private int                   current;
    private boolean               randomOrder;
    private LinkedList<AudioFile> shuffled;

    public PlayList() {
        super();
        this.current = 0;
        this.shuffled = (LinkedList<AudioFile>) new LinkedList<AudioFile>();
    }

    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public AudioFile getCurrentAudioFile() {
        if (this.current == 0)
            Collections.shuffle(this.shuffled);

        if (this.current < 0 || this.current >= this.size())
            return null;
        if (this.randomOrder)
            return this.shuffled.get(this.current);
        return this.get(this.current);
    }

    public void changeCurrent() { if (this.current < 0 || this.current >= this.size() - 1)
            this.current = 0;
        else
            ++this.current;
    }

    public void setRandomOrder(boolean randomOrder) {
        this.randomOrder = randomOrder;
        Collections.shuffle(this.shuffled);
    }

    public boolean add(AudioFile a) {
        boolean res = super.add(a);
        this.shuffled.add(a);
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
}

