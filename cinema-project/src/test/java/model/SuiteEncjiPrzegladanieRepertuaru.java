package model;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów warstwy encji (model) dla przypadku użycia "Przeglądanie
 * repertuaru".
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Zadanie: 3 (zestawy testów)
 * 
 * Ten zestaw uruchamia wszystkie testy z pakietu model oznaczone tagiem
 * "repertuar".
 * 
 * Ref. z instrukcji: "zestaw testów klas warstwy encji, czyli znajdujących się
 * w jej pakiecie"
 * 
 * Praktyczne zastosowanie: Weryfikacja poprawności wszystkich klas encji
 * zaangażowanych w proces przeglądania repertuaru (Seans, DAO, Model).
 */
@Suite
@SuiteDisplayName("Zestaw testów warstwy encji - Przeglądanie repertuaru")
@SelectPackages("model")
@IncludeTags("repertuar")
public class SuiteEncjiPrzegladanieRepertuaru {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów:
     * - TestSeans.java - testy encji Seans
     * - TestDAOSeansy.java - testy operacji DAO związanych z seansami
     * - TestModelPobierzRepertuar.java - testy Model.pobierzRepertuar() bez
     * mockowania
     * - TestModelPobierzRepertuarMock.java - testy Model.pobierzRepertuar() z
     * mockowaniem
     * 
     * Wszystkie powyższe klasy są oznaczone tagami @Tag("encja")
     * i @Tag("repertuar")
     */
}
