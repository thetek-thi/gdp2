public class TestCase {
    private String expect;
    private String name;
    private String test;

    public TestCase test(String test) {
        this.test = test;
        return this;
    }

    public TestCase expect(String expect) {
        this.expect = expect;
        return this;
    }

    public TestCase name(String name) {
        this.name = name;
        return this;
    }

    public TestResult run() {
        return new TestResult(this.test.equals(this.expect), this.name, this.test, this.expect);
    }
}

