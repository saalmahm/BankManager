package util;

import java.util.Scanner;

public class ValidationUtil {

    public static boolean isValidAccountCode(String code) {
        if (code == null || code.length() != 9) {
            return false;
        }
        if (!code.startsWith("CPT-")) {
            return false;
        }

        String digits = code.substring(4);
        for (int i = 0; i < digits.length(); i++) {
            if (!Character.isDigit(digits.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPositiveAmount(double amount) {
        return amount > 0;
    }

    public static double readPositiveAmount(Scanner scanner, String message) {
        double amount;
        do {
            System.out.print(message);
            try {
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine();

                    if (amount <= 0) {
                        System.out.println("Le montant doit etre positif!");
                    }
                } else {
                    System.out.println("Veuillez entrer un nombre valide!");
                    scanner.nextLine();
                    amount = -1;
                }
            } catch (Exception e) {
                System.out.println("Erreur de saisie, veuillez reessayer.");
                scanner.nextLine();
                amount = -1;
            }
        } while (amount <= 0);

        return amount;
    }

    public static String readValidAccountCode(Scanner scanner, String message) {
        String code;
        do {
            System.out.print(message);
            try {
                code = scanner.nextLine().trim();
                if (!isValidAccountCode(code)) {
                    System.out.println("Format invalide! Format attendu: CPT-XXXXX (ex: CPT-12345)");
                }
            } catch (Exception e) {
                System.out.println("Erreur de saisie, veuillez réessayer.");
                code = "";
            }
        } while (!isValidAccountCode(code));

        return code;
    }

    public static int readMenuChoice(Scanner scanner, String message) {
        int choice;
        do {
            System.out.print(message);
            try {
                while (!scanner.hasNextInt()) {
                    System.out.print("Veuillez entrer un nombre entier: ");
                    scanner.next();
                }
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (Exception e) {
                System.out.println("Erreur de saisie, veuillez réessayer.");
                scanner.nextLine();
                choice = -1;
            }
        } while (choice == -1);

        return choice;
    }
}