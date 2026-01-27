package controller;

import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy AdminController - operacja dodawania filmu.
 * Testuje pełny przepływ dodawania filmu przez kontroler administratora.
 */
@DisplayName("Testy klasy AdminController - dodawanie filmu")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("kontroler")
@Tag("dodawanie")
class TestAdminControllerDodawanieFilmu {

    private AdminController adminController;
    private Model model;
    private DAO dao;
    private Oferta oferta;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów AdminController - dodawanie filmu");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie pełnego systemu przed każdym testem
        dao = new DAO();
        oferta = new Oferta(dao);
        model = new Model(oferta, dao);
        adminController = new AdminController(model);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        adminController = null;
        model = null;
        oferta = null;
        dao = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów AdminController - dodawanie filmu");
    }

    @Test
    @Order(1)
    @DisplayName("Test dodawania filmu przez AdminController")
    void testDodajFilm() {
        // Jeśli: Dane filmu w formacie CSV
        String daneFilmu = "F001;Avengers;Superbohaterowie;150;Akcja;30.0";

        // Gdy: Dodajemy film przez kontroler
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być dodany pomyślnie
        assertNotNull(wynik, "Wynik nie powinien być null");
        assertTrue(wynik.contains("pomyslnie"), "Wynik powinien zawierać 'pomyslnie'");
        assertTrue(wynik.contains("ID"), "Wynik powinien zawierać ID filmu");
    }

    @Test
    @Order(2)
    @DisplayName("Test dodawania filmu i weryfikacja zapisu")
    void testDodajFilmWeryfikacja() {
        // Jeśli: Dane filmu
        String daneFilmu = "F001;Matrix;Cyberpunk;136;SciFi;28.5";

        // Gdy: Dodajemy film
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być w systemie
        assertTrue(wynik.contains("F001"), "Wynik powinien zawierać ID");

        // Weryfikacja w DAO
        String zapisanyFilm = dao.znajdzFilm("F001");
        assertNotNull(zapisanyFilm, "Film powinien być zapisany w bazie");
        assertTrue(zapisanyFilm.contains("Matrix"), "Film powinien zawierać poprawny tytuł");
    }

    @Test
    @Order(3)
    @DisplayName("Test wzorca Strategy w dodawaniu filmu")
    void testStrategiaEdycjiOferty() {
        // Jeśli: Dane filmu do dodania
        String daneFilmu = "F001;Inception;Thriller;148;Thriller;32.0";

        // Gdy: Dodajemy film (używa strategii DodanieNowegoFilmu)
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Strategia powinna zostać poprawnie wykonana
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"), "Strategia powinna wykonać się pomyślnie");
        assertFalse(wynik.isEmpty(), "Wynik nie powinien być pusty");
    }

    @Test
    @Order(4)
    @DisplayName("Test dodawania wielu filmów sekwencyjnie")
    void testDodajWieleFilmow() {
        // Jeśli: Dane kilku filmów
        String film1 = "F001;Film1;Opis1;90;Komedia;20.0";
        String film2 = "F002;Film2;Opis2;120;Dramat;25.0";
        String film3 = "F003;Film3;Opis3;110;Akcja;30.0";

        // Gdy: Dodajemy filmy kolejno
        String wynik1 = adminController.dodajFilm(film1);
        String wynik2 = adminController.dodajFilm(film2);
        String wynik3 = adminController.dodajFilm(film3);

        // Wtedy: Wszystkie filmy powinny być dodane z unikalnymi ID
        assertTrue(wynik1.contains("F001"), "Pierwszy film powinien mieć ID F001");
        assertTrue(wynik2.contains("F002"), "Drugi film powinien mieć ID F002");
        assertTrue(wynik3.contains("F003"), "Trzeci film powinien mieć ID F003");

        // Weryfikacja w DAO
        assertNotNull(dao.znajdzFilm("F001"));
        assertNotNull(dao.znajdzFilm("F002"));
        assertNotNull(dao.znajdzFilm("F003"));
    }

    @Test
    @Order(5)
    @DisplayName("Test poprawności formatu komunikatu")
    void testFormatKomunikatu() {
        // Jeśli: Dane filmu
        String daneFilmu = "F001;Titanic;Romans;195;Dramat;25.0";

        // Gdy: Dodajemy film
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Komunikat powinien mieć określony format
        assertTrue(wynik.startsWith("Film dodany"),
                "Komunikat powinien zaczynać się od 'Film dodany'");
        assertTrue(wynik.contains("ID:"), "Komunikat powinien zawierać 'ID:'");
        assertTrue(wynik.contains("F"), "Komunikat powinien zawierać prefix ID");
    }

    @ParameterizedTest
    @Order(6)
    @DisplayName("Test dodawania filmów z różnymi danymi")
    @CsvSource({
            "F001, Parasite, Dramat społeczny, 132, Dramat, 28.0",
            "F002, Joker, Psychologiczny, 122, Thriller, 30.0",
            "F003, 1917, Wojenny, 119, Wojenny, 27.5",
            "F004, Dune, Epicka SF, 155, SciFi, 35.0"
    })
    void testDodajFilmParametryzowany(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Dane filmu z parametrów
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;

        // Gdy: Dodajemy film
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być dodany
        assertNotNull(wynik, "Wynik nie powinien być null");
        assertTrue(wynik.contains("pomyslnie"), "Film powinien być dodany pomyślnie");
        assertTrue(wynik.contains("ID:"), "Wynik powinien zawierać ID");
    }

    @ParameterizedTest
    @Order(7)
    @DisplayName("Test dodawania filmów o różnych cenach")
    @ValueSource(doubles = { 10.0, 15.5, 20.0, 25.99, 30.0, 35.5, 40.0 })
    void testDodajFilmRozneCeny(double cena) {
        // Jeśli: Dane filmu z różnymi cenami
        String daneFilmu = "F" + ((int) (cena * 10)) + ";Film;Opis;120;Gatunek;" + cena;

        // Gdy: Dodajemy film
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być dodany niezależnie od ceny
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"));

        // Weryfikacja że cena jest zapisana
        String zapisanyFilm = dao.znajdzFilm("F" + ((int) (cena * 10)));
        assertNotNull(zapisanyFilm);
        assertTrue(zapisanyFilm.contains(String.valueOf(cena)),
                "Zapisany film powinien zawierać cenę");
    }

    @Test
    @Order(8)
    @DisplayName("Test pełnego przepływu dodawania filmu")
    void testPelnyPrzeplyw() {
        // Jeśli: Kompletne dane filmu
        String daneFilmu = "F001;Avatar;Fantasy epicki;162;SciFi;35.0";

        // Gdy: Dodajemy film przez AdminController
        // (co wywołuje EdytowanieOfertyKina -> DodanieNowegoFilmu -> Model -> Fabryka
        // -> DAO)
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Cały przepływ powinien zakończyć się sukcesem
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"));

        // Weryfikacja na poziomie DAO
        String zapisanyFilm = dao.znajdzFilm("F001");
        assertNotNull(zapisanyFilm, "Film powinien być w bazie danych");
        assertTrue(zapisanyFilm.contains("Avatar"), "Film powinien mieć poprawny tytuł");
        assertTrue(zapisanyFilm.contains("35.0"), "Film powinien mieć poprawną cenę");
    }

    @Test
    @Order(9)
    @DisplayName("Test że AdminController nie modyfikuje danych filmu")
    void testNiezmiennoscDanych() {
        // Jeśli: Oryginalne dane filmu
        String oryginalneDane = "F001;Original;Description;100;Genre;20.0";

        // Gdy: Dodajemy film
        String wynik = adminController.dodajFilm(oryginalneDane);

        // Wtedy: Dane w DAO powinny odpowiadać oryginalnym
        String zapisanyFilm = dao.znajdzFilm("F001");
        assertNotNull(zapisanyFilm);
        assertTrue(zapisanyFilm.contains("Original"), "Tytuł nie powinien być zmieniony");
        assertTrue(zapisanyFilm.contains("Description"), "Opis nie powinien być zmieniony");
        assertTrue(zapisanyFilm.contains("100"), "Czas nie powinien być zmieniony");
        assertTrue(zapisanyFilm.contains("20.0"), "Cena nie powinna być zmieniona");
    }
}
