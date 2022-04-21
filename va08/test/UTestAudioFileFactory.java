public class UTestAudioFileFactory {
    public static void test_getInstance() {
        Test test = new Test("AudioFileFactory . getInstance ()");

        try {
            AudioFileFactory.getInstance("unknown.xxx");
            test.add(new TestCase()
                .test  ("no exception")
                .expect("exception")
                .name  ("unknown file extension"));
        } catch (Exception e) {
            test.add(new TestCase()
                .test  ("exception")
                .expect("exception")
                .name  ("unknown file extension"));
        }

        try {
            AudioFileFactory.getInstance("nonexistent.mp3");
            test.add(new TestCase()
                .test  ("no exception")
                .expect("exception")
                .name  ("nonexistent file"));
        } catch (Exception e) {
            test.add(new TestCase()
                .test  ("exception")
                .expect("exception")
                .name  ("nonexistent file"));
        }

        test.add(new TestCase()
            .test  ((AudioFileFactory.getInstance("audiofiles/Eisbach Deep Snow.ogg") instanceof TaggedFile) + "")
            .expect("true")
            .name  (".ogg -> TaggedFile"));
        test.add(new TestCase()
            .test  ((AudioFileFactory.getInstance("audiofiles/wellenmeister - tranquility.wav") instanceof WavFile) + "")
            .expect("true")
            .name  (".wav -> WavFile"));
        test.add(new TestCase()
            .test  ((AudioFileFactory.getInstance("audiofiles/special.oGg") instanceof TaggedFile) + "")
            .expect("true")
            .name  (".oGg -> TaggedFile"));

        test.display();
    }
}

