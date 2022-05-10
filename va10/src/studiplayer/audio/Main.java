package studiplayer.audio;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        // cert tests
        Result result = JUnitCore.runClasses(/*studiplayer.cert.AttributesTest.class, studiplayer.cert.AudioFileFactoryTest.class, studiplayer.cert.AudioFileTest.class, studiplayer.cert.ComparatorTest.class, studiplayer.cert.NotPlayableExceptionTest.class,*/
                studiplayer.cert.PlayerTest.class/*, studiplayer.cert.PlayListTest.class, studiplayer.cert.SampledFileTest.class, studiplayer.cert.SortCriterionTest.class, studiplayer.cert.TaggedFileTest.class, studiplayer.cert.WavFileTest.class*/);
        for (Failure failure : result.getFailures())
            System.out.println(failure.getTrace());
        if (result.wasSuccessful())
            System.out.println("\u001b[1;36m > JUnit tests successful!\u001b[0m\n");
    }
}

