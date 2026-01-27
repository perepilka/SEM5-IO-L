package suites;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów dla przypadku użycia "Przeglądanie repertuaru" - WSZYSTKIE
 * testy.
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Zadanie: 3 (zestawy testów)
 * 
 * Ten zestaw uruchamia WSZYSTKIE testy związane z przeglądaniem repertuaru,
 * używając tagu "repertuar" do ich wyboru.
 * 
 * Ref. z instrukcji: "adnotacje @IncludeTags w JUnit6"
 * 
 * Praktyczne zastosowanie:
 * - Pełna weryfikacja przypadku użycia "Przeglądanie repertuaru"
 * - Kompletny zestaw testów do uruchomienia przed release'em
 * - Zawiera zarówno testy jednostkowe jak i testy z mockowaniem
 */
@Suite
@SuiteDisplayName("Zestaw WSZYSTKICH testów - Przeglądanie repertuaru")
@SelectPackages({ "model", "controller" })
@IncludeTags("repertuar")
public class SuitePrzegladanieRepertuaruWszystkie {
    // Klasa zestawu testów - testy są wybierane przez @IncludeTags

    /*
     * Ten zestaw zawiera WSZYSTKIE testy dla przypadku użycia
     * "Przeglądanie repertuaru":
     * 
     * ZADANIE 1 (bez mockowania) - oznaczone @Tag("repertuar"):
     * - model.TestSeans - testy encji danych seansu
     * - model.TestDAOSeansy - testy warstwy dostępu do danych (operacje seansów)
     * - model.TestModelPobierzRepertuar - testy fasady Model
     * - controller.TestClientControllerPrzegladanieRepertuaru - testy kontrolera
     * klienta
     * 
     * ZADANIE 2 (z mockowaniem) - oznaczone @Tag("repertuar") i @Tag("mock"):
     * - model.TestModelPobierzRepertuarMock - testy Model z @Mock IDAO
     * - controller.TestClientControllerPrzegladanieRepertuaruMock - testy z @Mock
     * IModel
     * 
     * Łącznie: 6 klas testowych, ~90+ testów
     */
}
