package suites;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów dla przypadku użycia "Dodanie filmu do oferty" BEZ mockowania.
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Zadanie: 3 (zestawy testów bazujące na tagach)
 * 
 * Ten zestaw uruchamia testy oznaczone tagiem "dodawanie", ale WYKLUCZA testy z
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
 * - Szybka weryfikacja podstawowej funkcjonalności dodawania filmu
 * - Przydatne do testów regresji przed wdrożeniem
 * - Uruchamiane gdy chcemy sprawdzić pełny przepływ bez izolacji zależności
 */
@Suite
@SuiteDisplayName("Zestaw testów dodawania filmu - BEZ mockowania")
@SelectPackages({ "model", "controller" })
@IncludeTags("dodawanie")
@ExcludeTags("mock")
public class SuiteDodawanieFilmuBezMock {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów (bez mockowania):
     * 
     * Z pakietu model:
     * - TestFilm.java - testy encji Film
     * - TestDAO.java - testy Data Access Object
     * - TestFabrykaStandardowegoFilmu.java - testy fabryki filmów
     * - TestModelDodawanieFilmu.java - testy Model.dodajFilm() bez mockowania
     * 
     * Z pakietu controller:
     * - TestAdminControllerDodawanieFilmu.java - testy kontrolera bez mockowania
     * 
     * WYKLUCZONE (tag "mock"):
     * - TestModelDodawanieFilmuMock.java
     * - TestAdminControllerDodawanieFilmuMock.java
     * - TestDodanieNowegoFilmuMock.java
     */
}
