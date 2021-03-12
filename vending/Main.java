package vending;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        VendingMachineImpl ven = new VendingMachineImpl();

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to Freightos Vending Machine, What will you be having today?\n");
            System.out.println("The following items are available.\n");
            ven.printItems();
            System.out.println("Enter the product code to get the price: ");
            String code = scanner.next(); // product code
            Item i = ven.detectItem(code);
            System.out.println(ven.selectItemAndGetPrice(i));

            System.out.println("Which slot you want to use?");
            System.out.println("1: Coins slot\n");
            System.out.println("2: Notes slot\n");
            System.out.println("3: Cards slot\n");

            int slot = scanner.nextInt();
            switch (slot) {
            case 1:
                System.out.println("Please insert the amount: ");
                double amount = scanner.nextDouble();
                while (amount != -1) {
                    amount = scanner.nextDouble();
                    Coin c = ven.detectCoin(amount);
                    ven.insertCoin(c);
                }

                System.out.println("Current balance: " + ven.getCurrentBalance());
                System.out.println("Enter the product code: ");
                String buyCode = scanner.next(); // product code
                Item item1 = ven.detectItem(buyCode);
                System.out.println("You bought: " + item1.name() + " For: " + item1.getPrice() + "\n");
                System.out.println("Your change: " + "\n");
                ven.printList(ven.collectItemAndChange().getSecond());
                break;

            case 2:
                System.out.println("Please insert the amount: ");
                double note = scanner.nextDouble();

                Note n = ven.detectNote(note);
                ven.insertNote(n);
                System.out.println("Current balance: " + ven.getCurrentBalance());
                System.out.println("Enter the product code: ");
                String code2 = scanner.next(); // product code
                Item item2 = ven.detectItem(code2);
                System.out.println("You bought: " + item2.name() + " For: " + item2.getPrice() + "\n");
                System.out.println("Your change: " + "\n");
                ven.printList(ven.collectItemAndChange().getSecond());
                break;

            case 3:
                System.out.println("Please insert the card serial: ");
                int serial = scanner.nextInt();
                System.out.println("Please insert the card cvv: ");
                int cvv = scanner.nextInt();

                Card card = new Card(serial, cvv);

                ven.insertCard(card);

                System.out.println("Current balance: " + ven.getCurrentBalance());
                System.out.println("Enter the product code: ");
                String code3 = scanner.next(); // product code
                Item item3 = ven.detectItem(code3);
                System.out.println("You bought: " + item3.name() + " For: " + item3.getPrice() + "\n");
                System.out.println("Your change: " + "\n");
                ven.printList(ven.collectItemAndChange().getSecond());
                break;
            default:
                System.out.println("Please enter a valid input");
            }

        } catch (IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }

    }

}
