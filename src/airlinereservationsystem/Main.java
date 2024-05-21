package airlinereservationsystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Airline Reservation System
 * Author: CSEMN (Mahmoud Nasser)
 */
public class Main {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ProjectDB.initialize(); // Read all saved data
        printHeader();
        mainMenu();
        printFooter();
    }

    private static void printHeader() {
        printLine(49);
        System.out.println("\n|\tEgypt Airline Reservation System\t|");
        printLine(49);
        System.out.print("\n\n");
    }

    private static void printFooter() {
        printLine(41);
        System.out.println("\n|\tCoded By: Mahmoud Nasser\t|");
        System.out.println("|\tClass   : CSE-54\t\t|");
        System.out.println("|\tSection : 4\t\t\t|");
        printLine(41);
        System.out.print("\n\n");
    }

    private static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("=> Main Menu <=");
            System.out.println("1- Passengers Menu");
            System.out.println("2- Flight Management Menu");
            System.out.println("3- Exit System");
            System.out.println("---------------");
            System.out.print("Choice: ");
            short choice = input.nextShort();
            switch (choice) {
                case 1:
                    passengersMenu();
                    break;
                case 2:
                    flightsMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("ERROR: Choice not valid");
            }
        }
    }

    private static void passengersMenu() {
        while (true) {
            System.out.println("=> Passengers Menu <=");
            System.out.println("1- Add Customer");
            System.out.println("2- View All Customers");
            System.out.println("3- Remove Customer");
            System.out.println("4- New Reservation");
            System.out.println("5- View All Reservations");
            System.out.println("6- Cancel Reservation");
            System.out.println("7- Main Menu");
            System.out.println("---------------");
            System.out.print("Choice: ");
            short choice = input.nextShort();
            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewAllCustomers();
                    break;
                case 3:
                    removeCustomer();
                    break;
                case 4:
                    newReservation();
                    break;
                case 5:
                    viewAllReservations();
                    break;
                case 6:
                    cancelReservation();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("ERROR: Choice not valid");
            }
        }
    }

    private static void flightsMenu() {
        while (true) {
            System.out.println("=> Flight Management Menu <=");
            System.out.println("1- Add New Flight Description");
            System.out.println("2- View All Flight Descriptions");
            System.out.println("3- Remove Flight Description");
            System.out.println("4- Schedule New Flight");
            System.out.println("5- View All Scheduled Flights");
            System.out.println("6- Cancel Scheduled Flight");
            System.out.println("7- View Scheduled Flight Passengers");
            System.out.println("8- Main Menu");
            System.out.println("---------------");
            System.out.print("Choice: ");
            short choice = input.nextShort();
            switch (choice) {
                case 1:
                    addFlightDescription();
                    break;
                case 2:
                    viewAllFlightDescriptions();
                    break;
                case 3:
                    removeFlightDescription();
                    break;
                case 4:
                    scheduleNewFlight();
                    break;
                case 5:
                    viewAllScheduledFlights();
                    break;
                case 6:
                    cancelScheduledFlight();
                    break;
                case 7:
                    viewScheduledFlightPassengers();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("ERROR: Choice not valid");
            }
        }
    }

    private static void addCustomer() {
        System.out.println("=> NEW CUSTOMER <=");
        input.nextLine(); // Clear buffer
        System.out.print("Full Name: ");
        String name = input.nextLine();
        System.out.print("Address: ");
        String address = input.nextLine();
        try {
            ProjectDB.add(new Person(name, address));
            System.out.println("Added successfully: " + name + "\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void viewAllCustomers() {
        System.out.println("=> CUSTOMERS TABLE <=");
        Person.showAll();
    }

    private static void removeCustomer() {
        System.out.println("=> CUSTOMERS TABLE <=");
        Person.showAll();
        int index;
        do {
            System.out.print("Customer Index to remove: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.personList.size());
        ProjectDB.personList.remove(index - 1);
        try {
            ProjectDB.updatePersonFile();
            System.out.println("Removed Successfully!\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void newReservation() {
        System.out.println("=> NEW RESERVATION <=");
        Person.showAll();
        int index;
        do {
            System.out.print("Customer Index: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.personList.size());
        Person p = ProjectDB.personList.get(index - 1);
        ScheduledFlight.showAll();
        boolean allGood;
        ScheduledFlight scf;
        do {
            do {
                System.out.print("Flight Index: ");
                index = input.nextInt();
            } while (index < 1 || index > ProjectDB.scheduledFlightList.size());
            scf = ProjectDB.scheduledFlightList.get(index - 1);
            allGood = true;
            if (scf.capacity == Passenger.getSCFlightPassengersCount(scf.flightNumber)) {
                System.out.println("This flight is at maximum capacity, pick another.");
                allGood = false;
            }
        } while (!allGood);
        try {
            ProjectDB.add(new Passenger(p, scf.flightNumber));
            System.out.println("Reservation completed: " + p.name + " (" + scf.from + " -> " + scf.to + ")\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void viewAllReservations() {
        System.out.println("=> RESERVATIONS TABLE <=");
        Passenger.showAll();
    }

    private static void cancelReservation() {
        System.out.println("=> RESERVATIONS TABLE <=");
        Passenger.showAll();
        int index;
        do {
            System.out.print("Passenger Index to cancel trip for: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.passengerList.size());
        ProjectDB.passengerList.remove(index - 1);
        try {
            ProjectDB.updatePassengerFile();
            System.out.println("Reservation canceled successfully!\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void addFlightDescription() {
        System.out.println("=> NEW FLIGHT DESCRIPTION <=");
        input.nextLine(); // Clear buffer
        System.out.print("From: ");
        String from = input.nextLine();
        System.out.print("To: ");
        String to = input.nextLine();
        String depTime, arrTime;
        do {
            System.out.print("Departure time (HH:MM): ");
            depTime = input.nextLine();
        } while (!FlightDescription.checkTime(depTime));
        do {
            System.out.print("Arrival time (HH:MM): ");
            arrTime = input.nextLine();
        } while (!FlightDescription.checkTime(arrTime));
        System.out.print("Capacity: ");
        int cap = input.nextInt();
        try {
            ProjectDB.add(new FlightDescription(from, to, depTime, arrTime, cap));
            System.out.println("Flight Description added successfully: " + from + " -> " + to + "\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void viewAllFlightDescriptions() {
        System.out.println("=> FLIGHT DESCRIPTION TABLE <=");
        FlightDescription.showAll();
    }

    private static void removeFlightDescription() {
        System.out.println("=> FLIGHT DESCRIPTION TABLE <=");
        FlightDescription.showAll();
        int index;
        do {
            System.out.print("Flight description index to remove: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.flightDescList.size());
        ProjectDB.flightDescList.remove(index - 1);
        try {
            ProjectDB.updateFlightDescFile();
            System.out.println("Flight description removed successfully!\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void scheduleNewFlight() {
        System.out.println("=> FLIGHT DESCRIPTION TABLE <=");
        FlightDescription.showAll();
        int index;
        do {
            System.out.print("Flight description index to schedule: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.flightDescList.size());
        FlightDescription fd = ProjectDB.flightDescList.get(index - 1);
        input.nextLine(); // Clear buffer
        String date;
        do {
            System.out.print("Date (YYYY/MM/DD): ");
            date = input.nextLine();
        } while (!ScheduledFlight.checkDateFormat(date));
        try {
            ProjectDB.add(new ScheduledFlight(fd, date));
            System.out.println("Scheduled " + date + " for flight: " + fd.from + " -> " + fd.to + "\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void viewAllScheduledFlights() {
        System.out.println("=> SCHEDULED FLIGHTS TABLE <=");
        ScheduledFlight.showAll();
    }

    private static void cancelScheduledFlight() {
        System.out.println("=> SCHEDULED FLIGHT TABLE <=");
        ScheduledFlight.showAll();
        int index;
        do {
            System.out.print("Scheduled flight index to cancel: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.scheduledFlightList.size());
        int flightNum = ProjectDB.scheduledFlightList.get(index - 1).flightNumber;
        ProjectDB.scheduledFlightList.remove(index - 1);
        try {
            ProjectDB.updateSCFlightFile();
            // Also remove all reservations for this flight
            ArrayList<Passenger> remList = new ArrayList<>();
            for (Passenger p : ProjectDB.passengerList) {
                if (p.flightNumber == flightNum) {
                    remList.add(p);
                }
            }
            ProjectDB.passengerList.removeAll(remList);
            ProjectDB.updatePassengerFile();
            System.out.println("Scheduled flight & reservations canceled successfully!\n");
        } catch (IOException ex) {
            System.out.println("ERROR: File not found!");
        }
    }

    private static void viewScheduledFlightPassengers() {
        System.out.println("=> SCHEDULED FLIGHT TABLE <=");
        ScheduledFlight.showAll();
        int index;
        do {
            System.out.print("Flight index: ");
            index = input.nextInt();
        } while (index < 1 || index > ProjectDB.scheduledFlightList.size());
        int flightNum = ProjectDB.scheduledFlightList.get(index - 1).flightNumber;
        Passenger.showOnlyFlightNo(flightNum);
    }
                }
                                
            
