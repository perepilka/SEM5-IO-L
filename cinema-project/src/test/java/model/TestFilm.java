package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Film.
 * Testuje encję danych filmu - podstawową strukturę przechowującą informacje o
 * filmie.
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Warstwa: Encja (model)
 * Zadanie: 1 (testy bez mockowania)
 */
@DisplayName("Testy klasy Film - encja danych")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("dodawanie")
class TestFilm {

    // Dane testowe przygotowywane przed każdym testem
    private Film film;
    private static final String TEST_ID = "F001";
    private static final String TEST_TYTUL = "Matrix";
    private static final String TEST_OPIS = "Cyberpunk thriller";
    private static final int TEST_CZAS = 136;
    private static final String TEST_GATUNEK = "SciFi";
    private static final double TEST_CENA = 28.5;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów klasy Film");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Przygotowanie danych testowych przed każdym testem
        // Tworzymy nowy obiekt Film z określonymi danymi
        film = new Film(TEST_ID, TEST_TYTUL, TEST_OPIS, TEST_CZAS, TEST_GATUNEK, TEST_CENA);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        film = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów klasy Film");
    }

    // ========== TESTY KONSTRUKTORA ==========

    @Test
    @Order(1)
    @DisplayName("Test tworzenia filmu przez konstruktor")
    void testTworzenieFilmu() {
        // Jeśli: Dane do utworzenia filmu zostały przygotowane w setUp()

        // Gdy: Film został utworzony w setUp()

        // Wtedy: Obiekt filmu nie powinien być null i powinien mieć poprawne dane
        assertNotNull(film, "Film nie powinien być null po utworzeniu");
        assertInstanceOf(Film.class, film, "Obiekt powinien być instancją klasy Film");
        assertTrue(film instanceof IFilm, "Film powinien implementować interfejs IFilm");
    }

    // ========== TESTY GETTERÓW ==========

    @Test
    @Order(2)
    @DisplayName("Test metody dajId() - zwracanie identyfikatora")
    void testDajId() {
        // Jeśli: Film został utworzony z ID = "F001"

        // Gdy: Pobieramy ID filmu
        String id = film.dajId();

        // Wtedy: ID powinno być równe wartości podanej w konstruktorze
        assertNotNull(id, "ID nie powinno być null");
        assertEquals(TEST_ID, id, "ID powinno być równe 'F001'");
        assertTrue(id.startsWith("F"), "ID powinno zaczynać się od 'F'");
    }

    @Test
    @Order(3)
    @DisplayName("Test metody dajTytul() - zwracanie tytułu")
    void testDajTytul() {
        // Jeśli: Film został utworzony z tytułem "Matrix"

        // Gdy: Pobieramy tytuł filmu
        String tytul = film.dajTytul();

        // Wtedy: Tytuł powinien być równy wartości podanej w konstruktorze
        assertNotNull(tytul, "Tytuł nie powinien być null");
        assertEquals(TEST_TYTUL, tytul, "Tytuł powinien być równy 'Matrix'");
        assertFalse(tytul.isEmpty(), "Tytuł nie powinien być pusty");
    }

    @Test
    @Order(4)
    @DisplayName("Test metody dajOpis() - zwracanie opisu")
    void testDajOpis() {
        // Jeśli: Film został utworzony z opisem "Cyberpunk thriller"

        // Gdy: Pobieramy opis filmu
        String opis = film.dajOpis();

        // Wtedy: Opis powinien być równy wartości podanej w konstruktorze
        assertNotNull(opis, "Opis nie powinien być null");
        assertEquals(TEST_OPIS, opis, "Opis powinien być równy wartości testowej");
    }

    @Test
    @Order(5)
    @DisplayName("Test metody dajCzasTrwania() - zwracanie czasu trwania")
    void testDajCzasTrwania() {
        // Jeśli: Film został utworzony z czasem trwania 136 minut

        // Gdy: Pobieramy czas trwania
        int czas = film.dajCzasTrwania();

        // Wtedy: Czas powinien być równy wartości podanej w konstruktorze
        assertEquals(TEST_CZAS, czas, "Czas trwania powinien być równy 136");
        assertTrue(czas > 0, "Czas trwania powinien być dodatni");
    }

    @Test
    @Order(6)
    @DisplayName("Test metody dajGatunek() - zwracanie gatunku")
    void testDajGatunek() {
        // Jeśli: Film został utworzony z gatunkiem "SciFi"

        // Gdy: Pobieramy gatunek filmu
        String gatunek = film.dajGatunek();

        // Wtedy: Gatunek powinien być równy wartości podanej w konstruktorze
        assertNotNull(gatunek, "Gatunek nie powinien być null");
        assertEquals(TEST_GATUNEK, gatunek, "Gatunek powinien być równy 'SciFi'");
    }

    @Test
    @Order(7)
    @DisplayName("Test metody dajCeneSeansow() - zwracanie ceny")
    void testDajCeneSeansow() {
        // Jeśli: Film został utworzony z ceną 28.5 PLN

        // Gdy: Pobieramy cenę seansów
        double cena = film.dajCeneSeansow();

        // Wtedy: Cena powinna być równa wartości podanej w konstruktorze
        assertEquals(TEST_CENA, cena, 0.01, "Cena powinna być równa 28.5");
        assertTrue(cena > 0, "Cena powinna być dodatnia");
    }

    // ========== TESTY PARAMETRYZOWANE - @CsvSource ==========

    @ParameterizedTest
    @Order(8)
    @DisplayName("Test tworzenia filmów z różnymi danymi - @CsvSource")
    @CsvSource({
            "F001, Inception, Thriller psychologiczny, 148, Thriller, 32.0",
            "F002, Avatar, Fantasy SciFi, 162, SciFi, 35.0",
            "F003, Titanic, Romans epicki, 195, Dramat, 25.0",
            "F004, Joker, Studium postaci, 122, Dramat, 30.0"
    })
    void testTworzenieFilmowZRoznymiDanymi(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Dane filmu z parametrów CSV

        // Gdy: Tworzymy film z tymi danymi
        Film testFilm = new Film(id, tytul, opis, czas, gatunek, cena);

        // Wtedy: Wszystkie pola powinny być poprawnie ustawione
        assertEquals(id, testFilm.dajId(), "ID powinno być poprawne");
        assertEquals(tytul, testFilm.dajTytul(), "Tytuł powinien być poprawny");
        assertEquals(opis, testFilm.dajOpis(), "Opis powinien być poprawny");
        assertEquals(czas, testFilm.dajCzasTrwania(), "Czas powinien być poprawny");
        assertEquals(gatunek, testFilm.dajGatunek(), "Gatunek powinien być poprawny");
        assertEquals(cena, testFilm.dajCeneSeansow(), 0.01, "Cena powinna być poprawna");
    }

    // ========== TESTY PARAMETRYZOWANE - @ValueSource ==========

    @ParameterizedTest
    @Order(9)
    @DisplayName("Test filmów z różnymi czasami trwania - @ValueSource")
    @ValueSource(ints = { 60, 90, 120, 150, 180, 240 })
    void testFilmyZRoznymCzasemTrwania(int czas) {
        // Jeśli: Różne czasy trwania filmu

        // Gdy: Tworzymy film z określonym czasem
        Film testFilm = new Film("FT", "Test", "Opis", czas, "Gatunek", 20.0);

        // Wtedy: Czas trwania powinien być poprawnie zapisany
        assertEquals(czas, testFilm.dajCzasTrwania(),
                "Czas trwania powinien być równy " + czas);
        assertTrue(testFilm.dajCzasTrwania() > 0, "Czas powinien być dodatni");
    }

    @ParameterizedTest
    @Order(10)
    @DisplayName("Test filmów z różnymi cenami - @ValueSource")
    @ValueSource(doubles = { 10.0, 15.5, 20.0, 25.99, 30.0, 35.5, 50.0 })
    void testFilmyZRoznaCena(double cena) {
        // Jeśli: Różne ceny seansów

        // Gdy: Tworzymy film z określoną ceną
        Film testFilm = new Film("FT", "Test", "Opis", 120, "Gatunek", cena);

        // Wtedy: Cena powinna być poprawnie zapisana
        assertEquals(cena, testFilm.dajCeneSeansow(), 0.01,
                "Cena powinna być równa " + cena);
        assertTrue(testFilm.dajCeneSeansow() >= 10.0, "Cena powinna być >= 10.0");
    }

    // ========== TESTY WARTOŚCI BRZEGOWYCH ==========

    @Test
    @Order(11)
    @DisplayName("Test filmu z minimalnym czasem trwania")
    void testFilmMinimalnyCzas() {
        // Jeśli: Film ma minimalny czas trwania (1 minuta)

        // Gdy: Tworzymy film z czasem 1 minuty
        Film krotki = new Film("FK", "Krótki", "Opis", 1, "Short", 5.0);

        // Wtedy: Film powinien być poprawnie utworzony
        assertEquals(1, krotki.dajCzasTrwania());
        assertNotNull(krotki.dajTytul());
    }

    @Test
    @Order(12)
    @DisplayName("Test filmu z bardzo długim czasem trwania")
    void testFilmDlugiCzas() {
        // Jeśli: Film ma bardzo długi czas trwania

        // Gdy: Tworzymy film z czasem 300 minut
        Film dlugi = new Film("FD", "Długi", "Opis", 300, "Epic", 40.0);

        // Wtedy: Film powinien być poprawnie utworzony
        assertEquals(300, dlugi.dajCzasTrwania());
        assertTrue(dlugi.dajCzasTrwania() > 180, "Film powinien być dłuższy niż 3 godziny");
    }

    // ========== TESTY NIEZMIENNICZOŚCI ==========

    @Test
    @Order(13)
    @DisplayName("Test niezmienniczości danych filmu")
    void testNiezmiennoscDanych() {
        // Jeśli: Film został utworzony z określonymi danymi
        String originalId = film.dajId();
        String originalTytul = film.dajTytul();
        double originalCena = film.dajCeneSeansow();

        // Gdy: Pobieramy dane wielokrotnie
        String id2 = film.dajId();
        String tytul2 = film.dajTytul();
        double cena2 = film.dajCeneSeansow();

        // Wtedy: Dane powinny pozostać niezmienione
        assertSame(originalId, id2, "ID nie powinno się zmienić");
        assertSame(originalTytul, tytul2, "Tytuł nie powinien się zmienić");
        assertEquals(originalCena, cena2, 0.001, "Cena nie powinna się zmienić");
    }
}
