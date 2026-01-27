package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy DAO - operacje związane z seansami.
 * Testuje dodawanie, wyszukiwanie i usuwanie seansów.
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Warstwa: Encja (model)
 * Zadanie: 1 (testy bez mockowania)
 */
@DisplayName("Testy klasy DAO - operacje seansów")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("repertuar")
class TestDAOSeansy {

    private DAO dao;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów DAO - operacje seansów");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie nowego DAO przed każdym testem
        dao = new DAO();
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        dao = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów DAO - operacje seansów");
    }

    // ========== TESTY DODAWANIA SEANSÓW ==========

    @Test
    @Order(1)
    @DisplayName("Test dodawania seansu i generowania ID")
    void testDodajSeansGenerujeId() {
        // Jeśli: Dane seansu do zapisania (format: idFilmu;data;sala;miejsca)
        String daneSeansu = "F1;2024-12-20 18:00;Sala1;100";

        // Gdy: Dodajemy seans do DAO
        String id = dao.dodajSeans(daneSeansu);

        // Wtedy: ID powinno być wygenerowane
        assertNotNull(id, "ID nie powinno być null");
        assertTrue(id.startsWith("S"), "ID powinno zaczynać się od S");
    }

    @Test
    @Order(2)
    @DisplayName("Test że kolejne seanse mają unikalne ID")
    void testDodajSeanseUnikalneldId() {
        // Jeśli: Dane kilku seansów
        String seans1 = "F1;2024-12-20 18:00;Sala1;100";
        String seans2 = "F1;2024-12-20 21:00;Sala2;80";
        String seans3 = "F2;2024-12-21 19:00;Sala1;100";

        // Gdy: Dodajemy seanse kolejno
        String id1 = dao.dodajSeans(seans1);
        String id2 = dao.dodajSeans(seans2);
        String id3 = dao.dodajSeans(seans3);

        // Wtedy: Wszystkie ID powinny być unikalne
        assertNotEquals(id1, id2, "ID pierwszego i drugiego seansu powinny być różne");
        assertNotEquals(id2, id3, "ID drugiego i trzeciego seansu powinny być różne");
        assertNotEquals(id1, id3, "ID pierwszego i trzeciego seansu powinny być różne");
    }

    // ========== TESTY WYSZUKIWANIA SEANSÓW ==========

    @Test
    @Order(3)
    @DisplayName("Test znajdowania seansu po ID")
    void testZnajdzSeans() {
        // Jeśli: Seans został dodany do DAO
        String daneSeansu = "F1;2024-12-20 18:00;Sala1;100";
        String id = dao.dodajSeans(daneSeansu);

        // Gdy: Szukamy seansu po ID
        String znalezionySeans = dao.znajdzSeans(id);

        // Wtedy: Powinniśmy znaleźć ten sam seans
        assertNotNull(znalezionySeans, "Seans powinien zostać znaleziony");
        assertEquals(daneSeansu, znalezionySeans, "Dane seansu powinny być identyczne");
    }

    @Test
    @Order(4)
    @DisplayName("Test szukania nieistniejącego seansu")
    void testZnajdzSeansNieistniejacy() {
        // Jeśli: DAO jest puste lub seans nie istnieje

        // Gdy: Szukamy nieistniejącego seansu
        String znalezionySeans = dao.znajdzSeans("S999");

        // Wtedy: Powinniśmy otrzymać null
        assertNull(znalezionySeans, "Nieistniejący seans powinien zwrócić null");
    }

    @Test
    @Order(5)
    @DisplayName("Test znajdowania seansów dla filmu")
    void testZnajdzSeansyFilmu() {
        // Jeśli: Dodano kilka seansów dla tego samego filmu
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");
        dao.dodajSeans("F2;2024-12-21 19:00;Sala1;100");

        // Gdy: Szukamy seansów dla filmu F1
        String[] seansyF1 = dao.znajdzSeansyFilmu("F1");

        // Wtedy: Powinny być 2 seanse dla filmu F1
        assertNotNull(seansyF1, "Tablica seansów nie powinna być null");
        assertEquals(2, seansyF1.length, "Powinny być 2 seanse dla filmu F1");
    }

    @Test
    @Order(6)
    @DisplayName("Test znajdowania seansów dla filmu który nie ma seansów")
    void testZnajdzSeansyFilmuBezSeansow() {
        // Jeśli: Dodano seanse dla filmu F1, ale nie dla F99
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");

        // Gdy: Szukamy seansów dla filmu F99
        String[] seansyF99 = dao.znajdzSeansyFilmu("F99");

        // Wtedy: Tablica powinna być pusta
        assertNotNull(seansyF99, "Tablica nie powinna być null");
        assertEquals(0, seansyF99.length, "Tablica powinna być pusta dla filmu bez seansów");
    }

    @Test
    @Order(7)
    @DisplayName("Test że znajdzSeansyFilmu zwraca poprawne ID seansów")
    void testZnajdzSeansyFilmuPoprawneId() {
        // Jeśli: Dodajemy seanse i zapisujemy ich ID
        String id1 = dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        String id2 = dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");
        dao.dodajSeans("F2;2024-12-21 19:00;Sala1;100"); // inny film

        // Gdy: Szukamy seansów dla filmu F1
        String[] seansyF1 = dao.znajdzSeansyFilmu("F1");

        // Wtedy: Zwrócone ID powinny odpowiadać dodanym seansom
        assertEquals(2, seansyF1.length, "Powinny być 2 seanse");

        // Sprawdzamy czy oba ID są w tablicy
        boolean zawieraId1 = false;
        boolean zawieraId2 = false;
        for (String id : seansyF1) {
            if (id.equals(id1))
                zawieraId1 = true;
            if (id.equals(id2))
                zawieraId2 = true;
        }
        assertTrue(zawieraId1, "Tablica powinna zawierać ID pierwszego seansu");
        assertTrue(zawieraId2, "Tablica powinna zawierać ID drugiego seansu");
    }

    // ========== TESTY USUWANIA SEANSÓW ==========

    @Test
    @Order(8)
    @DisplayName("Test usuwania seansu")
    void testUsunSeans() {
        // Jeśli: Seans został dodany
        String id = dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        assertNotNull(dao.znajdzSeans(id), "Seans powinien istnieć przed usunięciem");

        // Gdy: Usuwamy seans
        dao.usunSeans(id);

        // Wtedy: Seans nie powinien być już dostępny
        assertNull(dao.znajdzSeans(id), "Seans nie powinien istnieć po usunięciu");
    }

    @Test
    @Order(9)
    @DisplayName("Test że usunięcie seansu nie wpływa na inne seanse")
    void testUsunSeansNieWplywaNaInne() {
        // Jeśli: Dodano kilka seansów
        String id1 = dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        String id2 = dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");

        // Gdy: Usuwamy pierwszy seans
        dao.usunSeans(id1);

        // Wtedy: Drugi seans powinien nadal istnieć
        assertNull(dao.znajdzSeans(id1), "Usunięty seans nie powinien istnieć");
        assertNotNull(dao.znajdzSeans(id2), "Inny seans powinien nadal istnieć");
    }

    // ========== TESTY PARAMETRYZOWANE - @CsvSource ==========

    @ParameterizedTest
    @Order(10)
    @DisplayName("Test dodawania seansów z różnymi danymi - @CsvSource")
    @CsvSource({
            "F1, 2024-12-20 18:00, Sala1, 100",
            "F2, 2024-12-21 20:30, Sala2, 150",
            "F3, 2024-12-22 15:00, SalaVIP, 50",
            "F4, 2024-12-23 21:00, Sala3, 200"
    })
    void testDodajSeansParametryzowany(String idFilmu, String data, String sala, int miejsca) {
        // Jeśli: Dane seansu z parametrów
        String daneSeansu = idFilmu + ";" + data + ";" + sala + ";" + miejsca;

        // Gdy: Dodajemy seans
        String id = dao.dodajSeans(daneSeansu);

        // Wtedy: Seans powinien być dodany i możliwy do znalezienia
        assertNotNull(id, "ID nie powinno być null");
        String znaleziony = dao.znajdzSeans(id);
        assertNotNull(znaleziony, "Seans powinien być znaleziony");
        assertEquals(daneSeansu, znaleziony, "Dane seansu powinny być identyczne");
    }

    @ParameterizedTest
    @Order(11)
    @DisplayName("Test znajdowania seansów dla różnych filmów - @CsvSource")
    @CsvSource({
            "F1, 3",
            "F2, 2",
            "F3, 1"
    })
    void testZnajdzSeansyDlaRoznychFilmow(String idFilmu, int oczekiwanaLiczba) {
        // Jeśli: Dodajemy seanse dla różnych filmów
        dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
        dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");
        dao.dodajSeans("F1;2024-12-21 18:00;Sala1;100");
        dao.dodajSeans("F2;2024-12-21 19:00;Sala1;100");
        dao.dodajSeans("F2;2024-12-21 21:00;Sala2;80");
        dao.dodajSeans("F3;2024-12-22 20:00;Sala1;100");

        // Gdy: Szukamy seansów dla określonego filmu
        String[] seanse = dao.znajdzSeansyFilmu(idFilmu);

        // Wtedy: Liczba seansów powinna być zgodna z oczekiwaną
        assertEquals(oczekiwanaLiczba, seanse.length,
                "Film " + idFilmu + " powinien mieć " + oczekiwanaLiczba + " seansów");
    }

    // ========== TESTY PARAMETRYZOWANE - @ValueSource ==========

    @ParameterizedTest
    @Order(12)
    @DisplayName("Test dodawania seansów z różnymi ID filmów - @ValueSource")
    @ValueSource(strings = { "F1", "F10", "F100", "F999", "FILM001" })
    void testDodajSeansRozneIdFilmow(String idFilmu) {
        // Jeśli: Różne ID filmów
        String daneSeansu = idFilmu + ";2024-12-25 18:00;Sala1;100";

        // Gdy: Dodajemy seans
        String id = dao.dodajSeans(daneSeansu);

        // Wtedy: Seans powinien być dodany
        assertNotNull(id, "ID seansu nie powinno być null");

        // I powinien być znajdowany przez znajdzSeansyFilmu
        String[] seanse = dao.znajdzSeansyFilmu(idFilmu);
        assertEquals(1, seanse.length, "Powinien być 1 seans dla filmu " + idFilmu);
    }

    @ParameterizedTest
    @Order(13)
    @DisplayName("Test dodawania seansów z różną liczbą miejsc - @ValueSource")
    @ValueSource(ints = { 20, 50, 100, 150, 200, 500 })
    void testDodajSeansRoznaLiczbaMiejsc(int liczbaMiejsc) {
        // Jeśli: Różne liczby miejsc
        String daneSeansu = "F1;2024-12-25 18:00;Sala1;" + liczbaMiejsc;

        // Gdy: Dodajemy seans
        String id = dao.dodajSeans(daneSeansu);

        // Wtedy: Seans powinien być zapisany z poprawną liczbą miejsc
        String znaleziony = dao.znajdzSeans(id);
        assertNotNull(znaleziony, "Seans powinien zostać znaleziony");
        assertTrue(znaleziony.contains(String.valueOf(liczbaMiejsc)),
                "Dane seansu powinny zawierać liczbę miejsc");
    }

    // ========== TESTY INTEGRALNOŚCI DANYCH ==========

    @Test
    @Order(14)
    @DisplayName("Test że dane seansu są przechowywane bez modyfikacji")
    void testDaneSeansaBezModyfikacji() {
        // Jeśli: Oryginalne dane seansu
        String oryginalneDane = "F1;2024-12-20 18:00;Sala Główna;120";

        // Gdy: Dodajemy i pobieramy seans
        String id = dao.dodajSeans(oryginalneDane);
        String pobraneDane = dao.znajdzSeans(id);

        // Wtedy: Dane powinny być identyczne
        assertEquals(oryginalneDane, pobraneDane, "Dane nie powinny być modyfikowane");
    }

    @Test
    @Order(15)
    @DisplayName("Test wielokrotnego wyszukiwania tego samego seansu")
    void testWielokrotneWyszukiwanie() {
        // Jeśli: Seans został dodany
        String daneSeansu = "F1;2024-12-20 18:00;Sala1;100";
        String id = dao.dodajSeans(daneSeansu);

        // Gdy: Szukamy seansu wielokrotnie
        String wynik1 = dao.znajdzSeans(id);
        String wynik2 = dao.znajdzSeans(id);
        String wynik3 = dao.znajdzSeans(id);

        // Wtedy: Każde wyszukiwanie powinno zwrócić te same dane
        assertEquals(wynik1, wynik2, "Wyniki powinny być identyczne");
        assertEquals(wynik2, wynik3, "Wyniki powinny być identyczne");
        assertEquals(daneSeansu, wynik1, "Dane powinny odpowiadać oryginalnym");
    }
}
