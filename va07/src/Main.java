import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        // own tests
        UTestWavFile.test_computeDuration();
        UTestTaggedFile.test_timeFormatter();
        UTestTaggedFile.test_readAndStoreTags();
        //UTestTaggedFile.test_play();

        // cert tests
        Result result = JUnitCore.runClasses(AttributesTest.class, AudioFileTest.class, SampledFileTest.class, TaggedFileTest.class, WavFileTest.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure);
        if (result.wasSuccessful())
            System.out.println("\u001b[1;36m > JUnit tests successful!\u001b[0m\n");
    }
}

