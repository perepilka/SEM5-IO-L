package suites;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów dla przypadku użycia "Przeglądanie repertuaru" BEZ mockowania.
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Zadanie: 3 (zestawy testów bazujące na tagach)
 * 
 * Ten zestaw uruchamia testy oznaczone tagiem "repertuar", ale WYKLUCZA testy z
 * tagiem "mock".
 * 
 * Ref. z instrukcji: "przynajmniej 2 zestawy testów oznaczonych wybranymi
 * tagami
 * i nie oznaczonych innymi wybranymi tagami, które mają jakieś praktyczne
 * zastosowanie"
 * 
 * Praktyczne zastosowanie:
 * - Testy integracyjne bez mockowania - sprawdzają rzeczywistą współpracę
 * komponentów
 * - Szybka weryfikacja podstawowej funkcjonalności przeglądania repertuaru
 * - Przydatne do testów regresji przed wdrożeniem
 * - Uruchamiane gdy chcemy sprawdzić pełny przepływ bez izolacji zależności
 */
@Suite
@SuiteDisplayName("Zestaw testów przeglądania repertuaru - BEZ mockowania")
@SelectPackages({ "model", "controller" })
@IncludeTags("repertuar")
@ExcludeTags("mock")
public class SuitePrzegladanieRepertuaruBezMock {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów (bez mockowania):
     * 
     * Z pakietu model:
     * - TestSeans.java - testy encji Seans
     * - TestDAOSeansy.java - testy operacji DAO związanych z seansami
     * - TestModelPobierzRepertuar.java - testy Model.pobierzRepertuar() bez
     * mockowania
     * 
     * Z pakietu controller:
     * - TestClientControllerPrzegladanieRepertuaru.java - testy kontrolera bez
     * mockowania
     * 
     * WYKLUCZONE (tag "mock"):
     * - TestModelPobierzRepertuarMock.java
     * - TestClientControllerPrzegladanieRepertuaruMock.java
     */
}
