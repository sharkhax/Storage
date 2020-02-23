package aircraft;

import java.util.*;

public class PassengerPlane extends AbstractAircraft {
    public static final int NUMBER_OF_PILOTS_IN_PASSENGER_PLANE = 1;
    public static final int NUMBER_OF_STEWARDS_IN_PASSENGER_PLANE = 1;
    public static final String PASSENGER_PLANE = "passenger plane";
    public static final int MAX_PASSENGER_CAPACITY_IN_PASSENGER_PLANE = 300;

    private List<Integer> stewardsList;

    public List<Integer> getStewardsList() {
        return stewardsList;
    }

    public void setStewardsList(List<Integer> stewardsList) {
        this.stewardsList = stewardsList;
    }

    @Override
    public String getType() {
        return PASSENGER_PLANE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PassengerPlane that = (PassengerPlane) o;
        return Objects.equals(stewardsList, that.stewardsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stewardsList);
    }

    @Override
    public String toString() {
        return "Passenger plane. " + super.toString() + "\nStewards: " + stewardsList.toString();
    }

    public PassengerPlane(String model, String number, int passengerCapacity, List<Integer> pilotsList, List<Integer> stewardsList, boolean vacant) {
        super(model, number, passengerCapacity, pilotsList, vacant);
        this.stewardsList = stewardsList;
    }
}