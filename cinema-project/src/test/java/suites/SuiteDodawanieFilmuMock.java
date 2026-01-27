package suites;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów dla przypadku użycia "Dodanie filmu do oferty" TYLKO Z
 * mockowaniem.
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Zadanie: 3 (zestawy testów bazujące na tagach)
 * 
 * Ten zestaw uruchamia TYLKO testy oznaczone tagami "dodawanie" ORAZ "mock".
 * 
 * Ref. z instrukcji: "przynajmniej 2 zestawy testów oznaczonych wybranymi
 * tagami
 * i nie oznaczonych innymi wybranymi tagami, które mają jakieś praktyczne
 * zastosowanie"
 * 
 * Praktyczne zastosowanie:
 * - Szybkie testy jednostkowe z izolacją zależności
 * - Weryfikacja logiki biznesowej bez potrzeby rzeczywistych zależności
 * - Testy uruchamiane podczas CI/CD dla szybkiego feedbacku
 * - Przydatne gdy warstwa DAO lub inne zależności są niedostępne
 * - Testowanie obsługi błędów i edge cases (symulacja wyjątków)
 */
@Suite
@SuiteDisplayName("Zestaw testów dodawania filmu - TYLKO z mockowaniem")
@SelectPackages({ "model", "controller" })
@IncludeTags({ "dodawanie", "mock" })
public class SuiteDodawanieFilmuMock {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów (tylko z mockowaniem):
     * 
     * Z pakietu model:
     * - TestModelDodawanieFilmuMock.java - testy Model z mockowanym DAO
     * * Testuje: when/thenReturn, when/thenThrow, doNothing, doThrow
     * * Weryfikuje: verify, times, never, atLeast, atMost, InOrder
     * 
     * Z pakietu controller:
     * - TestAdminControllerDodawanieFilmuMock.java - testy kontrolera z mockowanym
     * IModel
     * - TestDodanieNowegoFilmuMock.java - testy strategii z mockowanym IModel
     * 
     * WYKLUCZONE (brak tagu "mock"):
     * - TestFilm.java
     * - TestDAO.java
     * - TestFabrykaStandardowegoFilmu.java
     * - TestModelDodawanieFilmu.java
     * - TestAdminControllerDodawanieFilmu.java
     */
}
