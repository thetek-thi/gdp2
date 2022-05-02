package studiplayer.audio;

import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
    public SampledFile() {
        super();
    }

    public SampledFile(String p) throws NotPlayableException {
        super(p);
    }

    public String getFormattedDuration() {
        return timeFormatter(this.duration);
    }

    public String getFormattedPosition() {
        return timeFormatter(BasicPlayer.getPosition());
    }

    public void play() throws NotPlayableException {
        try {
            BasicPlayer.play(this.getPathname());
        } catch(Exception e) {
            throw new NotPlayableException(this.getPathname(), e);
        }
    }

    public void stop() {
        BasicPlayer.stop();
    }

    public void togglePause() {
        BasicPlayer.togglePause();
    }

    public static String timeFormatter(long microtime) {
        if (microtime < 0) throw new RuntimeException("Negative time value provided");
        if (microtime >= 6000000000L) throw new RuntimeException("Time value exceeds allowed format");
        long secs = microtime / 1000000;
        long mins = secs / 60;
        secs %= 60;
        return (mins < 10 ? "0" : "") + mins + ":" + (secs < 10 ? "0" : "") + secs;
    }
}

