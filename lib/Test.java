import java.util.ArrayList;

public class Test {
    private String name;
    private ArrayList<TestCase> tests;

    public Test(String name) {
        this.name = name;
        this.tests = new ArrayList<TestCase>();
    }

    public void add(TestCase testCase) {
        tests.add(testCase);
    }

    public void display() {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        int successCount = 0;

        for (TestCase testCase : this.tests)
            results.add(testCase.run());

        System.out.print("\n \u001b[1;34m" + this.name + "\u001b[0m\n   ");
        for (TestResult res : results) {
            if (res.isSuccessful()) {
                System.out.print("\u001b[42m  \u001b[0m");
                ++successCount;
            } else
                System.out.print("\u001b[41m  \u001b[0m");
        }

        if (successCount == results.size())
            System.out.println("\n \u001b[32mAll tests successful.\u001b[0m\n");
        else
            System.out.println("\n \u001b[31m" + successCount + "/" + results.size() + " tests successful.\u001b[0m\n");

        for (TestResult res : results) {
            if (res.isSuccessful())
                System.out.println(" \u001b[32m+ " + res.getName() + "\u001b[0m");
            else
                System.out.println(" \u001b[31mx " + res.getName() + "\u001b[0m\n"
                    + "    \u001b[33mExpected:\u001b[0m \u001b[90m\"\u001b[0m" + res.getExpected()
                    + "\u001b[90m\"\u001b[0m\n    \u001b[33mGot:      \u001b[0m\u001b[90m\"\u001b[0m" + res.getResult() + "\u001b[90m\"\u001b[0m");
        }

        System.out.println();
    }
}

