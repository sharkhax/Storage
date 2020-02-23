package employees;

public class Dispatcher extends AbstractEmployee {
    public static final String DISPATCHER = "dispatcher";

    @Override
    public String toString() {
        return "Dispatcher. " + super.toString();
    }

    public Dispatcher(String name, String surname, String sex, int age) {
        super(name, surname, sex, age);
    }
}
