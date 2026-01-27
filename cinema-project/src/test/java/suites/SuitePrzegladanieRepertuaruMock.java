package suites;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów dla przypadku użycia "Przeglądanie repertuaru" TYLKO Z
 * mockowaniem.
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Zadanie: 3 (zestawy testów bazujące na tagach)
 * 
 * Ten zestaw uruchamia TYLKO testy oznaczone tagami "repertuar" ORAZ "mock".
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
@SuiteDisplayName("Zestaw testów przeglądania repertuaru - TYLKO z mockowaniem")
@SelectPackages({ "model", "controller" })
@IncludeTags({ "repertuar", "mock" })
public class SuitePrzegladanieRepertuaruMock {
    // Klasa zestawu testów - testy są wybierane automatycznie na podstawie
    // adnotacji

    /*
     * Ten zestaw zawiera następujące klasy testów (tylko z mockowaniem):
     * 
     * Z pakietu model:
     * - TestModelPobierzRepertuarMock.java - testy Model z mockowanym IDAO
     * * Testuje: when/thenReturn, when/thenThrow, InOrder, ArgumentCaptor
     * * Weryfikuje: verify, times, never, atLeast, atMost
     * 
     * Z pakietu controller:
     * - TestClientControllerPrzegladanieRepertuaruMock.java - testy z mockowanym
     * IModel
     * 
     * WYKLUCZONE (brak tagu "mock"):
     * - TestSeans.java
     * - TestDAOSeansy.java
     * - TestModelPobierzRepertuar.java
     * - TestClientControllerPrzegladanieRepertuaru.java
     */
}
