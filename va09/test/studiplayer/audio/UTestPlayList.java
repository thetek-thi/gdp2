package studiplayer.audio;

public class UTestPlayList {
    public static void test_sort() {
        Test test = new Test("PlayList # sort ()");

        PlayList p = new PlayList();
        try {
            p.add(new TaggedFile("audiofiles/Eisbach Deep Snow.ogg"));
            p.add(new WavFile("audiofiles/wellenmeister - tranquility.wav"));
            p.add(new TaggedFile("audiofiles/wellenmeister_awakening.ogg"));
            p.add(new TaggedFile("audiofiles/tanom p2 journey.mp3"));
            p.add(new TaggedFile("audiofiles/Rock 812.mp3"));
        } catch (Exception e) { e.printStackTrace(); }

        p.sort(SortCriterion.TITLE);
        String expect =
            "Eisbach - Deep Snow - The Sea, the Sky - 03:18\n" +
            "Eisbach - Rock 812 - The Sea, the Sky - 05:31\n" +
            "Wellenmeister - TANOM Part I: Awakening - TheAbsoluteNecessityOfMeaning - 05:55\n" +
            "Wellenmeister - TANOM Part II: Journey - TheAbsoluteNecessityOfMeaning - 02:52\n" +
            "wellenmeister - tranquility - 02:21\n";
        String got = "";
        for (AudioFile af : p) got += af.toString() + "\n";

        test.add(new TestCase()
            .test  (got)
            .expect(expect)
            .name  ("sort by title"));

        p.sort(SortCriterion.DURATION);
        expect =
            "wellenmeister - tranquility - 02:21\n" +
            "Wellenmeister - TANOM Part II: Journey - TheAbsoluteNecessityOfMeaning - 02:52\n" +
            "Eisbach - Deep Snow - The Sea, the Sky - 03:18\n" +
            "Eisbach - Rock 812 - The Sea, the Sky - 05:31\n" +
            "Wellenmeister - TANOM Part I: Awakening - TheAbsoluteNecessityOfMeaning - 05:55\n";
        got = "";
        for (AudioFile af : p) got += af.toString() + "\n";

        test.add(new TestCase()
            .test  (got)
            .expect(expect)
            .name  ("sort by duration"));

        test.display();

        // unfortunately, the other ones cannot be sorted reliably. or so i think, at least.
    }
}

