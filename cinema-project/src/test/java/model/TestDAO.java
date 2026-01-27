package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy DAO.
 * Testuje operacje dodawania i wyszukiwania danych.
 */
@DisplayName("Testy klasy DAO")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("dodawanie")
class TestDAO {

    private DAO dao;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów DAO");
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
        System.out.println("Zakończenie testów DAO");
    }

    @Test
    @Order(1)
    @DisplayName("Test dodawania filmu i generowania ID")
    void testDodajFilm() {
        // Jeśli: Dane filmu do zapisania
        String daneFilmu = "F001;Avengers;Superbohaterowie;150;Akcja;30.0";

        // Gdy: Dodajemy film do DAO
        String id = dao.dodajFilm(daneFilmu);

        // Wtedy: ID powinno być wygenerowane i film powinien być zapisany
        assertNotNull(id, "ID nie powinno być null");
        assertTrue(id.startsWith("F"), "ID powinno zaczynać się od F");
        assertEquals("F001", id, "ID powinno być F001");
    }

    @Test
    @Order(2)
    @DisplayName("Test znajdowania filmu po ID")
    void testZnajdzFilm() {
        // Jeśli: Film został dodany do DAO
        String daneFilmu = "F1;Matrix;SciFi;136;Akcja;28.0";
        String id = dao.dodajFilm(daneFilmu);

        // Gdy: Szukamy filmu po ID
        String znalezionyFilm = dao.znajdzFilm(id);

        // Wtedy: Powinniśmy znaleźć ten sam film
        assertNotNull(znalezionyFilm, "Film powinien zostać znaleziony");
        assertEquals(daneFilmu, znalezionyFilm, "Dane filmu powinny być identyczne");
    }

    @Test
    @Order(3)
    @DisplayName("Test szukania nieistniejącego filmu")
    void testZnajdzFilmNieistniejacy() {
        // Jeśli: DAO jest puste

        // Gdy: Szukamy nieistniejącego filmu
        String znalezionyFilm = dao.znajdzFilm("F999");

        // Wtedy: Powinniśmy otrzymać null
        assertNull(znalezionyFilm, "Nieistniejący film powinien zwrócić null");
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test dodawania filmów z różnymi ID")
    @CsvSource({
            "F1;Film1;Opis1;90;Komedia;20.0, F2;Film2;Opis2;120;Dramat;25.0, F3;Film3;Opis3;110;Akcja;30.0",
            "F10;KomediaX;OpisX;95;Komedia;18.0, F11;DramatY;OpisY;125;Dramat;22.5, F12;AkcjaZ;OpisZ;100;Akcja;27.0"
    })
    void testInkrementacjaIdFilmow(String film1, String film2, String film3) {
        // Jeśli: Dane kilku filmów (parametryzowane)

        // Gdy: Dodajemy filmy kolejno
        String id1 = dao.dodajFilm(film1);
        String id2 = dao.dodajFilm(film2);
        String id3 = dao.dodajFilm(film3);

        // Wtedy: ID powinny być takie same jak w danych
        String[] parts1 = film1.split(";");
        String[] parts2 = film2.split(";");
        String[] parts3 = film3.split(";");
        assertEquals(parts1[0], id1);
        assertEquals(parts2[0], id2);
        assertEquals(parts3[0], id3);
        assertNotEquals(id1, id2, "ID powinny być różne");
    }

    @Test
    @Order(5)
    @DisplayName("Test dodawania wpisu do logu")
    void testDodajWpisDoLogu() {
        // Jeśli: Treść zdarzenia do zalogowania
        String zdarzenie = "Test zdarzenia";

        // Gdy: Dodajemy wpis do logu
        // Wtedy: Nie powinien wystąpić wyjątek
        assertDoesNotThrow(() -> {
            dao.dodajWpisDoLogu(zdarzenie);
        }, "Dodawanie wpisu do logu nie powinno rzucić wyjątku");
    }

    @ParameterizedTest
    @Order(6)
    @DisplayName("Test dodawania filmów o różnych cenach")
    @ValueSource(doubles = { 10.0, 15.5, 20.0, 25.99, 30.0 })
    void testDodajFilmyRozneCeny(double cena) {
        // Jeśli: Dane filmu z różnymi cenami
        String daneFilmu = "FX;Film;Opis;120;Gatunek;" + cena;

        // Gdy: Dodajemy film
        String id = dao.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być zapisany
        assertNotNull(id);
        String znalezionyFilm = dao.znajdzFilm(id);
        assertTrue(znalezionyFilm.contains(String.valueOf(cena)),
                "Znaleziony film powinien zawierać cenę");
    }
}
