package employees;

import utilities.Vacant;

import java.util.Objects;

abstract class AircraftEmployee extends AbstractEmployee implements Vacant {
    private boolean vacant;
    private String planeNumber;

    @Override
    public boolean isVacant() {
        return vacant;
    }

    @Override
    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

    public String getPlaneNumber() {
        return planeNumber;
    }

    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AircraftEmployee that = (AircraftEmployee) o;
        return vacant == that.vacant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vacant);
    }

    @Override
    public String toString() {
        if (vacant)
        return super.toString() + "\nVacant";
        else return super.toString() + "\nNot vacant, plane number is " + planeNumber + ".";
    }

    AircraftEmployee(String name, String surname, String sex, int age, boolean vacant) {
        super(name, surname, sex, age);
        this.vacant = vacant;
    }
}
