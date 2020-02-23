package employees;

import java.util.Objects;

public class Pilot extends AircraftEmployee {
    public static final String PILOT = "pilot";

    private boolean isFirstPilot;

    public boolean isFirstPilot() {
        return isFirstPilot;
    }

    public void setFirstPilot(boolean firstPilot) {
        isFirstPilot = firstPilot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pilot pilot = (Pilot) o;
        return isFirstPilot == pilot.isFirstPilot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isFirstPilot);
    }

    @Override
    public String toString() {
        if (isFirstPilot)
        return "First pilot. " + super.toString();
        else return "Second pilot. " + super.toString();
    }

    public Pilot(String name, String surname, String sex, int age, boolean vacant, boolean isFirstPilot) {
        super(name, surname, sex, age, vacant);
        this.isFirstPilot = isFirstPilot;
    }
}