package suites;

import org.junit.platform.suite.api.*;

/**
 * Zestaw testów dla przypadku użycia "Dodanie filmu do oferty" - WSZYSTKIE
 * testy.
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Zadanie: 3 (zestawy testów)
 * 
 * Ten zestaw uruchamia WSZYSTKIE testy związane z dodawaniem filmu,
 * używając tagu "dodawanie" do ich wyboru.
 * 
 * Ref. z instrukcji: "adnotacje @IncludeTags w JUnit6"
 * 
 * Praktyczne zastosowanie:
 * - Pełna weryfikacja przypadku użycia "Dodanie filmu do oferty"
 * - Kompletny zestaw testów do uruchomienia przed release'em
 * - Zawiera zarówno testy jednostkowe jak i testy z mockowaniem
 */
@Suite
@SuiteDisplayName("Zestaw WSZYSTKICH testów - Dodanie filmu do oferty")
@SelectPackages({ "model", "controller" })
@IncludeTags("dodawanie")
public class SuiteDodawanieFilmuWszystkie {
    // Klasa zestawu testów - testy są wybierane przez @IncludeTags

    /*
     * Ten zestaw zawiera WSZYSTKIE testy dla przypadku użycia
     * "Dodanie filmu do oferty":
     * 
     * ZADANIE 1 (bez mockowania) - oznaczone @Tag("dodawanie"):
     * - model.TestFilm - testy encji danych filmu
     * - model.TestDAO - testy warstwy dostępu do danych
     * - model.TestFabrykaStandardowegoFilmu - testy fabryki tworzenia filmów
     * - model.TestModelDodawanieFilmu - testy fasady Model
     * - controller.TestAdminControllerDodawanieFilmu - testy kontrolera admin
     * 
     * ZADANIE 2 (z mockowaniem) - oznaczone @Tag("dodawanie") i @Tag("mock"):
     * - model.TestModelDodawanieFilmuMock - testy Model z @Mock IDAO
     * - controller.TestAdminControllerDodawanieFilmuMock - testy z @Mock IModel
     * - controller.TestDodanieNowegoFilmuMock - testy strategii z @Mock IModel
     * 
     * Łącznie: 8 klas testowych, ~80+ testów
     */
}
