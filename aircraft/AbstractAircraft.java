package aircraft;

import utilities.Vacant;
import java.util.List;
import java.util.Objects;

abstract public class AbstractAircraft implements Vacant {
    private String model, number;
    private int passengerCapacity;
    private List<Integer> pilotsList;
    private boolean vacant;

    @Override
    public boolean isVacant() {
        return vacant;
    }

    @Override
    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public List<Integer> getPilotsList() {
        return pilotsList;
    }

    public void setPilotsList(List<Integer> pilotsList) {
        this.pilotsList = pilotsList;
    }

    abstract public String getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAircraft that = (AbstractAircraft) o;
        return passengerCapacity == that.passengerCapacity &&
                vacant == that.vacant &&
                Objects.equals(model, that.model) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, number, passengerCapacity, vacant);
    }

    @Override
    public String toString() {
        return model + " " + number + ", passenger capacity: " + passengerCapacity + ", vacant: " + vacant + ".\nPilots: " + pilotsList.toString();
    }

    AbstractAircraft(String model, String number, int passengerCapacity, List<Integer> pilotsList, boolean vacant) {
        this.model = model;
        this.number = number;
        this.passengerCapacity = passengerCapacity;
        this.pilotsList = pilotsList;
        this.vacant = vacant;
    }
}
