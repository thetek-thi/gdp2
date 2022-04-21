package studiplayer.audio;

public class UTestAudioFileFactory {
    public static void test_getInstance() {
        Test test = new Test("AudioFileFactory . getInstance ()");

        try {
            AudioFileFactory.getInstance("unknown.xxx");
            test.add(new TestCase()
                .test  ("no exception")
                .expect("exception")
                .name  ("unknown file extension"));
        } catch (NotPlayableException e) {
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
        } catch (NotPlayableException e) {
            test.add(new TestCase()
                .test  ("exception")
                .expect("exception")
                .name  ("nonexistent file"));
        }

        test.display();
    }
}

