package studiplayer.audio;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        // own tests
        UTestAudioFileFactory.test_getInstance();

        // cert tests
        //Result result = JUnitCore.runClasses(AttributesTest.class, AudioFileFactoryTest.class, AudioFileTest.class, PlayListTest.class, SampledFileTest.class, TaggedFileTest.class, WavFileTest.class);
        //for (Failure failure : result.getFailures())
        //    System.out.println(failure);
        //if (result.wasSuccessful())
        //    System.out.println("\u001b[1;36m > JUnit tests successful!\u001b[0m\n");
    }
}

