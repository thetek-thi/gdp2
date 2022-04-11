import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
    public WavFile() {
        super();
    }

    public WavFile(String p) {
        super(p);
        this.readAndSetDurationFromFile(this.getPathname());
    }

    public static long computeDuration(long numberOfFrames, float frameRate) {
        return (long) ((numberOfFrames * 1000000) / frameRate);
    }

    public void readAndSetDurationFromFile(String p) {
        WavParamReader.readParams(p);
        this.duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
    }

    public String[] fields() {
        return new String[] { this.author, this.title, "", this.getFormattedDuration() };
    }
}

