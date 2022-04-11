public class UTestTaggedFile {
    public static void test_play() {
        TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");
        tf.play();
    }

    public static void test_timeFormatter() {
        Test test = new Test("TaggedFile . timeFormatter ()");

        test.add(new TestCase()
            .test  (TaggedFile.timeFormatter(305862000L))
            .expect("05:05")
            .name  ("valid time"));

        try {
            TaggedFile.timeFormatter(-1);
            test.add (new TestCase().test("no exception").expect("RuntimeException").name("negative time"));
        } catch (RuntimeException e) {
            test.add (new TestCase().test("RuntimeException").expect("RuntimeException").name("negative time"));
        }

        try {
            TaggedFile.timeFormatter(999999999999L);
            test.add (new TestCase().test("no exception").expect("RuntimeException").name("too big time"));
        } catch (RuntimeException e) {
            test.add (new TestCase().test("RuntimeException").expect("RuntimeException").name("too big time"));
        }

        test.display();
    }

    public static void test_readAndStoreTags() {
        Test test = new Test("TaggedFile # readAndStoreTags ()");

        TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");

        test.add(new TestCase()
            .test  (tf.getAuthor())
            .expect("Eisbach")
            .name  ("author"));
        test.add(new TestCase()
            .test  (tf.getTitle())
            .expect("Rock 812")
            .name  ("title"));
        test.add(new TestCase()
            .test  (tf.getAlbum())
            .expect("The Sea, the Sky")
            .name  ("album"));
        test.add(new TestCase()
            .test  (tf.getFormattedDuration())
            .expect("05:31")
            .name  ("duration"));

        test.display();
    }
}

