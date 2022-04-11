import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
    public static void main(String[] args) {
        // emulate a horrible operating system:
        //System.getProperties().setProperty("file.separator", "\\");
        //System.getProperties().setProperty("os.name", "windows");

        // own tests
        UTestAudioFile.test_getPathname();
        UTestAudioFile.test_getFilename();
        UTestAudioFile.test_getAuthor();
        UTestAudioFile.test_getTitle();

        // cert tests
        Result result = JUnitCore.runClasses(AttributesTest.class, AudioFileTest.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure);
        if (result.wasSuccessful())
            System.out.println("\u001b[1;36m > JUnit tests successful!\u001b[0m\n");
    }
}

