package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Model - operacja dodawania filmu.
 * Testuje pełny przepływ dodawania filmu przez warstwę modelu.
 */
@DisplayName("Testy klasy Model - dodawanie filmu")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("dodawanie")
class TestModelDodawanieFilmu {

    private Model model;
    private DAO dao;
    private Oferta oferta;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów Model - dodawanie filmu");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie systemu przed każdym testem
        dao = new DAO();
        oferta = new Oferta(dao);
        model = new Model(oferta, dao);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        model = null;
        oferta = null;
        dao = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów Model - dodawanie filmu");
    }

    @Test
    @Order(1)
    @DisplayName("Test dodawania filmu przez Model")
    void testDodajFilm() {
        // Jeśli: Dane filmu w formacie CSV
        String daneFilmu = "F001;Avengers;Superbohaterowie ratują świat;150;Akcja;30.0";

        // Gdy: Dodajemy film przez model
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być dodany z komunikatem sukcesu
        assertNotNull(wynik, "Wynik nie powinien być null");
        assertTrue(wynik.contains("pomyslnie"), "Wynik powinien zawierać 'pomyslnie'");
        assertTrue(wynik.contains("ID"), "Wynik powinien zawierać ID");
        assertFalse(wynik.isEmpty(), "Wynik nie powinien być pusty");
    }

    @Test
    @Order(2)
    @DisplayName("Test dodawania filmu i weryfikacja w DAO")
    void testDodajFilmWeryfikacjaDAO() {
        // Jeśli: Dane filmu
        String daneFilmu = "F001;Matrix;Cyberpunk thriller;136;SciFi;28.5";

        // Gdy: Dodajemy film
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Film powinien istnieć w DAO
        assertTrue(wynik.contains("F001"), "Wynik powinien zawierać ID F001");
        String zapisanyFilm = dao.znajdzFilm("F001");
        assertNotNull(zapisanyFilm, "Film powinien być zapisany w DAO");
        assertTrue(zapisanyFilm.contains("Matrix"), "Zapisany film powinien zawierać tytuł");
    }

    @Test
    @Order(3)
    @DisplayName("Test formatu komunikatu zwrotnego")
    void testFormatKomunikatu() {
        // Jeśli: Dane filmu
        String daneFilmu = "F001;Titanic;Romans;195;Dramat;25.0";

        // Gdy: Dodajemy film
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Komunikat powinien mieć odpowiedni format
        assertTrue(wynik.startsWith("Film dodany"), "Komunikat powinien zaczynać się od 'Film dodany'");
        assertTrue(wynik.contains("ID:"), "Komunikat powinien zawierać 'ID:'");
    }

    @Test
    @Order(4)
    @DisplayName("Test dodawania wielu filmów")
    void testDodajWieleFilmow() {
        // Jeśli: Dane kilku filmów
        String film1 = "F001;Film1;Opis1;90;Komedia;20.0";
        String film2 = "F002;Film2;Opis2;120;Dramat;25.0";
        String film3 = "F003;Film3;Opis3;110;Akcja;30.0";

        // Gdy: Dodajemy filmy kolejno
        String wynik1 = model.dodajFilm(film1);
        String wynik2 = model.dodajFilm(film2);
        String wynik3 = model.dodajFilm(film3);

        // Wtedy: Wszystkie filmy powinny być dodane z różnymi ID
        assertTrue(wynik1.contains("F001"));
        assertTrue(wynik2.contains("F002"));
        assertTrue(wynik3.contains("F003"));
        assertNotEquals(wynik1, wynik2, "Wyniki powinny być różne");
        assertNotEquals(wynik2, wynik3, "Wyniki powinny być różne");
    }

    @ParameterizedTest
    @Order(5)
    @DisplayName("Test dodawania filmów z różnymi danymi")
    @CsvSource({
            "F001, Inception, Thriller psychologiczny, 148, Thriller, 32.0",
            "F002, Joker, Studium postaci, 122, Dramat, 30.0",
            "F003, Parasite, Społeczny dramat, 132, Dramat, 28.0"
    })
    void testDodajFilmParametryzowany(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Dane filmu z parametrów
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;

        // Gdy: Dodajemy film
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być dodany
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"), "Wynik powinien zawierać potwierdzenie");
        assertTrue(wynik.contains("ID:"), "Wynik powinien zawierać ID");
    }

    @ParameterizedTest
    @Order(6)
    @DisplayName("Test dodawania filmów z metodą źródłową")
    @MethodSource("dostarczDaneFilmow")
    void testDodajFilmZMetody(String daneFilmu, String oczekiwanyTytul) {
        // Jeśli: Dane filmu z metody źródłowej

        // Gdy: Dodajemy film
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być dodany i dostępny w DAO
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"));

        // Weryfikacja w DAO
        String zapisanyFilm = dao.znajdzFilm("F001");
        assertNotNull(zapisanyFilm, "Film powinien być w DAO");
        assertTrue(zapisanyFilm.contains(oczekiwanyTytul),
                "Film powinien zawierać oczekiwany tytuł");
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> dostarczDaneFilmow() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(
                        "F001;Avatar;Fantasy SciFi;162;SciFi;35.0", "Avatar"),
                org.junit.jupiter.params.provider.Arguments.of(
                        "F002;Gladiator;Historyczny;155;Akcja;27.0", "Gladiator"),
                org.junit.jupiter.params.provider.Arguments.of(
                        "F003;Interstellar;Kosmos;169;SciFi;33.0", "Interstellar"));
    }

    @Test
    @Order(7)
    @DisplayName("Test integracji fabryki z modelem")
    void testIntegracjaFabryki() {
        // Jeśli: Dane filmu wymagające przetworzenia przez fabrykę
        String daneFilmu = "F001;TestFilm;TestOpis;100;TestGatunek;20.0";

        // Gdy: Dodajemy film (co używa fabryki wewnętrznie)
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być utworzony przez fabrykę i zapisany
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"));

        // Weryfikacja że dane zostały przetworzone
        String zapisanyFilm = dao.znajdzFilm("F001");
        assertNotNull(zapisanyFilm);
        assertTrue(zapisanyFilm.contains("TestFilm"));
        assertTrue(zapisanyFilm.contains("20.0"));
    }
}
