package aircraft;

import java.util.List;
import java.util.Objects;

public class CargoPlane extends AbstractAircraft {
    public static final int NUMBER_OF_PILOTS_IN_CARGO_PLANE = 1;
    public static final String CARGO_PLANE = "cargo plane";
    public static final int MAX_PASSENGER_CAPACITY_IN_CARGO_PLANE = 10;
    public static final double MAX_CARGO_CAPACITY_IN_CARGO_PLANE = 100;

    private double cargoCapacity;

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String getType() {
        return CARGO_PLANE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CargoPlane that = (CargoPlane) o;
        return Double.compare(that.cargoCapacity, cargoCapacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cargoCapacity);
    }

    @Override
    public String toString() {
        return "Cargo plane. " + super.toString() + "\nCargo capacity is " + cargoCapacity + ".";
    }

    public CargoPlane(String model, String number, int passengerCapacity, List<Integer> pilotsList, double cargoCapacity, boolean vacant) {
        super(model, number, passengerCapacity, pilotsList, vacant);
        this.cargoCapacity = cargoCapacity;
    }
}
