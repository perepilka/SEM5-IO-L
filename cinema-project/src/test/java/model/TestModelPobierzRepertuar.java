package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Model - operacja pobierania repertuaru.
 * Testuje metodę Model.pobierzRepertuar() bez mockowania (z prawdziwym DAO).
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Warstwa: Encja (model)
 * Zadanie: 1 (testy bez mockowania)
 */
@DisplayName("Testy klasy Model - pobieranie repertuaru")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("repertuar")
class TestModelPobierzRepertuar {

    private Model model;
    private DAO dao;
    private Oferta oferta;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów Model - pobieranie repertuaru");
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
        System.out.println("Zakończenie testów Model - pobieranie repertuaru");
    }

    // ========== TESTY POBIERANIA REPERTUARU Z SEANSAMI ==========

    @Test
    @Order(1)
    @DisplayName("Test pobierania repertuaru gdy są seanse dla filmu")
    void testPobierzRepertuarZSeansami() {
        // Jeśli: Dodano seanse dla filmu F1
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");

        // Gdy: Pobieramy repertuar dla filmu F1
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Repertuar powinien zawierać informacje o seansach
        assertNotNull(repertuar, "Repertuar nie powinien być null");
        assertFalse(repertuar.isEmpty(), "Repertuar nie powinien być pusty");
        assertTrue(repertuar.contains("Repertuar"), "Repertuar powinien zawierać nagłówek");
        assertTrue(repertuar.contains("Seans"), "Repertuar powinien zawierać informacje o seansach");
    }

    @Test
    @Order(2)
    @DisplayName("Test pobierania repertuaru gdy brak seansów dla filmu")
    void testPobierzRepertuarBezSeansow() {
        // Jeśli: Nie dodano żadnych seansów dla filmu F99

        // Gdy: Pobieramy repertuar dla filmu F99
        String repertuar = model.pobierzRepertuar("F99");

        // Wtedy: Powinniśmy otrzymać komunikat o braku seansów
        assertNotNull(repertuar, "Repertuar nie powinien być null");
        assertTrue(repertuar.contains("Brak seansow"),
                "Repertuar powinien zawierać komunikat o braku seansów");
        assertTrue(repertuar.contains("F99"),
                "Komunikat powinien zawierać ID filmu");
    }

    @Test
    @Order(3)
    @DisplayName("Test że repertuar zawiera dane wszystkich seansów filmu")
    void testRepertuarZawieraDaneSeansow() {
        // Jeśli: Dodano seanse z konkretnymi danymi
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Repertuar powinien zawierać dane wszystkich seansów
        assertTrue(repertuar.contains("2024-12-20 18:00"),
                "Repertuar powinien zawierać datę pierwszego seansu");
        assertTrue(repertuar.contains("2024-12-20 21:00"),
                "Repertuar powinien zawierać datę drugiego seansu");
        assertTrue(repertuar.contains("Sala1"),
                "Repertuar powinien zawierać salę pierwszego seansu");
        assertTrue(repertuar.contains("Sala2"),
                "Repertuar powinien zawierać salę drugiego seansu");
    }

    @Test
    @Order(4)
    @DisplayName("Test formatu repertuaru")
    void testFormatRepertuaru() {
        // Jeśli: Dodano seans
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Repertuar powinien mieć odpowiedni format
        assertTrue(repertuar.startsWith("Repertuar dla filmu"),
                "Repertuar powinien zaczynać się od nagłówka");
        assertTrue(repertuar.contains("F1"),
                "Repertuar powinien zawierać ID filmu");
        assertTrue(repertuar.contains("- Seans:"),
                "Repertuar powinien formatować seanse z myślnikiem");
    }

    @Test
    @Order(5)
    @DisplayName("Test że repertuar nie zawiera seansów innych filmów")
    void testRepertuarNieZawieraInnychFilmow() {
        // Jeśli: Dodano seanse dla różnych filmów
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F2;2024-12-21 19:00;Sala2;80");
        dao.dodajSeans("F3;2024-12-22 20:00;Sala3;120");

        // Gdy: Pobieramy repertuar dla filmu F1
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Repertuar powinien zawierać tylko seanse F1
        assertTrue(repertuar.contains("2024-12-20 18:00"),
                "Repertuar powinien zawierać seans F1");
        assertFalse(repertuar.contains("2024-12-21 19:00"),
                "Repertuar nie powinien zawierać seansu F2");
        assertFalse(repertuar.contains("2024-12-22 20:00"),
                "Repertuar nie powinien zawierać seansu F3");
    }

    // ========== TESTY WIELU SEANSÓW ==========

    @Test
    @Order(6)
    @DisplayName("Test pobierania repertuaru z wieloma seansami")
    void testPobierzRepertuarWieleSeansow() {
        // Jeśli: Dodano 5 seansów dla tego samego filmu
        dao.dodajSeans("F1;2024-12-20 10:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 13:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 16:00;Sala2;80");
        dao.dodajSeans("F1;2024-12-20 19:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 22:00;Sala2;80");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Wszystkie seanse powinny być wymienione
        assertNotNull(repertuar);
        // Liczymy wystąpienia "Seans" w repertuarze
        int liczbaSeansow = repertuar.split("Seans").length - 1;
        assertEquals(5, liczbaSeansow, "Powinno być 5 seansów w repertuarze");
    }

    @Test
    @Order(7)
    @DisplayName("Test pobierania repertuaru z pojedynczym seansem")
    void testPobierzRepertuarJedenSeans() {
        // Jeśli: Dodano tylko jeden seans
        dao.dodajSeans("F1;2024-12-25 20:00;SalaGłówna;150");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Repertuar powinien zawierać dokładnie jeden seans
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Seans"), "Repertuar powinien zawierać seans");
        assertTrue(repertuar.contains("2024-12-25 20:00"),
                "Repertuar powinien zawierać datę seansu");
    }

    // ========== TESTY PARAMETRYZOWANE - @CsvSource ==========

    @ParameterizedTest
    @Order(8)
    @DisplayName("Test pobierania repertuaru dla różnych filmów - @CsvSource")
    @CsvSource({
            "F1, 2024-12-20 18:00, Sala1",
            "F2, 2024-12-21 20:30, Sala2",
            "F3, 2024-12-22 15:00, SalaVIP"
    })
    void testPobierzRepertuarParametryzowany(String idFilmu, String data, String sala) {
        // Jeśli: Dodano seans z parametrów
        dao.dodajSeans(idFilmu + ";" + data + ";" + sala + ";100");

        // Gdy: Pobieramy repertuar dla danego filmu
        String repertuar = model.pobierzRepertuar(idFilmu);

        // Wtedy: Repertuar powinien zawierać dane seansu
        assertNotNull(repertuar, "Repertuar nie powinien być null");
        assertTrue(repertuar.contains(data), "Repertuar powinien zawierać datę");
        assertTrue(repertuar.contains(sala), "Repertuar powinien zawierać salę");
        assertFalse(repertuar.contains("Brak seansow"),
                "Nie powinno być komunikatu o braku seansów");
    }

    @ParameterizedTest
    @Order(9)
    @DisplayName("Test liczby seansów w repertuarze - @CsvSource")
    @CsvSource({
            "F1, 1",
            "F2, 2",
            "F3, 3"
    })
    void testLiczbaSeansowWRepertuarze(String idFilmu, int oczekiwanaLiczba) {
        // Jeśli: Dodajemy określoną liczbę seansów dla każdego filmu
        for (int i = 0; i < oczekiwanaLiczba; i++) {
            dao.dodajSeans(idFilmu + ";2024-12-20 " + (10 + i) + ":00;Sala1;100");
        }
        // Dodajemy też seanse innych filmów jako szum
        dao.dodajSeans("FX;2024-12-25 12:00;Sala1;100");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar(idFilmu);

        // Wtedy: Liczba seansów powinna być zgodna z oczekiwaną
        int liczbaSeansow = repertuar.split("Seans").length - 1;
        assertEquals(oczekiwanaLiczba, liczbaSeansow,
                "Repertuar dla " + idFilmu + " powinien mieć " + oczekiwanaLiczba + " seansów");
    }

    // ========== TESTY PARAMETRYZOWANE - @ValueSource ==========

    @ParameterizedTest
    @Order(10)
    @DisplayName("Test pobierania repertuaru dla filmów bez seansów - @ValueSource")
    @ValueSource(strings = { "F99", "F100", "NIEISTNIEJACY", "XYZ123" })
    void testPobierzRepertuarBrakSeansow(String idFilmu) {
        // Jeśli: Film nie ma żadnych seansów

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar(idFilmu);

        // Wtedy: Powinniśmy otrzymać komunikat o braku seansów
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"),
                "Powinien być komunikat o braku seansów dla " + idFilmu);
        assertTrue(repertuar.contains(idFilmu),
                "Komunikat powinien zawierać ID filmu");
    }

    @ParameterizedTest
    @Order(11)
    @DisplayName("Test repertuaru z różnymi salami - @ValueSource")
    @ValueSource(strings = { "Sala1", "Sala2", "SalaVIP", "SalaIMAX", "Sala3D" })
    void testRepertuarRozneSale(String sala) {
        // Jeśli: Dodano seans w określonej sali
        dao.dodajSeans("F1;2024-12-25 18:00;" + sala + ";100");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Repertuar powinien zawierać nazwę sali
        assertTrue(repertuar.contains(sala),
                "Repertuar powinien zawierać salę: " + sala);
    }

    // ========== TESTY EDGE CASES ==========

    @Test
    @Order(12)
    @DisplayName("Test pobierania repertuaru z pustym ID filmu")
    void testPobierzRepertuarPusteId() {
        // Jeśli: ID filmu jest puste

        // Gdy: Pobieramy repertuar z pustym ID
        String repertuar = model.pobierzRepertuar("");

        // Wtedy: Powinniśmy otrzymać komunikat o braku seansów
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"),
                "Powinien być komunikat o braku seansów dla pustego ID");
    }

    @Test
    @Order(13)
    @DisplayName("Test że pobieranie repertuaru nie modyfikuje danych")
    void testPobierzRepertuarNieModyfikujeDanych() {
        // Jeśli: Dodano seans
        String daneSeansu = "F1;2024-12-20 18:00;Sala1;100";
        String seansId = dao.dodajSeans(daneSeansu);

        // Gdy: Pobieramy repertuar wielokrotnie
        model.pobierzRepertuar("F1");
        model.pobierzRepertuar("F1");
        model.pobierzRepertuar("F1");

        // Wtedy: Dane seansu powinny pozostać niezmienione
        String danePo = dao.znajdzSeans(seansId);
        assertEquals(daneSeansu, danePo, "Dane seansu nie powinny być modyfikowane");
    }

    @Test
    @Order(14)
    @DisplayName("Test spójności wielokrotnego pobierania repertuaru")
    void testSpojnoscWielokrotnePobieranie() {
        // Jeśli: Dodano seanse
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");

        // Gdy: Pobieramy repertuar wielokrotnie
        String repertuar1 = model.pobierzRepertuar("F1");
        String repertuar2 = model.pobierzRepertuar("F1");
        String repertuar3 = model.pobierzRepertuar("F1");

        // Wtedy: Każde pobranie powinno zwrócić identyczny wynik
        assertEquals(repertuar1, repertuar2, "Repertuar powinien być spójny");
        assertEquals(repertuar2, repertuar3, "Repertuar powinien być spójny");
    }
}
