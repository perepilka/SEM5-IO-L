package controller;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów warstwy kontroli (controller) dla przypadku użycia
 * "Przeglądanie repertuaru".
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Zadanie: 3 (zestawy testów)
 * 
 * Ten zestaw uruchamia wszystkie testy z pakietu controller oznaczone tagiem
 * "repertuar".
 * 
 * Ref. z instrukcji: "zestaw testów klas warstwy kontroli, czyli znajdujących
 * się w jej pakiecie"
 * 
 * Praktyczne zastosowanie: Weryfikacja poprawności kontrolerów klienckich
 * zaangażowanych w proces przeglądania repertuaru (ClientController).
 */
@Suite
@SuiteDisplayName("Zestaw testów warstwy kontroli - Przeglądanie repertuaru")
@SelectPackages("controller")
@IncludeTags("repertuar")
public class SuiteKontroliPrzegladanieRepertuaru {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów:
     * - TestClientControllerPrzegladanieRepertuaru.java - testy kontrolera bez
     * mockowania
     * - TestClientControllerPrzegladanieRepertuaruMock.java - testy kontrolera z
     * mockowaniem
     * 
     * Wszystkie powyższe klasy są oznaczone tagami @Tag("kontroler")
     * i @Tag("repertuar")
     */
}
