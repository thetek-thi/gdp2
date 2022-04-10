public class UTestAudioFile {
    public static void test_getPathname() {
        char sep = System.getProperty("file.separator").charAt(0);

        Test test = new Test("AudioFile # getPathname ()");

        test.add(new TestCase()
            .test  ((new AudioFile("")).getPathname())
            .expect("")
            .name  ("empty string"));
        test.add(new TestCase()
            .test  ((new AudioFile("   \t  \t\t \t")).getPathname())
            .expect("   \t  \t\t \t")
            .name  ("spaces and tabs only"));
        test.add(new TestCase()
            .test  ((new AudioFile("file.mp3")).getPathname())
            .expect("file.mp3")
            .name  ("simple filename"));
        test.add(new TestCase()
            .test  ((new AudioFile("/my-tmp/file.mp3")).getPathname())
            .expect(sep + "my-tmp" + sep + "file.mp3")
            .name  ("path with folders"));
        test.add(new TestCase()
            .test  ((new AudioFile("//my-tmp////part1//file.mp3/")).getPathname())
            .expect(sep + "my-tmp" + sep + "part1" + sep + "file.mp3" + sep)
            .name  ("excessive slashes"));

        if (isAHorribleOperatingSystem())
            test.add(new TestCase()
                .test  ((new AudioFile("d:\\\\\\\\part1///file.mp3")).getPathname())
                .expect("d:" + sep + "part1" + sep + "file.mp3")
                .name  ("path with drive letter (windows)"));
        else
            test.add(new TestCase()
                .test  ((new AudioFile("d:\\\\\\\\part1///file.mp3")).getPathname())
                .expect(sep + "d" + sep + "part1" + sep + "file.mp3")
                .name  ("path with drive letter (linux)"));

        test.display();
    }

    public static void test_getFilename() {
        Test test = new Test("AudioFile # getFilename ()");

        test.add(new TestCase()
            .test  ((new AudioFile("")).getFilename())
            .expect("")
            .name  ("empty string"));
        test.add(new TestCase()
            .test  ((new AudioFile("   \t  \t\t \t")).getFilename())
            .expect("   \t  \t\t \t")
            .name  ("spaces and tabs only"));
        test.add(new TestCase()
            .test  ((new AudioFile("file.mp3")).getFilename())
            .expect("file.mp3")
            .name  ("simple filename"));
        test.add(new TestCase()
            .test  ((new AudioFile("/my-tmp/file.mp3")).getFilename())
            .expect("file.mp3")
            .name  ("path with folders"));
        test.add(new TestCase()
            .test  ((new AudioFile("//my-tmp////part1//file.mp3/")).getFilename())
            .expect("")
            .name  ("excessive slashes"));

        if (isAHorribleOperatingSystem())
            test.add(new TestCase()
                .test  ((new AudioFile("d:\\\\\\\\part1///file.mp3")).getFilename())
                .expect("file.mp3")
                .name  ("path with drive letter (windows)"));
        else
            test.add(new TestCase()
                .test  ((new AudioFile("d:\\\\\\\\part1///file.mp3")).getFilename())
                .expect("file.mp3")
                .name  ("path with drive letter (linux)"));

        test.display();
    }

    public static void test_getAuthor() {
        Test test = new Test("AudioFile # getAuthor ()");

        test.add(new TestCase()
            .test  ((new AudioFile("  Falco  -  Rock me    Amadeus .mp3  ")).getAuthor())
            .expect("Falco")
            .name  ("lots of spaces"));
        test.add(new TestCase()
            .test  ((new AudioFile("Frankie Goes To Hollywood - The Power Of Love.ogg")).getAuthor())
            .expect("Frankie Goes To Hollywood")
            .name  ("<author> - <title> format"));
        test.add(new TestCase()
            .test  ((new AudioFile("audiofile.aux")).getAuthor())
            .expect("")
            .name  ("no author"));
        test.add(new TestCase()
            .test  ((new AudioFile("   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION")).getAuthor())
            .expect("A.U.T.O.R")
            .name  ("lots of dots"));
        test.add(new TestCase()
            .test  ((new AudioFile("Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3")).getAuthor())
            .expect("Hans-Georg Sonstwas")
            .name  ("dashes in author and title"));
        test.add(new TestCase()
            .test  ((new AudioFile(".mp3")).getAuthor())
            .expect("")
            .name  ("only file extension"));
        test.add(new TestCase()
            .test  ((new AudioFile("Falco - Rock me Amadeus.")).getAuthor())
            .expect("Falco")
            .name  ("no file extension after dot"));
        test.add(new TestCase()
            .test  ((new AudioFile("-")).getAuthor())
            .expect("")
            .name  ("single dash"));
        test.add(new TestCase()
            .test  ((new AudioFile(" - ")).getAuthor())
            .expect("")
            .name  ("dash sourrounded by spaces"));

        test.display();
    }

    public static void test_getTitle() {
        Test test = new Test("AudioFile # getTitle ()");

        test.add(new TestCase()
            .test  ((new AudioFile("  Falco  -  Rock me    Amadeus .mp3  ")).getTitle())
            .expect("Rock me    Amadeus")
            .name  ("lots of spaces"));
        test.add(new TestCase()
            .test  ((new AudioFile("Frankie Goes To Hollywood - The Power Of Love.ogg")).getTitle())
            .expect("The Power Of Love")
            .name  ("<author> - <title> format"));
        test.add(new TestCase()
            .test  ((new AudioFile("audiofile.aux")).getTitle())
            .expect("audiofile")
            .name  ("no author"));
        test.add(new TestCase()
            .test  ((new AudioFile("   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION")).getTitle())
            .expect("T.I.T.E.L")
            .name  ("lots of dots"));
        test.add(new TestCase()
            .test  ((new AudioFile("Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3")).getTitle())
            .expect("Blue-eyed boy-friend")
            .name  ("dashes in author and title"));
        test.add(new TestCase()
            .test  ((new AudioFile(".mp3")).getTitle())
            .expect("")
            .name  ("only file extension"));
        test.add(new TestCase()
            .test  ((new AudioFile("Falco - Rock me Amadeus.")).getTitle())
            .expect("Rock me Amadeus")
            .name  ("no file extension after dot"));
        test.add(new TestCase()
            .test  ((new AudioFile("-")).getTitle())
            .expect("-")
            .name  ("single dash"));
        test.add(new TestCase()
            .test  ((new AudioFile(" - ")).getTitle())
            .expect("")
            .name  ("dash sourrounded by spaces"));

        test.display();
    }

    /**
     * isAHorribleOperatingSystem() - returns `true` if the user is running a
     * horrible operating system (i.e. Windows). Hoewever, if the user is
     * running a good operating system (such as Linux), this method will return
     * `false`.
     */
    private static boolean isAHorribleOperatingSystem() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }
}

