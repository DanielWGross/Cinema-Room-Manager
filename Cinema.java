package cinema;

import java.util.Arrays;
import java.util.Scanner;
public class Cinema {


    public static int determineIncome(int rows, int seats) {
        int totalSeats = rows * seats;

        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontHalfPrice = rows / 2 * seats * 10;
            int backHalfPrice =  (rows - (rows / 2))  * seats * 8;
            int totalPrice = frontHalfPrice + backHalfPrice;
            return totalPrice;
        }
    }
    
    public static int determineSeatPrice(int rows, int seats, int chosenRow) {
        int totalSeats = rows * seats;

        // If there are less than 60 total seats OR
        // If there are more than 60 and within the first half of rows
        if (totalSeats <= 60 || chosenRow <= rows / 2) {
            return 10;
        }
            return 8;
    }

    public static void printCinema(String[][] cinema) {
        System.out.println("\nCinema:");
        // Print Header Row
        System.out.print("  ");
        // Assuming all rows have the same number of seats
        // Checking first row as they should all be the same length
        for (int i = 0; i < cinema[0].length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                    // Print Leading Number for Each Row
                    if (j == 0) {
                        System.out.print((i + 1) + " ");
                    }
                    System.out.print(cinema[i][j] + " ");
                }
            System.out.println("");
        }
    }

    public static String[][] createCinema(int rows, int seats) {
        String[][] cinema = new String[rows][seats];
        for (String[] row: cinema) {
            Arrays.fill(row, "S");
        }
        return cinema;
    }

    public static int handlePurchase(int rows, int seats, String[][] cinema) {
        Scanner scanner = new Scanner(System.in);
        boolean validChoice = false;
        int chosenRow;
        int chosenSeat;

        do {
            System.out.println("\nEnter a row number:");
            chosenRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            chosenSeat = scanner.nextInt();

            if (chosenRow > rows || chosenSeat > seats) {
                System.out.println("\nWrong input!");
            } else if ("S".equals(cinema[chosenRow - 1][chosenSeat - 1])) {
                validChoice = true;
            } else {
                System.out.println("\nThat ticket has already been purchased!");
            }
        } while (!validChoice);


        int chosenSeatPrice = determineSeatPrice(rows, seats, chosenRow);
        System.out.println("\nTicket Price: $" + chosenSeatPrice);

        cinema[chosenRow - 1][chosenSeat - 1] = "B";

        return chosenSeatPrice;
    }

    public static void handleStats(int totalSeats, int numPurchased, int totalIncome, int lastPurchasePrice) {
        double percentage = numPurchased == 0 ? 0.00 : 100 * (double) numPurchased / (double) totalSeats;
        System.out.printf("%nNumber of purchased tickets: %d", numPurchased);
        System.out.printf("%nPercentage: %.2f", percentage);
        System.out.print("%");
        System.out.printf("%nCurrent Income: $%d", lastPurchasePrice);
        System.out.printf("%nTotal Income: $%d", totalIncome);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();


        String[][] cinema = createCinema(rows, seats);

        int totalSeats = rows * seats;
        int option;
        int numPurchased = 0;
        int totalIncome = determineIncome(rows, seats);
        int lastPurchasePrice = 0;


        do {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            option = scanner.nextInt();

            switch (option) {
                case 1: {
                    printCinema(cinema);
                    break;
                }
                case 2: {
                    numPurchased++;
                    lastPurchasePrice += handlePurchase(rows, seats, cinema);
                    break;
                }
                case 3: {
                    handleStats(totalSeats, numPurchased, totalIncome, lastPurchasePrice);
                    break;
                }
                default: {
                    return;
                }
            }
        } while (option != 0);
    }
}
