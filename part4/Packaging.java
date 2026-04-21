// Part 4
// Allows user to select which Round Robin queue to run

import java.util.Scanner;

public class Packaging {
    private static schedulerTestRR rr = new schedulerTestRR();
    private static schedulerTestCWRR cwrr = new schedulerTestCWRR();
    private static schedulerTestIWRR iwrr = new schedulerTestIWRR();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the round robin queue you would like to run (1 for RR, 2 for CWRR, 3 for IWRR): ");

        String s = sc.nextLine();
        if (s.equals("1")) rr.run();
        else if (s.equals("2")) cwrr.run();
        else if (s.equals("3")) iwrr.run();
        else System.out.println("Invalid input.");
    }
}
