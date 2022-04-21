package studiplayer.audio;

public class TestResult {
    private String  expect;
    private String  name;
    private boolean success;
    private String  test;

    public TestResult(boolean success, String name, String test, String expect) {
        this.expect  = expect;
        this.name    = name;
        this.success = success;
        this.test    = test;
    }

    public boolean isSuccessful() {
        return this.success;
    }

    public String getName() {
        return this.name;
    }

    public String getExpected() {
        return this.expect;
    }

    public String getResult() {
        return this.test;
    }
}

