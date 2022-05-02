package studiplayer.audio;

import java.util.Map;
import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {
    private String album;

    public TaggedFile() {
        super();
    }

    public TaggedFile(String p) throws NotPlayableException {
        super(p);
        this.readAndStoreTags();
    }

    public void readAndStoreTags() throws NotPlayableException {
        Map<String, Object> tagMap;
        try {
            tagMap = TagReader.readTags(this.getPathname());
        } catch(Exception e) {
            throw new NotPlayableException(this.getPathname(), e);
        }
        String author = (String) tagMap.get("author");
        String title = (String) tagMap.get("title");
        String album = (String) tagMap.get("album");
        long duration = (long) tagMap.get("duration");
        if (author != null && !author.isEmpty()) this.author = author.trim();
        if (title != null && !title.isEmpty()) this.title = title.trim();
        this.album = (album == null ? "" : album.trim());
        this.duration = duration;
    }

    public String getAlbum() {
        return this.album.trim();
    }

    public String toString() {
        return (this.author.isEmpty() ? "" : this.author + " - ") + this.title + " - " + (this.album.isEmpty() ? "" : this.album + " - ") + this.getFormattedDuration();
    }

    public String[] fields() {
        return new String[] { this.author, this.title, (this.album == null ? "" : this.album), this.getFormattedDuration() };
    }
}

