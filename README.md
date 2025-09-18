# Bank Manager Java

## Description
Application console Java 8 pour gérer les comptes bancaires (courant et épargne) avec opérations de versement, retrait et virement. Les données sont stockées en mémoire jusqu'à la fermeture de l'application.

## Technologies Utilisées
- Java 8
- Collections (ArrayList, HashMap)
- Java Time API (LocalDateTime)
- UUID

## Structure du Projet
- `src/model`        : Classes Compte, CompteCourant, CompteEpargne, Operation, Versement, Retrait
- `src/service`      : BankService (logique métier)
- `src/repository`   : CompteRepository (stockage en mémoire)
- `src/ui`           : ConsoleUI (interface utilisateur)
- `src/util`         : ValidationUtil, Exceptions

## Prérequis
- JDK 8 installé et configuré dans IntelliJ
- CMD ou PowerShell

## Compilation et Exécution
1. Compiler tous les fichiers `.java` :
   ```cmd
   javac -d classes src\ui\ConsoleUI.java src\model\*.java src\service\*.java src\repository\*.java src\util\*.java  

3. Créer le manifest :

   ```cmd
   echo Main-Class: ui.ConsoleUI>manifest.txt
   ```

   > Assurez-vous qu'il y ait une nouvelle ligne à la fin du fichier

4. Créer le JAR :

   ```cmd
   jar cfm bank-manager-java-bf1.jar manifest.txt -C classes .
   ```
4. Lancer l'application :

   ```cmd
   java -jar bank-manager-java-bf1.jar
   ```

## Fonctionnalités

* Créer un compte courant ou épargne
* Effectuer un versement sur un compte
* Effectuer un retrait depuis un compte
* Effectuer un virement entre comptes
* Consulter le solde d’un compte
* Lister toutes les opérations d’un compte
* Afficher tous les comptes existants



## Auteur

* Salma Hamdi

