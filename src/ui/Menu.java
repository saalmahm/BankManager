package ui;

import service.BankManager;
import logic.*;
import util.ValidationUtil;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Menu {
    private BankManager bankManager;
    private Scanner scanner;

    public Menu() {
        this.bankManager = new BankManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== SYSTEME BANCAIRE ===");

        while (true) {
            afficherMenu();
            int choix = lireChoix();

            switch (choix) {
                case 1:
                    creerCompte();
                    break;
                case 2:
                    faireVersement();
                    break;
                case 3:
                    faireRetrait();
                    break;
                case 4:
                    faireVirement();
                    break;
                case 5:
                    voirSolde();
                    break;
                case 6:
                    voirOperations();
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Choix invalide!");
            }

            System.out.println("\nAppuyez sur Entree pour continuer...");
            scanner.nextLine();
        }
    }

    private void afficherMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Creer un compte");
        System.out.println("2. Faire un versement");
        System.out.println("3. Faire un retrait");
        System.out.println("4. Faire un virement");
        System.out.println("5. Voir le solde");
        System.out.println("6. Voir les operations");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    private int lireChoix() {
        return ValidationUtil.readMenuChoice(scanner, "");
    }

    private void creerCompte() {
        System.out.println("\n--- Creer un compte ---");

        try {
            String code = ValidationUtil.readValidAccountCode(scanner, "Code du compte (CPT-XXXXX): ");

            System.out.println("Type de compte:");
            System.out.println("1. Compte Courant");
            System.out.println("2. Compte Epargne");
            int type = ValidationUtil.readMenuChoice(scanner, "Votre choix: ");

            if (type == 1) {
                double decouvert = ValidationUtil.readPositiveAmount(scanner, "Montant du decouvert autorise: ");

                if (bankManager.creerCompteCourant(code, decouvert)) {
                    System.out.println("Compte courant cree avec succes!");
                } else {
                    System.out.println("Erreur: Ce compte existe deja!");
                }

            } else if (type == 2) {
                double taux = ValidationUtil.readInterestRate(scanner, "Taux d interet (ex: 0.05 pour 5% ou tapez 5%): ");

                if (bankManager.creerCompteEpargne(code, taux)) {
                    System.out.println("Compte epargne cree avec succes!");
                } else {
                    System.out.println("Erreur: Ce compte existe deja!");
                }
            } else {
                System.out.println("Type invalide!");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la creation du compte: " + e.getMessage());
        }
    }

    private void faireVersement() {
        System.out.println("\n--- Faire un versement ---");

        System.out.print("Code du compte: ");
        String code = scanner.nextLine();

        System.out.print("Montant a verser: ");
        double montant = lireMontant();

        System.out.print("Source du versement: ");
        String source = scanner.nextLine();

        if (bankManager.faireVersement(code, montant, source)) {
            System.out.println("Versement effectue avec succes!");
            Compte compte = bankManager.trouverCompte(code);
            System.out.println("Nouveau solde: " + compte.getSolde() + " MAD");
        } else {
            System.out.println("Erreur: Compte introuvable ou montant invalide!");
        }
    }

    private void faireRetrait() {
        System.out.println("\n--- Faire un retrait ---");

        System.out.print("Code du compte: ");
        String code = scanner.nextLine();

        System.out.print("Montant a retirer: ");
        double montant = lireMontant();

        System.out.print("Destination du retrait: ");
        String destination = scanner.nextLine();

        if (bankManager.faireRetrait(code, montant, destination)) {
            System.out.println("Retrait effectue avec succes!");
            Compte compte = bankManager.trouverCompte(code);
            System.out.println("Nouveau solde: " + compte.getSolde() + " MAD");
        } else {
            System.out.println("Erreur: Solde insuffisant ou compte introuvable!");
        }
    }

    private void faireVirement() {
        System.out.println("\n--- Faire un virement ---");

        System.out.print("Code du compte source: ");
        String codeSource = scanner.nextLine();

        System.out.print("Code du compte destination: ");
        String codeDestination = scanner.nextLine();

        System.out.print("Montant a virer: ");
        double montant = lireMontant();

        if (bankManager.faireVirement(codeSource, codeDestination, montant)) {
            System.out.println("Virement effectue avec succes!");
        } else {
            System.out.println("Erreur: Solde insuffisant ou compte introuvable!");
        }
    }

    private void voirSolde() {
        System.out.println("\n--- Voir le solde ---");

        System.out.print("Code du compte: ");
        String code = scanner.nextLine();

        Compte compte = bankManager.trouverCompte(code);
        if (compte != null) {
            compte.afficherDetails();
        } else {
            System.out.println("Compte introuvable!");
        }
    }

    private void voirOperations() {
        System.out.println("\n--- Voir les operations ---");

        System.out.print("Code du compte: ");
        String code = scanner.nextLine();

        Compte compte = bankManager.trouverCompte(code);
        if (compte == null) {
            System.out.println("Compte introuvable!");
            return;
        }

        if (compte.getListeOperations().size() == 0) {
            System.out.println("Aucune operation trouvee.");
            return;
        }

        System.out.println("\n=== OPERATIONS DU COMPTE " + code + " ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < compte.getListeOperations().size(); i++) {
            Operation op = compte.getListeOperations().get(i);
            System.out.println("\n--- Operation " + (i + 1) + " ---");
            System.out.println("Date: " + op.getDate().format(formatter));
            System.out.println("Montant: " + op.getMontant() + " MAD");

            if (op instanceof Versement) {
                System.out.println("Type: VERSEMENT");
                System.out.println("Source: " + ((Versement) op).getSource());
            } else if (op instanceof Retrait) {
                System.out.println("Type: RETRAIT");
                System.out.println("Destination: " + ((Retrait) op).getDestination());
            }
        }
    }

    private double lireMontant() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Entrez un montant valide: ");
            scanner.next();
        }
        double montant = scanner.nextDouble();
        scanner.nextLine();

        if (montant <= 0) {
            System.out.println("Le montant doit etre positif!");
            return lireMontant();
        }
        return montant;
    }
}