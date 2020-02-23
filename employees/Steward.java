package employees;

import java.util.Objects;

public class Steward extends AircraftEmployee {
    public static final String STEWARD = "steward";

    private boolean isMainSteward;

    public boolean isMainSteward() {
        return isMainSteward;
    }

    public void setMainSteward(boolean mainSteward) {
        isMainSteward = mainSteward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Steward steward = (Steward) o;
        return isMainSteward == steward.isMainSteward;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isMainSteward);
    }

    @Override
    public String toString() {
        if (isMainSteward)
        return "Main Steward. " + super.toString();
        else return "Steward. " + super.toString();
    }

    public Steward(String name, String surname, String sex, int age, boolean vacant, boolean isMainSteward) {
        super(name, surname, sex, age, vacant);
        this.isMainSteward = isMainSteward;
    }
}
