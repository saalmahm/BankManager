# 🏦 Système de Gestion Bancaire

Un système de gestion des comptes bancaires développé en Java 8 avec interface console interactive.

## 📋 Description

Ce projet implémente un système automatisé de gestion bancaire permettant de gérer efficacement les comptes bancaires et leurs opérations (versements, retraits, virements). Le système utilise une architecture en couches pour une meilleure organisation du code.

## ✨ Fonctionnalités

### 🏧 Gestion des Comptes
- **Compte Courant** : Avec découvert autorisé
- **Compte Épargne** : Avec calcul d'intérêts

### 💰 Opérations Bancaires
- ✅ Versement avec source (Salaire, Dépôt, etc.)
- ✅ Retrait avec destination (ATM, Chèque, etc.)
- ✅ Virement entre comptes
- ✅ Consultation de solde
- ✅ Historique des opérations

### 🔒 Validations
- Format de code compte : `CPT-XXXXX` (ex: CPT-12345)
- Montants positifs obligatoires
- Vérification des soldes et découverts
- Gestion des erreurs utilisateur avec try-catch
- Validation robuste des entrées utilisateur
- Récupération automatique en cas d'erreur de saisie

## 🏗️ Architecture

```
src/
├── logic/              # Couche métier
│   ├── Compte.java          # Classe abstraite des comptes
│   ├── CompteCourant.java   # Compte avec découvert
│   ├── CompteEpargne.java   # Compte avec intérêts
│   ├── Operation.java       # Classe abstraite des opérations
│   ├── Versement.java       # Opération de versement
│   └── Retrait.java         # Opération de retrait
├── service/            # Couche service
│   └── BankManager.java     # Gestion des opérations bancaires
├── ui/                 # Couche présentation
│   └── Menu.java            # Interface console interactive
├── util/               # Couche utilitaire
│   └── ValidationUtil.java  # Validation et gestion des entrées
└── Main.java           # Point d'entrée de l'application
```

## 🚀 Installation et Exécution

### Prérequis
- Java 8 ou supérieur
- IDE Java (Eclipse, IntelliJ IDEA, VS Code) ou ligne de commande

### Compilation et Exécution

#### Via IDE
1. Clonez le repository
2. Importez le projet dans votre IDE
3. Exécutez `Main.java`

#### Via Ligne de Commande
```bash
# Cloner le repository
git clone https://github.com/saalmahm/BankManager
cd src

# Compiler
javac logic/*.java service/*.java ui/*.java util/*.java Main.java

# Exécuter
java Main
```

## 📖 Guide d'Utilisation

### Menu Principal
```
==================================================
           SYSTEME BANCAIRE
==================================================
Bienvenue dans votre gestionnaire de comptes bancaires


==================================================
                    MENU PRINCIPAL
==================================================
  [1] Creer un nouveau compte            
  [2] Effectuer un versement             
  [3] Effectuer un retrait               
  [4] Effectuer un virement              
  [5] Consulter le solde d'un compte     
  [6] Consulter l'historique des operations
  [7] Lister tous les comptes            
------------------------------
  [0] Quitter l'application              
==================================================
Votre choix: 
```

### Exemples d'Utilisation

#### 1. Créer un Compte Courant
```
Code: CPT-12345
Type: Compte Courant
Découvert: 500.0
```

#### 2. Créer un Compte Épargne
```
Code: CPT-67890
Type: Compte Épargne
Taux d'intérêt: 0.05 (5%)
```

#### 3. Effectuer un Versement
```
Code du compte: CPT-12345
Montant: 1000.0
Source: Salaire
```

#### 4. Effectuer un Virement
```
Compte source: CPT-12345
Compte destination: CPT-67890
Montant: 300.0
```

## 🧪 Scénarios de Test

### Test Complet Recommandé

1. **Création de comptes**
    - Compte courant : CPT-12345 (découvert 500)
    - Compte épargne : CPT-67890 (taux 5%)

2. **Opérations de base**
    - Versement 1000€ sur CPT-12345
    - Versement 2000€ sur CPT-67890
    - Retrait 1200€ de CPT-12345 (test découvert)
    - Virement 300€ de CPT-67890 vers CPT-12345

3. **Consultations**
    - Vérification des soldes
    - Consultation de l'historique

4. **Tests d'erreur**
    - Code invalide : `CPT-123`
    - Montant négatif : `-100`
    - Compte inexistant : `CPT-99999`

## 🔧 Technologies Utilisées

- **Java 8** : Language principal
- **ArrayList** : Stockage des opérations
- **HashMap** : Recherche rapide des comptes
- **LocalDateTime** : Gestion des dates
- **UUID** : Identifiants uniques des opérations
- **Scanner** : Interface console
- **Try-Catch** : Gestion des exceptions
- **ValidationUtil** : Couche utilitaire pour les validations

## 📈 Améliorations Possibles

- 💾 Persistance des données (fichier/base de données)
- 🔐 Système d'authentification
- 📊 Rapports et statistiques
- 🌐 Interface web
- 📱 Application mobile
- 🔄 Sauvegarde automatique