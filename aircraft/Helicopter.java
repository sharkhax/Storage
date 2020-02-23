package aircraft;

import java.util.List;

public class Helicopter extends AbstractAircraft {
    public static final int NUMBER_OF_PILOTS_IN_HELICOPTER = 1;
    public static final String HELICOPTER = "helicopter";
    public static final int MAX_PASSENGER_CAPACITY_IN_HELICOPTER = 10;

    @Override
    public String getType() {
        return HELICOPTER;
    }

    @Override
    public String toString() {
        return "Helicopter. " + super.toString();
    }

    public Helicopter(String model, String number, int passengerCapacity, List<Integer> pilotsList, boolean vacant) {
        super(model, number, passengerCapacity, pilotsList, vacant);
    }
}
