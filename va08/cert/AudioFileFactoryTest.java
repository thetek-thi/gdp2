import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AudioFileFactoryTest {
	@Test
    public void testFactory() {
        try {
            AudioFile f1 = AudioFileFactory
                    .getInstance("audiofiles/Rock 812.mp3");
            assertEquals("MP3 type not recognized",
                    "TaggedFile", f1.getClass().getName());
            AudioFile f2 = AudioFileFactory
                    .getInstance("audiofiles/wellenmeister - tranquility.wav");
            assertEquals("WAV type not recognized",
                    "WavFile", f2.getClass().getName());
            AudioFile f3 = AudioFileFactory
                    .getInstance("audiofiles/wellenmeister_awakening.ogg");
            assertEquals("OGG type not recognized",
                    "TaggedFile", f3.getClass().getName());
            AudioFile f4 = AudioFileFactory
                    .getInstance("audiofiles/special.oGg");
            assertEquals("OGG type not recognized",
                    "TaggedFile", f4.getClass().getName());
            AudioFile f5 = AudioFileFactory
                    .getInstance("audiofiles/kein.wav.sondern.ogg");
            assertTrue("Check for filename suffix not correct",
                    f5 instanceof TaggedFile);
            AudioFile f6 = AudioFileFactory
                    .getInstance("audiofiles/kein.ogg.sondern.wav");
            assertTrue("Check for filename suffix not correct",
                    f6 instanceof WavFile);

        } catch (Exception e) {
            fail("AudioFileFactory is not able to create AudioFile: "
                    + e.getMessage());
        }
    }

	@Test
    public void testException() {
        try {
            AudioFileFactory.getInstance("does not exist.mp3");
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            // Expected
        }
    }
}
