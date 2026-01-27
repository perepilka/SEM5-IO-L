package model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy FabrykaStandardowegoFilmu.
 * Testuje tworzenie filmów z danych wejściowych.
 */
@DisplayName("Testy klasy FabrykaStandardowegoFilmu")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("encja")
@Tag("dodawanie")
class TestFabrykaStandardowegoFilmu {

    private FabrykaStandardowegoFilmu fabryka;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów FabrykaStandardowegoFilmu");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie fabryki przed każdym testem
        fabryka = new FabrykaStandardowegoFilmu();
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        fabryka = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów FabrykaStandardowegoFilmu");
    }

    @Test
    @Order(1)
    @DisplayName("Test tworzenia filmu z poprawnych danych")
    void testUtworzFilmPoprawne() {
        // Jeśli: Dane filmu w formacie CSV
        String daneFilmu = "F001;Avengers;Superbohaterowie;150;Akcja;30.0";

        // Gdy: Tworzymy film używając fabryki
        IFilm film = fabryka.utworzFilm(daneFilmu);

        // Wtedy: Film powinien być utworzony z poprawnymi danymi
        assertNotNull(film, "Film nie powinien być null");
        assertEquals("Avengers", film.dajTytul());
        assertEquals("Superbohaterowie", film.dajOpis());
        assertEquals(150, film.dajCzasTrwania());
        assertEquals(30.0, film.dajCeneSeansow(), 0.01);
    }

    @Test
    @Order(2)
    @DisplayName("Test tworzenia filmu z minimalnymi danymi")
    void testUtworzFilmMinimalne() {
        // Jeśli: Minimalne dane filmu
        String daneFilmu = "F002;Film;Opis;60;Dramat;10.0";

        // Gdy: Tworzymy film
        IFilm film = fabryka.utworzFilm(daneFilmu);

        // Wtedy: Film powinien być utworzony
        assertNotNull(film);
        assertTrue(film.dajCzasTrwania() > 0, "Czas trwania powinien być dodatni");
        assertTrue(film.dajCeneSeansow() > 0, "Cena powinna być dodatnia");
    }

    @ParameterizedTest
    @Order(3)
    @DisplayName("Test tworzenia filmów z różnymi danymi wejściowymi")
    @CsvSource({
            "F001, Titanic, Romans na statku, 195, Dramat, 25.0",
            "F002, Matrix, Cyberpunk, 136, SciFi, 28.5",
            "F003, Joker, Psychologiczny, 122, Thriller, 32.0"
    })
    void testUtworzFilmParametryzowany(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Dane filmu z różnych źródeł
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;

        // Gdy: Tworzymy film
        IFilm film = fabryka.utworzFilm(daneFilmu);

        // Wtedy: Wszystkie pola powinny być poprawnie ustawione
        assertEquals(id, film.dajId());
        assertEquals(tytul, film.dajTytul());
        assertEquals(opis, film.dajOpis());
        assertEquals(czas, film.dajCzasTrwania());
        assertEquals(cena, film.dajCeneSeansow(), 0.01);
    }

    @ParameterizedTest
    @Order(4)
    @DisplayName("Test tworzenia filmów z różnymi cenami")
    @MethodSource("dostarczDaneFilmow")
    void testUtworzFilmZMetody(String daneFilmu, double oczekiwanaCena) {
        // Jeśli: Dane filmu dostarczone z metody

        // Gdy: Tworzymy film
        IFilm film = fabryka.utworzFilm(daneFilmu);

        // Wtedy: Cena powinna odpowiadać oczekiwanej
        assertNotNull(film);
        assertEquals(oczekiwanaCena, film.dajCeneSeansow(), 0.01);
        assertFalse(film.dajTytul().isEmpty(), "Tytuł nie powinien być pusty");
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> dostarczDaneFilmow() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of("F001;Film1;Opis1;90;Komedia;15.0", 15.0),
                org.junit.jupiter.params.provider.Arguments.of("F002;Film2;Opis2;120;Akcja;22.5", 22.5),
                org.junit.jupiter.params.provider.Arguments.of("F003;Film3;Opis3;100;Dramat;18.99", 18.99));
    }

    @Test
    @Order(5)
    @DisplayName("Test wyjątku przy niepoprawnych danych")
    void testUtworzFilmNiepoprawne() {
        // Jeśli: Niepoprawne dane wejściowe (brak wystarczającej liczby pól)
        String daneFilmu = "F001;Film";

        // Gdy/Wtedy: Powinien wystąpić wyjątek
        assertThrows(Exception.class, () -> {
            fabryka.utworzFilm(daneFilmu);
        }, "Powinien wystąpić wyjątek przy niepoprawnych danych");
    }
}
