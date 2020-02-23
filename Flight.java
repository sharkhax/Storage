import aircraft.CargoPlane;

import java.util.*;

class Flight implements Comparable<Flight> {
    private String number, type;
    private int takenSeats;
    private double cargoLoad;
    private GregorianCalendar date;

    public String getNumber() {
        return number;
    }

    public int getTakenSeats() {
        return takenSeats;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getCargoLoad() {
        return cargoLoad;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCargoLoad(double cargoLoad) {
        this.cargoLoad = cargoLoad;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setTakenSeats(int takenSeats) {
        this.takenSeats = takenSeats;
    }

    Flight(String number, int takenSeats, GregorianCalendar date) {
        this.number = number;
        this.takenSeats = takenSeats;
        this.date = date;
    }

    Flight(String number, int takenSeats, double cargoLoad, GregorianCalendar date) {
        this.number = number;
        this.takenSeats = takenSeats;
        this.cargoLoad = cargoLoad;
        this.date = date;
    }

    @Override
    public int compareTo(Flight f) {
        return this.getDate().compareTo(f.getDate());
    }

    @Override
    public String toString() {
        if (Airport.planesList.get(number).getType().equals(CargoPlane.CARGO_PLANE))
        return "Date: " + date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" +
                date.get(Calendar.YEAR) + ", plane: " + Airport.planesList.get(number).getModel() + ", number " + number +
                ", number of the taken seats: " + takenSeats + ", cargo load: " + cargoLoad + ".";
        else return "Date: " + date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" +
                date.get(Calendar.YEAR) + ", plane: " + Airport.planesList.get(number).getModel() + ", number " + number +
                ", number of the taken seats: " + takenSeats + ".";
    }
}