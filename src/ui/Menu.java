package ui;

import service.BankManager;
import logic.*;
import util.ValidationUtil;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Menu {
    private BankManager bankManager;
    private Scanner scanner;
    private static final String SEPARATOR = "==================================================";
    private static final String MINI_SEPARATOR = "------------------------------";

    public Menu() {
        this.bankManager = new BankManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        displayWelcomeBanner();

        while (true) {
            try {
                displayMainMenu();
                int choix = lireChoix();

                if (!executeChoice(choix)) {
                    break;
                }

                pauseForUser();
            } catch (Exception e) {
                System.err.println("Erreur inattendue: " + e.getMessage());
                System.out.println("Veuillez reessayer...");
                pauseForUser();
            }
        }

        displayGoodbyeMessage();
    }

    private void displayWelcomeBanner() {
        clearScreen();
        System.out.println(SEPARATOR);
        System.out.println("           SYSTEME BANCAIRE");
        System.out.println(SEPARATOR);
        System.out.println("Bienvenue dans votre gestionnaire de comptes bancaires");
        System.out.println();
    }

    private void displayMainMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("                    MENU PRINCIPAL");
        System.out.println(SEPARATOR);
        System.out.printf("  %-3s %-35s%n", "[1]", "Creer un nouveau compte");
        System.out.printf("  %-3s %-35s%n", "[2]", "Effectuer un versement");
        System.out.printf("  %-3s %-35s%n", "[3]", "Effectuer un retrait");
        System.out.printf("  %-3s %-35s%n", "[4]", "Effectuer un virement");
        System.out.printf("  %-3s %-35s%n", "[5]", "Consulter le solde d'un compte");
        System.out.printf("  %-3s %-35s%n", "[6]", "Consulter l'historique des operations");
        System.out.printf("  %-3s %-35s%n", "[7]", "Lister tous les comptes");
        System.out.println(MINI_SEPARATOR);
        System.out.printf("  %-3s %-35s%n", "[0]", "Quitter l'application");
        System.out.println(SEPARATOR);
        System.out.print("Votre choix: ");
    }

    private boolean executeChoice(int choix) {
        System.out.println(); // Ligne vide pour la lisibilite

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
            case 7:
                listerTousLesComptes();
                break;
            case 0:
                return false;
            default:
                displayError("Choix invalide! Veuillez saisir un numero entre 0 et 8.");
        }
        return true;
    }

    private int lireChoix() {
        return ValidationUtil.readMenuChoice(scanner, "");
    }

    private void creerCompte() {
        displaySectionHeader("CREATION D'UN NOUVEAU COMPTE");

        try {
            String code = ValidationUtil.readValidAccountCode(scanner, "Code du compte (format: CPT-XXXXX): ");

            // Verification si le compte existe deja
            if (bankManager.trouverCompte(code) != null) {
                displayError("Un compte avec ce code existe deja!");
                return;
            }

            displayAccountTypeMenu();
            int type = ValidationUtil.readMenuChoice(scanner, "Votre choix: ");

            switch (type) {
                case 1:
                    creerCompteCourant(code);
                    break;
                case 2:
                    creerCompteEpargne(code);
                    break;
                default:
                    displayError("Type de compte invalide!");
            }
        } catch (Exception e) {
            displayError("Erreur lors de la creation du compte: " + e.getMessage());
        }
    }

    private void displayAccountTypeMenu() {
        System.out.println("\nTypes de compte disponibles:");
        System.out.println("  [1] Compte Courant (avec decouvert autorise)");
        System.out.println("  [2] Compte Epargne (avec taux d'interet)");
    }

    private void creerCompteCourant(String code) {
        double decouvert = ValidationUtil.readPositiveAmount(scanner,
                "Montant du decouvert autorise (MAD): ");

        if (bankManager.creerCompteCourant(code, decouvert)) {
            displaySuccess("Compte courant '" + code + "' cree avec succes!");
            System.out.println("Decouvert autorise: " + decouvert + " MAD");
        } else {
            displayError("Impossible de creer le compte!");
        }
    }

    private void creerCompteEpargne(String code) {
        double taux = ValidationUtil.readInterestRate(scanner,
                "Taux d'interet annuel (ex: 0.05 pour 5% ou '5%'): ");

        if (bankManager.creerCompteEpargne(code, taux)) {
            displaySuccess("Compte epargne '" + code + "' cree avec succes!");
            System.out.println("Taux d'interet: " + (taux * 100) + "%");
        } else {
            displayError("Impossible de creer le compte!");
        }
    }

    private void faireVersement() {
        displaySectionHeader("VERSEMENT SUR COMPTE");

        String code = saisirCodeCompte("Code du compte beneficiaire: ");
        if (code == null) return;

        double montant = ValidationUtil.readPositiveAmount(scanner, "Montant a verser (MAD): ");
        String source = saisirTexte("Source du versement: ");

        if (bankManager.faireVersement(code, montant, source)) {
            Compte compte = bankManager.trouverCompte(code);
            displaySuccess("Versement de " + montant + " MAD effectue avec succes!");
            System.out.println("Nouveau solde: " + String.format("%.2f", compte.getSolde()) + " MAD");
        } else {
            displayError("Impossible d'effectuer le versement!");
        }
    }

    private void faireRetrait() {
        displaySectionHeader("RETRAIT DE COMPTE");

        String code = saisirCodeCompte("Code du compte debiteur: ");
        if (code == null) return;

        // Afficher le solde actuel
        Compte compte = bankManager.trouverCompte(code);
        System.out.println("Solde actuel: " + String.format("%.2f", compte.getSolde()) + " MAD");

        double montant = ValidationUtil.readPositiveAmount(scanner, "Montant a retirer (MAD): ");
        String destination = saisirTexte("Destination du retrait: ");

        if (bankManager.faireRetrait(code, montant, destination)) {
            displaySuccess("Retrait de " + montant + " MAD effectue avec succes!");
            System.out.println("Nouveau solde: " + String.format("%.2f", compte.getSolde()) + " MAD");
        } else {
            displayError("Impossible d'effectuer le retrait! Verifiez le solde disponible.");
        }
    }

    private void faireVirement() {
        displaySectionHeader("VIREMENT ENTRE COMPTES");

        String codeSource = saisirCodeCompte("Code du compte source (debiteur): ");
        if (codeSource == null) return;

        String codeDestination = saisirCodeCompte("Code du compte destination (beneficiaire): ");
        if (codeDestination == null) return;

        if (codeSource.equals(codeDestination)) {
            displayError("Les comptes source et destination doivent etre differents!");
            return;
        }

        // Afficher le solde du compte source
        Compte compteSource = bankManager.trouverCompte(codeSource);
        System.out.println("Solde du compte source: " + String.format("%.2f", compteSource.getSolde()) + " MAD");

        double montant = ValidationUtil.readPositiveAmount(scanner, "Montant a virer (MAD): ");

        if (bankManager.faireVirement(codeSource, codeDestination, montant)) {
            displaySuccess("Virement de " + montant + " MAD effectue avec succes!");
            System.out.println("De: " + codeSource + " vers: " + codeDestination);
        } else {
            displayError("Impossible d'effectuer le virement! Verifiez les soldes et codes de compte.");
        }
    }

    private void voirSolde() {
        displaySectionHeader("CONSULTATION DE SOLDE");

        String code = saisirCodeCompte("Code du compte a consulter: ");
        if (code == null) return;

        Compte compte = bankManager.trouverCompte(code);
        System.out.println(MINI_SEPARATOR);
        compte.afficherDetails();
        System.out.println(MINI_SEPARATOR);
    }

    private void voirOperations() {
        displaySectionHeader("HISTORIQUE DES OPERATIONS");

        String code = saisirCodeCompte("Code du compte: ");
        if (code == null) return;

        Compte compte = bankManager.trouverCompte(code);

        if (compte.getListeOperations().isEmpty()) {
            System.out.println("Aucune operation enregistree pour ce compte.");
            return;
        }

        displayOperationsHistory(compte);
    }

    private void listerTousLesComptes() {
        displaySectionHeader("LISTE DE TOUS LES COMPTES");

        // Cette methode devrait etre ajoutee a BankManager
        System.out.println("Fonctionnalite en cours de developpement...");
        System.out.println("Veuillez utiliser l'option 5 pour consulter un compte specifique.");
    }

    private void displayOperationsHistory(Compte compte) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("       HISTORIQUE - COMPTE " + compte.getCode());
        System.out.println(SEPARATOR);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (int i = 0; i < compte.getListeOperations().size(); i++) {
            Operation op = compte.getListeOperations().get(i);
            System.out.println("\n[" + (i + 1) + "] --------------------");
            System.out.println("Date: " + op.getDate().format(formatter));
            System.out.printf("Montant: %,.2f MAD%n", op.getMontant());

            if (op instanceof Versement) {
                System.out.println("Type: VERSEMENT");
                System.out.println("Source: " + ((Versement) op).getSource());
            } else if (op instanceof Retrait) {
                System.out.println("Type: RETRAIT");
                System.out.println("Destination: " + ((Retrait) op).getDestination());
            }
        }
        System.out.println("\n" + MINI_SEPARATOR);
        System.out.println("Total d'operations: " + compte.getListeOperations().size());
    }

    private String saisirCodeCompte(String message) {
        System.out.print(message);
        String code = scanner.nextLine().trim();

        if (code.isEmpty()) {
            displayError("Le code du compte ne peut pas etre vide!");
            return null;
        }

        Compte compte = bankManager.trouverCompte(code);
        if (compte == null) {
            displayError("Compte '" + code + "' introuvable!");
            return null;
        }

        return code;
    }

    private String saisirTexte(String message) {
        System.out.print(message);
        String texte = scanner.nextLine().trim();

        if (texte.isEmpty()) {
            return "Non specifie";
        }

        return texte;
    }

    private void displaySectionHeader(String title) {
        System.out.println(SEPARATOR);
        System.out.println("  " + title);
        System.out.println(SEPARATOR);
    }

    private void displaySuccess(String message) {
        System.out.println("\n✓ SUCCES: " + message);
    }

    private void displayError(String message) {
        System.out.println("\n✗ ERREUR: " + message);
    }

    private void displayGoodbyeMessage() {
        clearScreen();
        System.out.println(SEPARATOR);
        System.out.println("    Merci d'avoir utilise le Systeme Bancaire!");
        System.out.println("              A bientot!");
        System.out.println(SEPARATOR);
    }

    private void pauseForUser() {
        System.out.println("\nAppuyez sur Entree pour continuer...");
        scanner.nextLine();
    }

    private void clearScreen() {
        // Simulation d'effacement d'ecran (compatible avec la plupart des terminaux)
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }

    private double lireMontant() {
        return ValidationUtil.readPositiveAmount(scanner, "Montant: ");
    }
}