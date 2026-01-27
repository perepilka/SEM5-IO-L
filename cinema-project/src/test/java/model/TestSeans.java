package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Seans.
 * Testuje encję danych seansu - podstawową strukturę przechowującą informacje o
 * seansie filmowym.
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Warstwa: Encja (model)
 * Zadanie: 1 (testy bez mockowania)
 */
@DisplayName("Testy klasy Seans - encja danych")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("repertuar")
class TestSeans {

    // Dane testowe przygotowywane przed każdym testem
    private Seans seans;
    private Film filmTestowy;
    private static final String TEST_ID = "S001";
    private static final String TEST_DATA = "2024-12-20 18:00";
    private static final String TEST_SALA = "Sala1";
    private static final int TEST_MIEJSCA = 100;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów klasy Seans");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Przygotowanie danych testowych przed każdym testem
        // Tworzymy film testowy potrzebny do seansu
        filmTestowy = new Film("F001", "Matrix", "Cyberpunk thriller", 136, "SciFi", 28.5);
        // Tworzymy nowy obiekt Seans z określonymi danymi
        seans = new Seans(TEST_ID, filmTestowy, TEST_DATA, TEST_SALA, TEST_MIEJSCA);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        seans = null;
        filmTestowy = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów klasy Seans");
    }

    // ========== TESTY KONSTRUKTORA ==========

    @Test
    @Order(1)
    @DisplayName("Test tworzenia seansu przez konstruktor")
    void testTworzenieSeansu() {
        // Jeśli: Dane do utworzenia seansu zostały przygotowane w setUp()

        // Gdy: Seans został utworzony w setUp()

        // Wtedy: Obiekt seansu nie powinien być null i powinien mieć poprawne dane
        assertNotNull(seans, "Seans nie powinien być null po utworzeniu");
        assertInstanceOf(Seans.class, seans, "Obiekt powinien być instancją klasy Seans");
        assertTrue(seans instanceof ISeans, "Seans powinien implementować interfejs ISeans");
    }

    // ========== TESTY GETTERÓW ==========

    @Test
    @Order(2)
    @DisplayName("Test metody dajId() - zwracanie identyfikatora seansu")
    void testDajId() {
        // Jeśli: Seans został utworzony z ID = "S001"

        // Gdy: Pobieramy ID seansu
        String id = seans.dajId();

        // Wtedy: ID powinno być równe wartości podanej w konstruktorze
        assertNotNull(id, "ID nie powinno być null");
        assertEquals(TEST_ID, id, "ID powinno być równe 'S001'");
        assertTrue(id.startsWith("S"), "ID powinno zaczynać się od 'S'");
    }

    @Test
    @Order(3)
    @DisplayName("Test metody dajFilm() - zwracanie filmu seansu")
    void testDajFilm() {
        // Jeśli: Seans został utworzony z filmem "Matrix"

        // Gdy: Pobieramy film seansu
        IFilm film = seans.dajFilm();

        // Wtedy: Film powinien być równy filmowi podanemu w konstruktorze
        assertNotNull(film, "Film nie powinien być null");
        assertEquals(filmTestowy, film, "Film powinien być tym samym obiektem");
        assertEquals("Matrix", film.dajTytul(), "Tytuł filmu powinien być 'Matrix'");
    }

    @Test
    @Order(4)
    @DisplayName("Test metody dajDate() - zwracanie daty seansu")
    void testDajDate() {
        // Jeśli: Seans został utworzony z datą "2024-12-20 18:00"

        // Gdy: Pobieramy datę seansu
        String data = seans.dajDate();

        // Wtedy: Data powinna być równa wartości podanej w konstruktorze
        assertNotNull(data, "Data nie powinna być null");
        assertEquals(TEST_DATA, data, "Data powinna być równa wartości testowej");
        assertFalse(data.isEmpty(), "Data nie powinna być pusta");
    }

    @Test
    @Order(5)
    @DisplayName("Test metody dajWolneMiejsca() - początkowa liczba wolnych miejsc")
    void testDajWolneMiejscaPoczatkowo() {
        // Jeśli: Seans został utworzony z 100 miejscami, żadne nie jest zajęte

        // Gdy: Pobieramy wolne miejsca
        int[] wolneMiejsca = seans.dajWolneMiejsca();

        // Wtedy: Wszystkie miejsca powinny być wolne
        assertNotNull(wolneMiejsca, "Tablica wolnych miejsc nie powinna być null");
        assertEquals(TEST_MIEJSCA, wolneMiejsca.length, "Wszystkie 100 miejsc powinno być wolnych");
        assertEquals(1, wolneMiejsca[0], "Pierwsze wolne miejsce powinno mieć numer 1");
        assertEquals(100, wolneMiejsca[99], "Ostatnie wolne miejsce powinno mieć numer 100");
    }

    // ========== TESTY REZERWACJI MIEJSC ==========

    @Test
    @Order(6)
    @DisplayName("Test zarezerwujMiejsce() - udana rezerwacja")
    void testZarezerwujMiejsceUdana() {
        // Jeśli: Miejsce nr 50 jest wolne

        // Gdy: Rezerwujemy miejsce nr 50
        boolean wynik = seans.zarezerwujMiejsce(50);

        // Wtedy: Rezerwacja powinna się udać
        assertTrue(wynik, "Rezerwacja powinna się udać");

        // I liczba wolnych miejsc powinna się zmniejszyć o 1
        int[] wolneMiejsca = seans.dajWolneMiejsca();
        assertEquals(99, wolneMiejsca.length, "Powinno być 99 wolnych miejsc");
    }

    @Test
    @Order(7)
    @DisplayName("Test zarezerwujMiejsce() - próba rezerwacji zajętego miejsca")
    void testZarezerwujMiejsceZajete() {
        // Jeśli: Miejsce nr 25 zostało już zarezerwowane
        seans.zarezerwujMiejsce(25);

        // Gdy: Próbujemy zarezerwować to samo miejsce ponownie
        boolean wynik = seans.zarezerwujMiejsce(25);

        // Wtedy: Rezerwacja powinna się nie udać
        assertFalse(wynik, "Rezerwacja zajętego miejsca powinna się nie udać");
    }

    @Test
    @Order(8)
    @DisplayName("Test zarezerwujMiejsce() - nieprawidłowy numer miejsca")
    void testZarezerwujMiejsceNieprawidlowe() {
        // Jeśli: Seans ma 100 miejsc (numery 1-100)

        // Gdy: Próbujemy zarezerwować miejsce o numerze 0 lub 101
        boolean wynik0 = seans.zarezerwujMiejsce(0);
        boolean wynik101 = seans.zarezerwujMiejsce(101);
        boolean wynikUjemny = seans.zarezerwujMiejsce(-5);

        // Wtedy: Wszystkie rezerwacje powinny się nie udać
        assertFalse(wynik0, "Rezerwacja miejsca 0 powinna się nie udać");
        assertFalse(wynik101, "Rezerwacja miejsca 101 powinna się nie udać");
        assertFalse(wynikUjemny, "Rezerwacja miejsca -5 powinna się nie udać");
    }

    @Test
    @Order(9)
    @DisplayName("Test zwolnijMiejsce() - zwalnianie zarezerwowanego miejsca")
    void testZwolnijMiejsce() {
        // Jeśli: Miejsce nr 30 zostało zarezerwowane
        seans.zarezerwujMiejsce(30);
        int wolnychPoRezerwacji = seans.dajWolneMiejsca().length;

        // Gdy: Zwalniamy miejsce nr 30
        seans.zwolnijMiejsce(30);

        // Wtedy: Miejsce powinno być ponownie dostępne
        int wolnychPoZwolnieniu = seans.dajWolneMiejsca().length;
        assertEquals(wolnychPoRezerwacji + 1, wolnychPoZwolnieniu,
                "Liczba wolnych miejsc powinna wzrosnąć o 1");
    }

    // ========== TESTY PARAMETRYZOWANE - @CsvSource ==========

    @ParameterizedTest
    @Order(10)
    @DisplayName("Test tworzenia seansów z różnymi danymi - @CsvSource")
    @CsvSource({
            "S001, 2024-12-20 18:00, Sala1, 100",
            "S002, 2024-12-21 20:30, Sala2, 150",
            "S003, 2024-12-22 15:00, SalaVIP, 50",
            "S004, 2024-12-23 21:00, Sala3, 200"
    })
    void testTworzenieSeansowZRoznymiDanymi(String id, String data, String sala, int miejsca) {
        // Jeśli: Dane seansu z parametrów CSV

        // Gdy: Tworzymy seans z tymi danymi
        Seans testSeans = new Seans(id, filmTestowy, data, sala, miejsca);

        // Wtedy: Wszystkie pola powinny być poprawnie ustawione
        assertEquals(id, testSeans.dajId(), "ID powinno być poprawne");
        assertEquals(data, testSeans.dajDate(), "Data powinna być poprawna");
        assertEquals(miejsca, testSeans.dajWolneMiejsca().length, "Liczba miejsc powinna być poprawna");
        assertNotNull(testSeans.dajFilm(), "Film nie powinien być null");
    }

    // ========== TESTY PARAMETRYZOWANE - @ValueSource ==========

    @ParameterizedTest
    @Order(11)
    @DisplayName("Test seansów z różną liczbą miejsc - @ValueSource")
    @ValueSource(ints = { 20, 50, 100, 150, 200, 300 })
    void testSeanseZRoznaLiczbaMiejsc(int liczbaMiejsc) {
        // Jeśli: Różne liczby miejsc w sali

        // Gdy: Tworzymy seans z określoną liczbą miejsc
        Seans testSeans = new Seans("ST", filmTestowy, "2024-12-25 12:00", "TestSala", liczbaMiejsc);

        // Wtedy: Liczba wolnych miejsc powinna być poprawna
        assertEquals(liczbaMiejsc, testSeans.dajWolneMiejsca().length,
                "Liczba wolnych miejsc powinna być równa " + liczbaMiejsc);
    }

    @ParameterizedTest
    @Order(12)
    @DisplayName("Test rezerwacji różnych numerów miejsc - @ValueSource")
    @ValueSource(ints = { 1, 25, 50, 75, 100 })
    void testRezerwacjaRoznychMiejsc(int nrMiejsca) {
        // Jeśli: Różne numery miejsc do rezerwacji

        // Gdy: Rezerwujemy określone miejsce
        boolean wynik = seans.zarezerwujMiejsce(nrMiejsca);

        // Wtedy: Rezerwacja powinna się udać
        assertTrue(wynik, "Rezerwacja miejsca " + nrMiejsca + " powinna się udać");
        assertEquals(99, seans.dajWolneMiejsca().length, "Powinno być 99 wolnych miejsc");
    }

    // ========== TESTY WARTOŚCI BRZEGOWYCH ==========

    @Test
    @Order(13)
    @DisplayName("Test rezerwacji pierwszego i ostatniego miejsca")
    void testRezerwacjaMiejscBrzegowych() {
        // Jeśli: Seans ma 100 miejsc

        // Gdy: Rezerwujemy pierwsze i ostatnie miejsce
        boolean pierwszeUdane = seans.zarezerwujMiejsce(1);
        boolean ostatnieUdane = seans.zarezerwujMiejsce(100);

        // Wtedy: Obie rezerwacje powinny się udać
        assertTrue(pierwszeUdane, "Rezerwacja miejsca 1 powinna się udać");
        assertTrue(ostatnieUdane, "Rezerwacja miejsca 100 powinna się udać");
        assertEquals(98, seans.dajWolneMiejsca().length, "Powinno być 98 wolnych miejsc");
    }

    @Test
    @Order(14)
    @DisplayName("Test seansu z minimalną liczbą miejsc")
    void testSeanseMinimalnaLiczbaMiejsc() {
        // Jeśli: Seans ma tylko 1 miejsce

        // Gdy: Tworzymy seans z 1 miejscem
        Seans maly = new Seans("SM", filmTestowy, "2024-12-25 12:00", "MicroSala", 1);

        // Wtedy: Powinno być 1 wolne miejsce
        assertEquals(1, maly.dajWolneMiejsca().length, "Powinno być 1 wolne miejsce");

        // I rezerwacja powinna się udać
        assertTrue(maly.zarezerwujMiejsce(1), "Rezerwacja jedynego miejsca powinna się udać");
        assertEquals(0, maly.dajWolneMiejsca().length, "Powinno być 0 wolnych miejsc po rezerwacji");
    }

    // ========== TESTY NIEZMIENNICZOŚCI ==========

    @Test
    @Order(15)
    @DisplayName("Test niezmienniczości danych seansu")
    void testNiezmiennoscDanych() {
        // Jeśli: Seans został utworzony z określonymi danymi
        String originalId = seans.dajId();
        String originalData = seans.dajDate();
        IFilm originalFilm = seans.dajFilm();

        // Gdy: Pobieramy dane wielokrotnie
        String id2 = seans.dajId();
        String data2 = seans.dajDate();
        IFilm film2 = seans.dajFilm();

        // Wtedy: Dane powinny pozostać niezmienione
        assertSame(originalId, id2, "ID nie powinno się zmienić");
        assertSame(originalData, data2, "Data nie powinna się zmienić");
        assertSame(originalFilm, film2, "Film nie powinien się zmienić");
    }
}
