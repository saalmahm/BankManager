package ui;

import service.BankService;
import util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

import model.Operation;

public class ConsoleUI {

    private BankService service = new BankService();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.seedSampleData();
        ui.run();
    }

    private void seedSampleData() {
        // optional: seed two accounts for quick testing
        try {
            service.createCompteCourant("CPT-00001", 500.0, 200.0);
            service.createCompteEpargne("CPT-00002", 1000.0, 0.03);
        } catch (Exception e) {
            // ignore
        }
    }

    private void run() {
        boolean quit = false;
        while (!quit) {
            printMenu();
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1": createCompte(); break;
                    case "2": faireVersement(); break;
                    case "3": faireRetrait(); break;
                    case "4": faireVirement(); break;
                    case "5": consulterSolde(); break;
                    case "6": listerOperations(); break;
                    case "7": service.afficherTousComptes(); break;
                    case "0": quit = true; break;
                    default: System.out.println("Choix invalide."); break;
                }
            } catch (Exception ex) {
                System.out.println("Erreur: " + ex.getMessage());
            }
            System.out.println();
        }
        System.out.println("Au revoir !");
        scanner.close();
    }

    private void printMenu() {
        System.out.println("=== Gestion comptes bancaires ===");
        System.out.println("1) Créer compte");
        System.out.println("2) Versement");
        System.out.println("3) Retrait");
        System.out.println("4) Virement");
        System.out.println("5) Consulter solde");
        System.out.println("6) Lister opérations");
        System.out.println("7) Lister tous les comptes");
        System.out.println("0) Quitter");
        System.out.print("Choix: ");
    }

    private void createCompte() {
        System.out.print("Type (C = courant, E = epargne): ");
        String t = scanner.nextLine().trim().toUpperCase();
        System.out.print("Code (CPT-12345): ");
        String code = scanner.nextLine().trim();
        if (!ValidationUtil.isValidCompteCode(code)) {
            System.out.println("Code invalide.");
            return;
        }
        System.out.print("Solde initial: ");
        double solde = Double.parseDouble(scanner.nextLine().trim());
        if (solde < 0) {
            System.out.println("Solde initial doit être >= 0");
            return;
        }
        if ("C".equals(t)) {
            System.out.print("Découvert autorisé: ");
            double dec = Double.parseDouble(scanner.nextLine().trim());
            service.createCompteCourant(code, solde, dec);
            System.out.println("Compte courant créé.");
        } else if ("E".equals(t)) {
            System.out.print("Taux intérêt (ex: 0.03): ");
            double taux = Double.parseDouble(scanner.nextLine().trim());
            service.createCompteEpargne(code, solde, taux);
            System.out.println("Compte épargne créé.");
        } else {
            System.out.println("Type inconnu.");
        }
    }

    private void faireVersement() {
        System.out.print("Code compte: ");
        String code = scanner.nextLine().trim();
        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Source: ");
        String source = scanner.nextLine().trim();
        service.verser(code, montant, source);
        System.out.println("Versement effectué.");
    }

    private void faireRetrait() {
        System.out.print("Code compte: ");
        String code = scanner.nextLine().trim();
        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Destination: ");
        String dest = scanner.nextLine().trim();
        service.retirer(code, montant, dest);
        System.out.println("Retrait effectué.");
    }

    private void faireVirement() {
        System.out.print("Compte source: ");
        String from = scanner.nextLine().trim();
        System.out.print("Compte destination: ");
        String to = scanner.nextLine().trim();
        System.out.print("Montant: ");
        double montant = Double.parseDouble(scanner.nextLine().trim());
        service.virement(from, to, montant);
        System.out.println("Virement effectué.");
    }

    private void consulterSolde() {
        System.out.print("Code compte: ");
        String code = scanner.nextLine().trim();
        double s = service.consulterSolde(code);
        System.out.println("Solde: " + s);
    }

    private void listerOperations() {
        System.out.print("Code compte: ");
        String code = scanner.nextLine().trim();
        List<Operation> ops = service.listerOperations(code);
        if (ops.isEmpty()) {
            System.out.println("Aucune opération.");
        } else {
            for (Operation op : ops) {
                System.out.println(op);
            }
        }
    }
}
