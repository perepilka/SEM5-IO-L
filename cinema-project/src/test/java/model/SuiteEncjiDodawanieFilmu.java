package model;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów warstwy encji (model) dla przypadku użycia "Dodanie filmu do
 * oferty".
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Zadanie: 3 (zestawy testów)
 * 
 * Ten zestaw uruchamia wszystkie testy z pakietu model oznaczone tagiem
 * "dodawanie".
 * 
 * Ref. z instrukcji: "zestaw testów klas warstwy encji, czyli znajdujących się
 * w jej pakiecie"
 * 
 * Praktyczne zastosowanie: Weryfikacja poprawności wszystkich klas encji
 * zaangażowanych w proces dodawania filmu (Film, FabrykaFilmu, DAO, Model).
 */
@Suite
@SuiteDisplayName("Zestaw testów warstwy encji - Dodanie filmu do oferty")
@SelectPackages("model")
@IncludeTags("dodawanie")
public class SuiteEncjiDodawanieFilmu {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów:
     * - TestFilm.java - testy encji Film
     * - TestDAO.java - testy Data Access Object
     * - TestFabrykaStandardowegoFilmu.java - testy fabryki filmów
     * - TestModelDodawanieFilmu.java - testy Model.dodajFilm() bez mockowania
     * - TestModelDodawanieFilmuMock.java - testy Model.dodajFilm() z mockowaniem
     * 
     * Wszystkie powyższe klasy są oznaczone tagami @Tag("encja")
     * i @Tag("dodawanie")
     */
}
