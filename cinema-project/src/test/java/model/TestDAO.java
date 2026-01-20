package model;

import org.junit.jupiter.api.*;
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
        assertEquals("F1", id, "Pierwsze ID powinno być F1");
    }
    
    @Test
    @Order(2)
    @DisplayName("Test znajdowania filmu po ID")
    void testZnajdzFilm() {
        // Jeśli: Film został dodany do DAO
        String daneFilmu = "F001;Matrix;SciFi;136;Akcja;28.0";
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
    
    @Test
    @Order(4)
    @DisplayName("Test automatycznego inkrementowania ID filmów")
    void testInkrementacjaIdFilmow() {
        // Jeśli: Dane kilku filmów
        String film1 = "F001;Film1;Opis1;90;Komedia;20.0";
        String film2 = "F002;Film2;Opis2;120;Dramat;25.0";
        String film3 = "F003;Film3;Opis3;110;Akcja;30.0";
        
        // Gdy: Dodajemy filmy kolejno
        String id1 = dao.dodajFilm(film1);
        String id2 = dao.dodajFilm(film2);
        String id3 = dao.dodajFilm(film3);
        
        // Wtedy: ID powinny być kolejno inkrementowane
        assertEquals("F1", id1);
        assertEquals("F2", id2);
        assertEquals("F3", id3);
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
    @DisplayName("Test dodawania wielu filmów")
    @CsvSource({
        "F001, Titanic, Romans, 195, Dramat, 25.0",
        "F002, Avatar, Fantasy, 162, SciFi, 35.0",
        "F003, Inception, Thriller, 148, Akcja, 32.0"
    })
    void testDodajWieleFilmow(String id, String tytul, String opis, 
                              int czas, String gatunek, double cena) {
        // Jeśli: Dane filmu
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;
        
        // Gdy: Dodajemy film
        String generowaneId = dao.dodajFilm(daneFilmu);
        
        // Wtedy: Film powinien być dodany i możliwy do znalezienia
        assertNotNull(generowaneId);
        String znalezionyFilm = dao.znajdzFilm(generowaneId);
        assertNotNull(znalezionyFilm);
        assertTrue(znalezionyFilm.contains(tytul), "Znaleziony film powinien zawierać tytuł");
    }
    
    @ParameterizedTest
    @Order(7)
    @DisplayName("Test dodawania filmów o różnych cenach")
    @ValueSource(doubles = {10.0, 15.5, 20.0, 25.99, 30.0})
    void testDodajFilmyRozneCeny(double cena) {
        // Jeśli: Dane filmu z różnymi cenami
        String daneFilmu = "F001;Film;Opis;120;Gatunek;" + cena;
        
        // Gdy: Dodajemy film
        String id = dao.dodajFilm(daneFilmu);
        
        // Wtedy: Film powinien być zapisany
        assertNotNull(id);
        String znalezionyFilm = dao.znajdzFilm(id);
        assertTrue(znalezionyFilm.contains(String.valueOf(cena)), 
                   "Znaleziony film powinien zawierać cenę");
    }
}
