package controller;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów warstwy kontroli (controller) dla przypadku użycia "Dodanie
 * filmu do oferty".
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Zadanie: 3 (zestawy testów)
 * 
 * Ten zestaw uruchamia wszystkie testy z pakietu controller oznaczone tagiem
 * "dodawanie".
 * 
 * Ref. z instrukcji: "zestaw testów klas warstwy kontroli, czyli znajdujących
 * się w jej pakiecie"
 * 
 * Praktyczne zastosowanie: Weryfikacja poprawności kontrolerów i strategii
 * zaangażowanych w proces dodawania filmu (AdminController,
 * EdytowanieOfertyKina, DodanieNowegoFilmu).
 */
@Suite
@SuiteDisplayName("Zestaw testów warstwy kontroli - Dodanie filmu do oferty")
@SelectPackages("controller")
@IncludeTags("dodawanie")
public class SuiteKontroliDodawanieFilmu {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów:
     * - TestAdminControllerDodawanieFilmu.java - testy kontrolera bez mockowania
     * - TestAdminControllerDodawanieFilmuMock.java - testy kontrolera z mockowaniem
     * - TestDodanieNowegoFilmuMock.java - testy strategii z mockowaniem
     * 
     * Wszystkie powyższe klasy są oznaczone tagami @Tag("kontroler")
     * i @Tag("dodawanie")
     */
}
