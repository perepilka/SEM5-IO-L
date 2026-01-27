package controller;

import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy ClientController - przeglądanie repertuaru.
 * Testuje metodę ClientController.przegladajRepertuar() bez mockowania.
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Warstwa: Kontroli (controller)
 * Zadanie: 1 (testy bez mockowania)
 */
@DisplayName("Testy klasy ClientController - przeglądanie repertuaru")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("kontroler")
@Tag("repertuar")
class TestClientControllerPrzegladanieRepertuaru {

    private ClientController clientController;
    private Model model;
    private DAO dao;
    private Oferta oferta;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów ClientController - przeglądanie repertuaru");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie pełnego systemu przed każdym testem
        dao = new DAO();
        oferta = new Oferta(dao);
        model = new Model(oferta, dao);
        clientController = new ClientController(model);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        clientController = null;
        model = null;
        oferta = null;
        dao = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów ClientController - przeglądanie repertuaru");
    }

    // ========== TESTY PRZEGLĄDANIA REPERTUARU ==========

    @Test
    @Order(1)
    @DisplayName("Test przeglądania repertuaru przez ClientController")
    void testPrzegladajRepertuar() {
        // Jeśli: Dodano seanse dla filmu
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar("F1");

        // Wtedy: Powinien otrzymać informacje o seansach
        assertNotNull(repertuar, "Repertuar nie powinien być null");
        assertFalse(repertuar.isEmpty(), "Repertuar nie powinien być pusty");
        assertTrue(repertuar.contains("Repertuar"), "Repertuar powinien zawierać nagłówek");
    }

    @Test
    @Order(2)
    @DisplayName("Test przeglądania repertuaru bez seansów")
    void testPrzegladajRepertuarBezSeansow() {
        // Jeśli: Film nie ma seansów

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar("F99");

        // Wtedy: Powinien otrzymać komunikat o braku seansów
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"),
                "Powinien być komunikat o braku seansów");
    }

    @Test
    @Order(3)
    @DisplayName("Test że ClientController deleguje do Model")
    void testDelegacjaDoModel() {
        // Jeśli: Dodano seans z unikalnymi danymi
        dao.dodajSeans("F1;2024-12-25 20:00;SalaSpecjalna;200");

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar("F1");

        // Wtedy: Wynik powinien zawierać dane z DAO (przeszło przez Model)
        assertTrue(repertuar.contains("SalaSpecjalna"),
                "Repertuar powinien zawierać dane z DAO");
        assertTrue(repertuar.contains("2024-12-25 20:00"),
                "Repertuar powinien zawierać datę z DAO");
    }

    @Test
    @Order(4)
    @DisplayName("Test przeglądania repertuaru z wieloma seansami")
    void testPrzegladajRepertuarWieleSeansow() {
        // Jeśli: Dodano wiele seansów
        dao.dodajSeans("F1;2024-12-20 10:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 13:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 16:00;Sala2;80");
        dao.dodajSeans("F1;2024-12-20 19:00;Sala1;100");

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar("F1");

        // Wtedy: Wszystkie seanse powinny być dostępne
        assertTrue(repertuar.contains("10:00"), "Repertuar powinien zawierać seans o 10:00");
        assertTrue(repertuar.contains("13:00"), "Repertuar powinien zawierać seans o 13:00");
        assertTrue(repertuar.contains("16:00"), "Repertuar powinien zawierać seans o 16:00");
        assertTrue(repertuar.contains("19:00"), "Repertuar powinien zawierać seans o 19:00");
    }

    @Test
    @Order(5)
    @DisplayName("Test że repertuar zawiera tylko seanse wybranego filmu")
    void testRepertuarTylkoWybranyFilm() {
        // Jeśli: Dodano seanse dla różnych filmów
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F2;2024-12-21 19:00;Sala2;80");
        dao.dodajSeans("F3;2024-12-22 20:00;Sala3;120");

        // Gdy: Klient przegląda repertuar dla F1
        String repertuar = clientController.przegladajRepertuar("F1");

        // Wtedy: Repertuar powinien zawierać tylko seanse F1
        assertTrue(repertuar.contains("2024-12-20 18:00"),
                "Repertuar powinien zawierać seans F1");
        assertFalse(repertuar.contains("2024-12-21 19:00"),
                "Repertuar nie powinien zawierać seansu F2");
        assertFalse(repertuar.contains("2024-12-22 20:00"),
                "Repertuar nie powinien zawierać seansu F3");
    }

    // ========== TESTY PEŁNEGO PRZEPŁYWU ==========

    @Test
    @Order(6)
    @DisplayName("Test pełnego przepływu - od kontrolera do DAO")
    void testPelnyPrzeplyw() {
        // Jeśli: Kompletne dane seansu
        String daneSeansu = "F1;2024-12-25 18:00;SalaGłówna;150";
        dao.dodajSeans(daneSeansu);

        // Gdy: Klient przegląda repertuar przez ClientController
        // (co wywołuje Model -> DAO)
        String repertuar = clientController.przegladajRepertuar("F1");

        // Wtedy: Cały przepływ powinien zakończyć się sukcesem
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Repertuar dla filmu F1"),
                "Repertuar powinien mieć poprawny nagłówek");
        assertTrue(repertuar.contains("SalaGłówna"),
                "Repertuar powinien zawierać dane seansu");
    }

    @Test
    @Order(7)
    @DisplayName("Test wielokrotnego przeglądania tego samego repertuaru")
    void testWielokrotnePrzegladanie() {
        // Jeśli: Dodano seanse
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");

        // Gdy: Klient przegląda repertuar wielokrotnie
        String repertuar1 = clientController.przegladajRepertuar("F1");
        String repertuar2 = clientController.przegladajRepertuar("F1");
        String repertuar3 = clientController.przegladajRepertuar("F1");

        // Wtedy: Każde przeglądanie powinno zwrócić identyczny wynik
        assertEquals(repertuar1, repertuar2, "Repertuar powinien być spójny");
        assertEquals(repertuar2, repertuar3, "Repertuar powinien być spójny");
    }

    @Test
    @Order(8)
    @DisplayName("Test przeglądania repertuaru różnych filmów sekwencyjnie")
    void testPrzegladanieRoznychFilmow() {
        // Jeśli: Dodano seanse dla różnych filmów
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F2;2024-12-21 19:00;Sala2;80");
        dao.dodajSeans("F3;2024-12-22 20:00;Sala3;120");

        // Gdy: Klient przegląda repertuary kolejno
        String repertuarF1 = clientController.przegladajRepertuar("F1");
        String repertuarF2 = clientController.przegladajRepertuar("F2");
        String repertuarF3 = clientController.przegladajRepertuar("F3");

        // Wtedy: Każdy repertuar powinien być inny i zawierać właściwe dane
        assertNotEquals(repertuarF1, repertuarF2, "Repertuary powinny być różne");
        assertNotEquals(repertuarF2, repertuarF3, "Repertuary powinny być różne");
        assertTrue(repertuarF1.contains("F1"), "Repertuar F1 powinien zawierać ID F1");
        assertTrue(repertuarF2.contains("F2"), "Repertuar F2 powinien zawierać ID F2");
        assertTrue(repertuarF3.contains("F3"), "Repertuar F3 powinien zawierać ID F3");
    }

    // ========== TESTY PARAMETRYZOWANE - @CsvSource ==========

    @ParameterizedTest
    @Order(9)
    @DisplayName("Test przeglądania repertuaru z różnymi danymi - @CsvSource")
    @CsvSource({
            "F1, 2024-12-20 18:00, Sala1, 100",
            "F2, 2024-12-21 20:30, Sala2, 150",
            "F3, 2024-12-22 15:00, SalaVIP, 50",
            "F4, 2024-12-23 21:00, IMAX, 200"
    })
    void testPrzegladajRepertuarParametryzowany(String idFilmu, String data,
            String sala, int miejsca) {
        // Jeśli: Dodano seans z parametrów
        dao.dodajSeans(idFilmu + ";" + data + ";" + sala + ";" + miejsca);

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar(idFilmu);

        // Wtedy: Repertuar powinien zawierać dane seansu
        assertNotNull(repertuar);
        assertTrue(repertuar.contains(data),
                "Repertuar powinien zawierać datę: " + data);
        assertTrue(repertuar.contains(sala),
                "Repertuar powinien zawierać salę: " + sala);
    }

    @ParameterizedTest
    @Order(10)
    @DisplayName("Test przeglądania repertuaru dla różnych filmów - @CsvSource")
    @CsvSource({
            "FILM001, 3",
            "FILM002, 2",
            "FILM003, 1"
    })
    void testLiczbaSeansowWRepertuarze(String idFilmu, int liczbaSeansow) {
        // Jeśli: Dodajemy określoną liczbę seansów
        for (int i = 0; i < liczbaSeansow; i++) {
            dao.dodajSeans(idFilmu + ";2024-12-20 " + (10 + i * 3) + ":00;Sala" + (i + 1) + ";100");
        }

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar(idFilmu);

        // Wtedy: Liczba seansów powinna być prawidłowa
        int licznik = repertuar.split("Seans").length - 1;
        assertEquals(liczbaSeansow, licznik,
                "Powinno być " + liczbaSeansow + " seansów dla " + idFilmu);
    }

    // ========== TESTY PARAMETRYZOWANE - @ValueSource ==========

    @ParameterizedTest
    @Order(11)
    @DisplayName("Test przeglądania repertuaru dla filmów bez seansów - @ValueSource")
    @ValueSource(strings = { "NIEISTNIEJACY", "F999", "XYZ", "TEST123" })
    void testPrzegladajRepertuarBrakSeansow(String idFilmu) {
        // Jeśli: Film nie ma żadnych seansów

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar(idFilmu);

        // Wtedy: Powinien otrzymać komunikat o braku seansów
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"),
                "Powinien być komunikat o braku seansów dla " + idFilmu);
    }

    @ParameterizedTest
    @Order(12)
    @DisplayName("Test przeglądania repertuaru z różnymi ID filmów - @ValueSource")
    @ValueSource(strings = { "F1", "F10", "FILM", "MOVIE_2024" })
    void testPrzegladajRepertuarRozneIdFilmow(String idFilmu) {
        // Jeśli: Dodano seans dla filmu
        dao.dodajSeans(idFilmu + ";2024-12-25 18:00;Sala1;100");

        // Gdy: Klient przegląda repertuar
        String repertuar = clientController.przegladajRepertuar(idFilmu);

        // Wtedy: Repertuar powinien zawierać dane seansu
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Repertuar dla filmu " + idFilmu),
                "Repertuar powinien zawierać nagłówek z ID filmu");
        assertFalse(repertuar.contains("Brak seansow"),
                "Nie powinno być komunikatu o braku seansów");
    }

    // ========== TESTY EDGE CASES ==========

    @Test
    @Order(13)
    @DisplayName("Test przeglądania repertuaru z pustym ID")
    void testPrzegladajRepertuarPusteId() {
        // Jeśli: ID filmu jest puste

        // Gdy: Klient przegląda repertuar z pustym ID
        String repertuar = clientController.przegladajRepertuar("");

        // Wtedy: Powinien otrzymać komunikat o braku seansów
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"),
                "Powinien być komunikat o braku seansów");
    }

    @Test
    @Order(14)
    @DisplayName("Test że ClientController nie modyfikuje danych")
    void testNieModyfikujeDanych() {
        // Jeśli: Dodano seans
        String daneSeansu = "F1;2024-12-20 18:00;Sala1;100";
        String seansId = dao.dodajSeans(daneSeansu);

        // Gdy: Klient przegląda repertuar wielokrotnie
        clientController.przegladajRepertuar("F1");
        clientController.przegladajRepertuar("F1");

        // Wtedy: Dane seansu powinny pozostać niezmienione
        String danePo = dao.znajdzSeans(seansId);
        assertEquals(daneSeansu, danePo,
                "Dane seansu nie powinny być modyfikowane przez przeglądanie");
    }
}
