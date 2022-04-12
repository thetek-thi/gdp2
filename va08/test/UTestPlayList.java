public class UTestPlayList {
    public static void test_getCurrentAudioFile() {
        Test test = new Test("PlayList # getCurrentAudioFile ()");

        test.add(new TestCase()
            .test  (((new PlayList()).getCurrentAudioFile() == null) + "")
            .expect("true")
            .name  ("empty playlist"));

        PlayList pl1 = new PlayList();
        pl1.add(new TaggedFile("audiofiles/Eisbach Deep Snow.ogg"));
        pl1.setCurrent(10);
        test.add(new TestCase()
            .test  ((pl1.getCurrentAudioFile() == null) + "")
            .expect("true")
            .name  ("invalid index"));

        PlayList pl2 = new PlayList();
        TaggedFile tf = new TaggedFile("audiofiles/Eisbach Deep Snow.ogg");
        pl2.add(new TaggedFile("audiofiles/Rock 812.mp3"));
        pl2.add(tf);
        pl2.setCurrent(1);
        test.add(new TestCase()
            .test  (pl2.getCurrentAudioFile().toString())
            .expect(tf.toString())
            .name  ("current audiofile"));

        test.display();
    }

    public static void test_changeCurrent() {
        Test test = new Test("PlayList # changeCurrent ()");

        PlayList pl = new PlayList();
        pl.add(new TaggedFile("audiofiles/Eisbach Deep Snow.ogg"));
        pl.add(new TaggedFile("audiofiles/tanom p2 journey.mp3"));
        pl.add(new TaggedFile("audiofiles/Rock 812.mp3"));
        pl.setCurrent(0);

        test.add(new TestCase()
            .test  (pl.getCurrent() + "")
            .expect("0")
            .name  ("current = 0"));
        pl.changeCurrent();
        test.add(new TestCase()
            .test  (pl.getCurrent() + "")
            .expect("1")
            .name  ("current = 1"));
        pl.changeCurrent();
        test.add(new TestCase()
            .test  (pl.getCurrent() + "")
            .expect("2")
            .name  ("current = 2"));
        pl.changeCurrent();
        test.add(new TestCase()
            .test  (pl.getCurrent() + "")
            .expect("0")
            .name  ("current = 0"));

        test.display();
    }

    public static void test_saveAsM3U() {
        PlayList pl = new PlayList();
        pl.add(new TaggedFile("audiofiles/Eisbach Deep Snow.ogg"));
        pl.add(new TaggedFile("audiofiles/tanom p2 journey.mp3"));
        pl.add(new TaggedFile("audiofiles/Rock 812.mp3"));
        pl.saveAsM3U("playlist.m3u");
    }
}

