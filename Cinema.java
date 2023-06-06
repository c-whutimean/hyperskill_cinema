package cinema;
import java.util.*;

public class Cinema {
    public int purchasedTickets;
    public float percentage;
    public int currentIncome = 0;
    public int totalIncome;
    public int ticketPrice;
    public int totalSeats;

    static int[] enterSize(Scanner sc) {
        int size[] = new int[2];

        System.out.print("Enter the number of rows:\n> ");
        size[0] = sc.nextInt(); // row
        System.out.print("Enter the number of seats in each row:\n> ");
        size[1] = sc.nextInt(); // col

        return size;
    }

    public static void menu() {
        String options = """
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """;

        System.out.println(options);
        System.out.println();
    }

    public static void fillCinema(char[][] cinema) {
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                cinema[i][j] = 'S';
            }
        }
    }

    public static void cinemaMap(char[][] cinema, int row, int col) {
        System.out.printf("\nCinema:\n%-2s", "");
        for (int i = 1; i <= cinema[0].length; i++) {
            System.out.printf("%-2d", i);
        }
        System.out.println();

        for (int r = 1; r <= row; r++) {
            System.out.printf("%-2d", r);
            for (int c = 1; c <= col; c++) {
                System.out.printf("%-2c", cinema[r - 1][c -1]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static int[] chooseSeat(Scanner sc) {
        int[] seats = new int[2];
        System.out.println("Enter a row number:");
        seats[0] = sc.nextInt();
        System.out.println("Enter a seat number in that row:");
        seats[1] = sc.nextInt();
        System.out.println();
        return seats;
    }

    public void income(int row, int col) {
        if (totalSeats <= 60) {
            ticketPrice = 10;
            totalIncome = totalSeats * ticketPrice;

        } else {
            int     firstHalf = row / 2;
            int     secHalf = row - firstHalf;

            totalIncome = (firstHalf * col) * 10;
            totalIncome += (secHalf * col) * 8;
        }
    }

    public void ticketPrice(int row, int col, int rowWant) {
        if (totalSeats <= 60) {
            ticketPrice = 10;
            totalIncome = totalSeats * ticketPrice;

        } else {
            int     firstHalf = row / 2;
            int     secHalf = row - firstHalf;

            if (rowWant <= firstHalf)
                ticketPrice = 10;
            else
                ticketPrice = 8;

            totalIncome = (firstHalf * col) * 10;
            totalIncome += (secHalf * col) * 8;
        }
        updateStats(ticketPrice);
    }

    public void updateStats(int ticketPrice) {
        currentIncome += ticketPrice;
        percentage = ((float)purchasedTickets / (float) totalSeats) * 100;
    }

    public void showStats() {
        String stats = """
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """.formatted(purchasedTickets, percentage, currentIncome, totalIncome);
        System.out.println();
        System.out.println(stats);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int         row;
        int         col;
        boolean     valid;
        int         choice;
        int         rowWant;
        int         colWant;
        char[][]    cinema;

        valid = true;

        Cinema c = new Cinema();
        int size[] = enterSize(sc);
        row = size[0];
        col = size[1];
        cinema = new char[row][col];
        c.totalSeats = row * col;

        c.income(row, col);
        fillCinema(cinema);             //  FILL CINEMA MAP WITH 'S'

        do {
            System.out.println();
            menu();                         // DISPLAYS MENU TO SELECT FROM
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    cinemaMap(cinema, row, col);      //  PRINTS OUT MAP OF THE CINEMA (lines 23-36)
                    break;
                case 2:
                    boolean invalid = true;

                    do {
                        System.out.println();
                        int[] select = chooseSeat(sc);    //  PROMPTS SEAT SELECTION
                        rowWant = select[0];
                        colWant = select[1];

                        if (rowWant > cinema.length || colWant > cinema[0].length) {
                            System.out.println("Wrong input!\n");
                        } else if (cinema[rowWant - 1][colWant - 1] == 'B') {
                            System.out.println("That ticket has already been purchased!\n");
                        } else {
                            cinema[rowWant - 1][colWant - 1] = 'B';
                            c.purchasedTickets += 1;
                            c.ticketPrice(row, col, rowWant);
                            System.out.println("Ticket price: $" + c.ticketPrice);
                            System.out.println();
                            invalid = false;
                        }
                    } while (invalid);
                    break;
                case 3:
                    c.showStats();
                    System.out.println();
                    break;
                case 0:
                    valid = false;
                    break;
                default:
                    System.out.println("Please choose one of the options listed.");

            }
        } while (valid);
    }
}