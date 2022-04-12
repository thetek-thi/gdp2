import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        // own tests
        UTestPlayList.test_getCurrentAudioFile();
        UTestPlayList.test_changeCurrent();
        UTestPlayList.test_saveAsM3U();

        // cert tests
        //Result result = JUnitCore.runClasses(AttributesTest.class, AudioFileTest.class, SampledFileTest.class, TaggedFileTest.class, WavFileTest.class);
        //for (Failure failure : result.getFailures())
        //    System.out.println(failure);
        //if (result.wasSuccessful())
        //    System.out.println("\u001b[1;36m > JUnit tests successful!\u001b[0m\n");
    }
}

