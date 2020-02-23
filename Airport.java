import aircraft.*;
import employees.*;
import utilities.DateCheckResult;

import java.util.*;

import static aircraft.Helicopter.*;
import static aircraft.CargoPlane.*;
import static aircraft.PassengerPlane.*;
import static employees.Dispatcher.*;
import static employees.Pilot.*;
import static employees.Steward.*;
import static java.lang.Character.isDigit;

public class Airport {
    private static final String NEXT_MESSAGE_OF_MAIN_MENU = "Choose what do you want to do:\n0. Exit\n" +
            "1. Add new aircraft\n2. Add new employee\n3. Add new flight\n4. Show flights\nInput number: ";
    private static final String FIRST_MESSAGE_OF_MAIN_MENU = "Welcome to the airport named after Vladislav. " +
            NEXT_MESSAGE_OF_MAIN_MENU;
    private static final String DIVIDING_LINE = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
    private static final String INPUT_IS_CANCELLED_MESSAGE = "Input is canceled, back to the main menu.";
    private static final String FLIGHTS_LIST_IS_EMPTY_MESSAGE = "No flights, list is empty.";
    private static final String WRONG_FORMAT_MESSAGE = "Wrong format (dd/mm/yyyy), retry (0 for exit): ";
    private static final String WRONG_VALUE = "Wrong value, retry (0 for exit): ";
    private static final String PILOT_DOESNT_EXIST_MESSAGE = "The pilot with chosen ID does not exist, retry (0 for exit): ";
    private static final String STEWARD_DOESNT_EXIST_MESSAGE = "The steward with chosen ID does not exist, retry (0 for exit): ";
    private static final String WRONG_PASSENGER_CAPACITY = "Wrong passenger capacity, should be less than (0 for exit): ";
    private static final String WRONG_CARGO_CAPACITY = "Wrong cargo capacity, should be less than (0 for exit): ";
    private static final String MESSAGE_OF_AIRCRAFT_TYPE_MENU = "Choose the type of the aircraft:\n0. Exit\n1. "
            + PASSENGER_PLANE + "\n2. " + HELICOPTER + "\n3. " + CARGO_PLANE + "\nInput number: ";
    private static final String MESSAGE_OF_EMPLOYEE_POSITION_MENU = "Choose the position of the employee:\n0. Exit\n1. "
            + PILOT + "\n2. " + STEWARD + "\n3. " + DISPATCHER + "\nInput number: ";

    private static Map<Integer, AbstractEmployee> employeesList = new HashMap<>();
    static Map<String, AbstractAircraft> planesList = new HashMap<>();
    private static List<Flight> flightsList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean start = false;

        System.out.println(FIRST_MESSAGE_OF_MAIN_MENU);
        while (true) {
            if (start) System.out.println(DIVIDING_LINE + NEXT_MESSAGE_OF_MAIN_MENU);
            start = true;
            byte answer = getAnswer();
            switch (answer) {
                case (1): {
                    addAircraft();
                    break;
                }
                case (2): {
                    addEmployee();
                    break;
                }
                case (3): {
                    addFlight();
                    break;
                }
                case (4): {
                    showFlights();
                    break;
                }
                case (0): {
                    scanner.close();
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Smth wrong, closing program");
                    scanner.close();
                    System.exit(1);
                }
            }
        }
    }

    private static void addEmployee() {
        String position = getPosition();
        if (exitCheck(position)) return;

        int employeeID = inputInt("ID", position, false);
//        if (employeesList.containsKey(employeeID)) {
//            System.out.println("There is the employee with similar ID, do u want to rewrite it (Y/N): ");
//        }
        while (true) {
            if (exitCheck(employeeID)) return;
            if (isNegative(employeeID))
                employeeID = inputInt(null, null, true);
            else break;
        }

        String name = inputString("name", position);
        if (exitCheck(name)) return;

        String surname = inputString("surname", position);
        if (exitCheck(surname)) return;

        String sex = inputString("sex", position);
        while (true) {
            if (exitCheck(sex)) return;
            if (!sex.equals("M") && !sex.equals("F")) {
                System.out.println(WRONG_VALUE);
                sex = scanner.next();
            } else break;
        }

        int age = inputInt("age", position, false);
        while (true) {
            if (exitCheck(age)) return;
            if (age < 18) {
                System.out.println("Incorrect value, employee should be 18+, retry (0 for exit): ");
                age = inputInt(null, null, true);
            } else break;
        }

        if (position.equals(DISPATCHER)) {
            if (!addDispatcher(employeeID, name, surname, sex, age)) return;
        } else if (!addAircraftEmployee(position, employeeID, name, surname, sex, age)) return;
        successfullyAddedMessage(position);
    }

    private static boolean addDispatcher(int employeeID, String name, String surname, String sex, int age) {
        employeesList.put(employeeID, new Dispatcher(name, surname, sex, age));
        return true;
    }

    private static boolean addAircraftEmployee(String position, int employeeID, String name, String surname, String sex, int age) {
//        System.out.println("Input the plane number where the " + position + " will work (0 for exit): ");
//        String planeNumber = scanner.next();
//        while (!planesList.containsKey(planeNumber)) {
//            if (exitCheck(planeNumber)) return false;
//            System.out.println("Chosen plane does not exist, retry (0 for exit): ");
//            planeNumber = scanner.next();
//        }

        if (position.equals(PILOT)) {
            return addPilot(employeeID, name, surname, sex, age);
        } else return addSteward(employeeID, name, surname, sex, age);
    }

    private static boolean addPilot(int employeeID, String name, String surname, String sex, int age) {
        boolean isFirstPilot;
        try {
            isFirstPilot = inputBoolean("the first pilot", false);
        } catch (InputMismatchException e) {
            return false;
        }
        employeesList.put(employeeID, new Pilot(name, surname, sex, age, true, isFirstPilot));
        return true;
    }

    private static boolean addSteward(int employeeID, String name, String surname, String sex, int age) {
        boolean isMainSteward;
        try {
            isMainSteward = inputBoolean("the main steward", false);
        } catch (InputMismatchException e) {
            return false;
        }
        employeesList.put(employeeID, new Steward(name, surname, sex, age, true, isMainSteward));
        return true;
    }

    private static void addAircraft() {
        String type = getType();
        if (exitCheck(type)) return;

        String model = inputString("model", type);
        if (exitCheck(model)) return;

        String number = inputString("number", type);
        while (true) {
            if (exitCheck(number)) return;
            if (planesList.containsKey(number)) {
                System.out.println("This plane is already exists, retry (0 for exit): ");
                number = scanner.next();
            } else break;
        }

        int passengerCapacity = inputInt("passenger capacity", type, false);
        while (true) {
            if (exitCheck(passengerCapacity)) return;
            if (isNegative(passengerCapacity)) {
                passengerCapacity = inputInt(null, null, true);
                continue;
            }
            if (!passengerCapacityCheck(type, passengerCapacity)) {
                passengerCapacity = inputInt(null, null, true);
            } else break;
        }

        List<Integer> pilotsList;
        try {
            if (type.equals(HELICOPTER)) {
                pilotsList = bunchOfPilots(type, NUMBER_OF_PILOTS_IN_HELICOPTER);
            } else if (type.equals(CARGO_PLANE)) {
                pilotsList = bunchOfPilots(type, NUMBER_OF_PILOTS_IN_CARGO_PLANE);
            } else pilotsList = bunchOfPilots(type, NUMBER_OF_PILOTS_IN_PASSENGER_PLANE);
        } catch (Exception e) {
            return;
        }

        if (type.equals(HELICOPTER)) {
            if (!addHelicopter(model, number, passengerCapacity, pilotsList)) return;
        } else if (type.equals(PASSENGER_PLANE)) {
            if (!addPassengerPlane(type, model, number, passengerCapacity, pilotsList)) return;
        } else {
            if (!addCargoPlane(type, model, number, passengerCapacity, pilotsList)) return;
        }
        successfullyAddedMessage(type);
    }

    private static boolean addPassengerPlane(String type, String model, String number, int passengerCapacity, List<Integer> pilotsList) {
        List<Integer> stewardList;
        try {
            stewardList = bunchOfStewards(type, NUMBER_OF_STEWARDS_IN_PASSENGER_PLANE);
        } catch (Exception e) {
            return false;
        }
        planesList.put(number, new PassengerPlane(model, number, passengerCapacity, pilotsList, stewardList, true));
        setPilotNotVacant(pilotsList);
        setStewardNotVacant(stewardList);
        return true;
    }

    private static boolean addCargoPlane(String type, String model, String number, int passengerCapacity, List<Integer> pilotsList) {
        double cargoCapacity = inputDouble("cargo capacity", type, false);
        while (true) {
            if (exitCheck(cargoCapacity)) return false;
            if (isNegative(cargoCapacity)) {
                cargoCapacity = inputDouble(null, null, true);
                continue;
            }
            if (cargoCapacity > MAX_CARGO_CAPACITY_IN_CARGO_PLANE) {
                System.out.println(WRONG_CARGO_CAPACITY + MAX_CARGO_CAPACITY_IN_CARGO_PLANE);
                cargoCapacity = inputDouble(null, null, true);
            } else break;
        }
        planesList.put(number, new CargoPlane(model, number, passengerCapacity, pilotsList, cargoCapacity, true));
        setPilotNotVacant(pilotsList);
        return true;
    }

    private static boolean addHelicopter(String model, String number, int passengerCapacity, List<Integer> pilotsList) {
        planesList.put(number, new Helicopter(model, number, passengerCapacity, pilotsList, true));
        setPilotNotVacant(pilotsList);
        return true;
    }

    private static void addFlight() {
        int takenSeats;
        double cargoLoad;
        String stringDate, number;

        System.out.println("Input number of plane which will fly (0 for exit): ");
        while (true) {
            number = scanner.next();
            if (exitCheck(number)) return;
            if (!planesList.containsKey(number)) {
                System.out.println("There is no plane like this, retry (0 for exit): ");
                if (exitCheck(number)) return;
            } else if (planesList.get(number).isVacant()) {
                planesList.get(number).setVacant(false);
                break;
            } else System.out.println("Chosen plane isn't vacant, retry (0 for exit): ");
        }

        String type = planesList.get(number).getType();

        System.out.println("Input number of taken seats (-1 for exit): ");
        while (true) {
            takenSeats = scanner.nextInt();
            if (takenSeats < -1) {
                System.out.println("Wrong value, shouldn't be negative, retry (-1 for exit): ");
            } else if (takenSeats == -1) {
                System.out.println(INPUT_IS_CANCELLED_MESSAGE);
                return;
            } else if (planesList.get(number).getPassengerCapacity() < takenSeats) {
                System.out.println("Error, the capacity of the plane is less than the number of taken seats. " +
                        "Retry (-1 for exit): ");
            } else break;
        }

        System.out.println("Enter date of the flight in format dd/mm/yyyy (0 for exit): ");
        stringDate = scanner.next();
        if (exitCheck(stringDate)) return;
        DateCheckResult date = dateFormatCheck(stringDate);
        if (!date.isResult()) return;

        if (type.equals(CARGO_PLANE)) {
            System.out.println("Input the cargo load (-1 for exit): ");
            while (true) {
                cargoLoad = scanner.nextDouble();
                if (cargoLoad < -1) {
                    System.out.println("Wrong value, shouldn't be negative, retry (-1 for exit): ");
                } else if (cargoLoad == -1) {
                    System.out.println(INPUT_IS_CANCELLED_MESSAGE);
                    return;
                } else if (((CargoPlane) planesList.get(number)).getCargoCapacity() < cargoLoad) {
                    System.out.println("Error, the cargo capacity of the plane is less than the maximum capacity. " +
                            "Retry (-1 for exit): ");
                } else break;
            }
            flightsList.add(new Flight(number, takenSeats, cargoLoad, date.getDate()));
        } else flightsList.add(new Flight(number, takenSeats, date.getDate()));

        successfullyAddedMessage("flight");
    }

    private static void showFlights() {
        if (flightsList.isEmpty()) {
            System.out.println(FLIGHTS_LIST_IS_EMPTY_MESSAGE);
            return;
        }
        System.out.println("\n");
        Collections.sort(flightsList);
        for (Flight buffer : flightsList) {
            System.out.println(buffer.toString());
        }
    }

    private static <t> boolean exitCheck(t ans) {
        if (ans.equals(0.0) || ans.equals("0") || ans.equals(0)) {
            System.out.println(INPUT_IS_CANCELLED_MESSAGE);
            return true;
        } else return false;
    }

    private static boolean inputBoolean(String value, boolean noMessage) throws InputMismatchException {
        if (!noMessage)
            System.out.println("Is it " + value + "? (Y/N, 0 for exit): ");
        String input = scanner.next();
        while (!input.equals("0") && !input.equals("Y") && !input.equals("N")) {
            System.out.println("Wrong value, retry (Y/N, 0 for exit): ");
            input = scanner.next();
        }
        if (input.equals("Y")) return true;
        else if (input.equals("N")) return false;
        throw new InputMismatchException();
    }

    private static int inputInt(String value, String className, boolean noMessage) {
        boolean isCaught = false;
        int input = -1;
        if (!noMessage)
            System.out.println("Input " + value + " of the " + className + " (0 for exit): ");
        do {
            try {
                input = scanner.nextInt();
                isCaught = false;
            } catch (InputMismatchException e) {
                System.out.println(WRONG_VALUE);
                scanner.next();
                isCaught = true;
            }
        } while (isCaught);
        return input;
    }

    private static double inputDouble(String value, String className, boolean noMessage) {
        double input = -1;
        boolean isCaught = false;
        if (!noMessage)
            System.out.println("Input " + value + " of the " + className + " (0 for exit): ");
        do {
            try {
                input = scanner.nextDouble();
                isCaught = false;
            } catch (InputMismatchException e) {
                System.out.println("Wrong value, retry (0 for exit): ");
                scanner.next();
                isCaught = true;
            }
        } while (isCaught);
        return input;
    }

    private static String inputString(String value, String className) {
        System.out.println("Input " + value + " of the " + className + " (0 for exit): ");
        return scanner.next();
    }

    private static boolean isNegative(Number value) {
        value = value.doubleValue();
        if ((Double) value < 0.0) {
            System.out.println("Wrong value, should be positive, retry (0 for exit): ");
            return true;
        } else return false;
    }

    private static void successfullyAddedMessage(String value) {
        System.out.println("The " + value + " is successfully added.");
    }

    private static DateCheckResult dateFormatCheck(String stringDate) {
        boolean start = false;
        int dd, mm, yyyy;

        while (true) {
            if (start) {
                wrongFormatMessage();
                stringDate = scanner.next();
                if (exitCheck(stringDate)) return new DateCheckResult(false, null);
            }
            start = true;
            while (true) {
                if (stringDate.length() == 10)
                    if (stringDate.charAt(2) == '/' && stringDate.charAt(5) == '/')
                        if (isDigit(stringDate.charAt(0)) && isDigit(stringDate.charAt(1))
                                && isDigit(stringDate.charAt(3)) && isDigit(stringDate.charAt(4))
                                && isDigit(stringDate.charAt(6)) && isDigit(stringDate.charAt(7))
                                && isDigit(stringDate.charAt(8)) && isDigit(stringDate.charAt(9)))
                            break;
                wrongFormatMessage();
                stringDate = scanner.next();
                if (exitCheck(stringDate)) return new DateCheckResult(false, null);
            }

            dd = Integer.parseInt(stringDate.substring(0, 2));
            mm = Integer.parseInt(stringDate.substring(3, 5));
            yyyy = Integer.parseInt(stringDate.substring(6, 10));
            if (dd > 0 && dd <= 31 && mm > 0 && mm <= 12 && yyyy >= 0) {
                if (mm == 4 || mm == 6 || mm == 9 || mm == 11) {
                    if (dd == 31) continue; // ne ok
                } else if (dd > 28 && mm == 2 && yyyy % 4 != 0) {
                    continue; // vse ok
                } else if (dd > 29 && mm == 2) continue; // vse ok
                break;
            }
        }
        return new DateCheckResult(true, new GregorianCalendar(yyyy, mm - 1, dd));
    }

    private static void wrongFormatMessage() {
        System.out.println(WRONG_FORMAT_MESSAGE);
    }

    private static List<Integer> bunchOfPilots(String type, int numberOfPilots) throws Exception {
        List<Integer> pilotsList = new ArrayList<>();
        int pilotID;
        for (int i = 0; i < numberOfPilots; i++) {
            if (numberOfPilots > 1) {
                if (i == 0) pilotID = inputInt("the first pilot's ID", type, false);
                else pilotID = inputInt("the second pilot's ID", type, false);
            } else pilotID = inputInt("pilot's ID", type, false);
            while (true) {
                while (!employeesList.containsKey(pilotID)) {
                    if (exitCheck(pilotID)) throw new Exception();
                    System.out.println(PILOT_DOESNT_EXIST_MESSAGE);
                    pilotID = inputInt(null, null, true);
                }
                if (employeesList.get(pilotID) instanceof Pilot) {
                    if (!((Pilot) employeesList.get(pilotID)).isVacant()) {
                        notVacantMessage(PILOT);
                        pilotID = inputInt(null, null, true);
                    } else if (((Pilot) employeesList.get(pilotID)).isFirstPilot() ^ i == 0) {
                        System.out.println("Wrong pilot's rank, retry (0 for exit): ");
                        pilotID = inputInt(null, null, true);
                    } else break;
                } else {
                    System.out.println(PILOT_DOESNT_EXIST_MESSAGE);
                    pilotID = inputInt(null, null, true);
                }
            }
            pilotsList.add(pilotID);
        }
        return pilotsList;
    }

    private static List<Integer> bunchOfStewards(String type, int numberOfStewards) throws Exception {
        List<Integer> stewardsList = new ArrayList<>();
        int stewardID;
        for (int i = 0; i < numberOfStewards; i++) {
            if (numberOfStewards > 1) {
                if (i == 0) stewardID = inputInt("the main steward's ID", type, false);
                else if (i == 1) stewardID = inputInt("the 2nd steward's ID", type, false);
                else if (i == 2) stewardID = inputInt("the 3rd steward's ID", type, false);
                else stewardID = inputInt("the " + (i + 1) + "th steward's ID", type, false);
            } else stewardID = inputInt("the main steward's ID", type, false);
            while (true) {
                while (!employeesList.containsKey(stewardID)) {
                    if (exitCheck(stewardID)) throw new Exception();
                    System.out.println(STEWARD_DOESNT_EXIST_MESSAGE);
                    stewardID = inputInt(null, null, true);
                }
                if (employeesList.get(stewardID) instanceof Steward) {
                    if (!((Steward) employeesList.get(stewardID)).isVacant()) {
                        notVacantMessage(STEWARD);
                        stewardID = inputInt(null, null, true);
                    } else if (((Steward) employeesList.get(stewardID)).isMainSteward() ^ i == 0) {
                        System.out.println("Wrong steward's rank, retry (0 for exit): ");
                        stewardID = inputInt(null, null, true);
                    } else break;
                } else {
                    System.out.println(STEWARD_DOESNT_EXIST_MESSAGE);
                    stewardID = inputInt(null, null, true);
                }
            }
            stewardsList.add(stewardID);
        }
        return stewardsList;
    }

    private static boolean passengerCapacityCheck(String type, int passengerCapacity) {
        if (type.equals(HELICOPTER)) {
            if (passengerCapacity > MAX_PASSENGER_CAPACITY_IN_HELICOPTER) {
                System.out.println(WRONG_PASSENGER_CAPACITY + MAX_PASSENGER_CAPACITY_IN_HELICOPTER);
                return false;
            } else return true;
        } else if (type.equals(PASSENGER_PLANE)) {
            if (passengerCapacity > MAX_PASSENGER_CAPACITY_IN_PASSENGER_PLANE) {
                System.out.println(WRONG_PASSENGER_CAPACITY + MAX_PASSENGER_CAPACITY_IN_PASSENGER_PLANE);
                return false;
            } else return true;
        } else {
            if (passengerCapacity > MAX_PASSENGER_CAPACITY_IN_CARGO_PLANE) {
                System.out.println(WRONG_PASSENGER_CAPACITY + MAX_PASSENGER_CAPACITY_IN_CARGO_PLANE);
                return false;
            } else return true;
        }
    }

    private static byte getAnswer() {
        byte answer = -1;
        boolean isCaught;

        do
            try {
                isCaught = false;
                answer = scanner.nextByte();
                while (!rangeCheck(answer, 0, 4)) answer = scanner.nextByte();
            } catch (InputMismatchException e) {
                System.out.println("Input correct number (0-4): ");
                isCaught = true;
            }
        while (isCaught);
        return answer;
    }

    private static String getType() {
        byte answer = -1;
        boolean isCaught;

        System.out.println(MESSAGE_OF_AIRCRAFT_TYPE_MENU);
        do
            try {
                isCaught = false;
                answer = scanner.nextByte();
                while (!rangeCheck(answer, 0, 3)) answer = scanner.nextByte();
            } catch (InputMismatchException e) {
                System.out.println("Input correct number (0-3): ");
                isCaught = true;
            }
        while (isCaught);
        if (exitCheck(answer)) return "0";
        switch (answer) {
            case (1): {
                return PASSENGER_PLANE;
            }
            case (2): {
                return HELICOPTER;
            }
            case (3): {
                return CARGO_PLANE;
            }
        }
        return "0";
    }

    private static String getPosition() {
        byte answer = -1;
        boolean isCaught;

        System.out.println(MESSAGE_OF_EMPLOYEE_POSITION_MENU);
        do
            try {
                isCaught = false;
                answer = scanner.nextByte();
                while (!rangeCheck(answer, 0, 3)) answer = scanner.nextByte();
            } catch (InputMismatchException e) {
                System.out.println("Input correct number (0-3): ");
                isCaught = true;
            }
        while (isCaught);
        if (exitCheck(answer)) return "0";
        switch (answer) {
            case (1): {
                return PILOT;
            }
            case (2): {
                return STEWARD;
            }
            case (3): {
                return DISPATCHER;
            }
        }
        return "0";
    }

    private static boolean rangeCheck(Number value, Number firstValue, Number secondValue) {
        value = value.intValue();
        firstValue = firstValue.intValue();
        secondValue = secondValue.intValue();
        if ((Integer) value > (Integer) secondValue || (Integer) value < (Integer) firstValue) {
            System.out.println("Input correct number (" + firstValue + "-" + secondValue + "): ");
            return false;
        } else return true;
    }

    private static void setPilotNotVacant(List<Integer> pilotsList) {
        for (Integer buffer : pilotsList) {
            ((Pilot) employeesList.get(buffer)).setVacant(false);
        }
    }

    private static void setStewardNotVacant(List<Integer> stewardList) {
        for (Integer buffer : stewardList) {
            ((Steward) employeesList.get(buffer)).setVacant(false);
        }
    }

    private static void notVacantMessage(String value) {
        System.out.println("Chosen " + value + " is not vacant, retry (0 for exit): ");
    }
}