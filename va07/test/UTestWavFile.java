public class UTestWavFile {
    public static void test_computeDuration() {
        Test test = new Test("WavFile . computeDuration ()");
        test.add(new TestCase()
            .test  (WavFile.computeDuration(88200L, 44100.0f) + "")
            .expect("2000000")
            .name  ("wav duration"));
        test.display();
    }
}

