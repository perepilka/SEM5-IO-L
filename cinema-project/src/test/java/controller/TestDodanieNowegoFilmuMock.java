package controller;

import model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testy jednostkowe dla klasy DodanieNowegoFilmu - strategia dodawania filmu.
 * Testuje metodę DodanieNowegoFilmu.edytujOferte() z symulacją zależności
 * (IModel).
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Warstwa: Kontroli (controller)
 * Zadanie: 2 (testy z mockowaniem)
 * 
 * Ref. z instrukcji: "Testy klas modelujących elementarne usługi biznesowe"
 */
@DisplayName("Testy klasy DodanieNowegoFilmu - strategia z mockowaniem")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Tag("kontroler")
@Tag("dodawanie")
@Tag("mock")
class TestDodanieNowegoFilmuMock {

    /**
     * Mock obiektu Model - symulacja warstwy modelu.
     * Ref. z instrukcji: "adnotacja @Mock"
     */
    @Mock
    private IModel mockModel;

    /**
     * Testowana strategia.
     */
    private DodanieNowegoFilmu strategia;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów DodanieNowegoFilmu z mockowaniem");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie strategii z mockowanym modelem
        strategia = new DodanieNowegoFilmu(mockModel);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        reset(mockModel);
        strategia = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów DodanieNowegoFilmu z mockowaniem");
    }

    // ========== TESTY DELEGACJI ==========

    @Test
    @Order(1)
    @DisplayName("Test że strategia deleguje do Model.dodajFilm()")
    void testDelegacjaDoModelu() {
        // Jeśli: Mock Model zwraca sukces
        // Ref. z instrukcji: "when().thenReturn()"
        String daneFilmu = "F001;Matrix;Cyberpunk;136;SciFi;28.5";
        when(mockModel.dodajFilm(daneFilmu)).thenReturn("Film dodany pomyslnie. ID: F001");

        // Gdy: Wywołujemy edytujOferte
        String wynik = strategia.edytujOferte(daneFilmu);

        // Wtedy: Model.dodajFilm powinien być wywołany raz
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"));

        // Ref. z instrukcji: "verify(), times()"
        verify(mockModel, times(1)).dodajFilm(daneFilmu);
    }

    @Test
    @Order(2)
    @DisplayName("Test że dane są przekazywane bez modyfikacji")
    void testPrzekazywanieDanychBezModyfikacji() {
        // Jeśli: Dane filmu
        String daneFilmu = "F001;Original;Description;100;Genre;20.0";
        when(mockModel.dodajFilm(daneFilmu)).thenReturn("OK");

        // Gdy: Wywołujemy strategię
        strategia.edytujOferte(daneFilmu);

        // Wtedy: Model powinien otrzymać dokładnie te same dane
        verify(mockModel).dodajFilm(eq(daneFilmu));
    }

    @Test
    @Order(3)
    @DisplayName("Test że wynik z modelu jest zwracany")
    void testZwracanieWyniku() {
        // Jeśli: Model zwraca określony wynik
        String oczekiwanyWynik = "Film dodany pomyslnie. ID: F002";
        when(mockModel.dodajFilm(anyString())).thenReturn(oczekiwanyWynik);

        // Gdy: Wywołujemy strategię
        String wynik = strategia.edytujOferte("F002;Film;Opis;120;Gatunek;25.0");

        // Wtedy: Wynik powinien być dokładnie taki jak z modelu
        assertEquals(oczekiwanyWynik, wynik);
    }

    // ========== TESTY Z WYJĄTKAMI ==========

    @Test
    @Order(4)
    @DisplayName("Test obsługi wyjątku z modelu")
    void testWyjatekZModelu() {
        // Jeśli: Model rzuca wyjątek
        // Ref. z instrukcji: "when().thenThrow()"
        when(mockModel.dodajFilm(anyString()))
                .thenThrow(new RuntimeException("Błąd dodawania filmu"));

        // Gdy/Wtedy: Strategia powinna propagować wyjątek
        assertThrows(RuntimeException.class,
                () -> strategia.edytujOferte("FE;Error;Opis;100;Gatunek;20.0"));
    }

    // ========== TESTY KOLEJNOŚCI - InOrder ==========

    @Test
    @Order(5)
    @DisplayName("Test kolejności wywołań")
    void testKolejnoscWywolan() {
        // Jeśli: InOrder dla weryfikacji
        // Ref. z instrukcji: "klasa InOrder w Mockito"
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");
        InOrder inOrder = inOrder(mockModel);

        // Gdy: Wywołujemy strategię
        strategia.edytujOferte("F001;Film;Opis;100;Gatunek;20.0");

        // Wtedy: Model.dodajFilm powinien być jedynym wywołaniem
        inOrder.verify(mockModel).dodajFilm(anyString());
    }

    // ========== TESTY WERYFIKACJI ==========

    @Test
    @Order(6)
    @DisplayName("Test że inne metody modelu nie są wywoływane")
    void testNieWywolywanieInnychMetod() {
        // Jeśli: Mock
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");

        // Gdy: Wywołujemy strategię
        strategia.edytujOferte("F001;Film;Opis;100;Gatunek;20.0");

        // Wtedy: Tylko dodajFilm powinien być wywołany
        // Ref. z instrukcji: "never()"
        verify(mockModel, never()).edytujFilm(anyString(), anyString());
        verify(mockModel, never()).usunFilm(anyString());
    }

    // ========== TESTY PARAMETRYZOWANE ==========

    @ParameterizedTest
    @Order(7)
    @DisplayName("Test strategii z różnymi filmami - @CsvSource")
    @CsvSource({
            "F001, Matrix, SciFi, 136, SciFi, 28.0",
            "F002, Avatar, Fantasy, 162, SciFi, 35.0",
            "F003, Titanic, Romans, 195, Dramat, 25.0"
    })
    void testStrategiaParametryzowana(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Mock zwraca sukces
        String expectedResult = "Film dodany pomyslnie. ID: " + id;
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;
        when(mockModel.dodajFilm(daneFilmu)).thenReturn(expectedResult);

        // Gdy: Wywołujemy strategię
        String wynik = strategia.edytujOferte(daneFilmu);

        // Wtedy: Wynik powinien zawierać ID
        assertTrue(wynik.contains(id));
        verify(mockModel).dodajFilm(daneFilmu);
    }

    @ParameterizedTest
    @Order(8)
    @DisplayName("Test strategii z różnymi komunikatami - @ValueSource")
    @ValueSource(strings = { "OK", "Sukces", "Film dodany" })
    void testRozneKomunikaty(String komunikat) {
        // Jeśli: Model zwraca różne komunikaty
        when(mockModel.dodajFilm(anyString())).thenReturn(komunikat);

        // Gdy: Wywołujemy strategię
        String wynik = strategia.edytujOferte("FX;Film;Opis;100;Gatunek;20.0");

        // Wtedy: Komunikat powinien być przekazany
        assertEquals(komunikat, wynik);
    }

    // ========== TESTY WIELOKROTNYCH WYWOŁAŃ ==========

    @Test
    @Order(9)
    @DisplayName("Test wielokrotnego użycia strategii")
    void testWielokrotneUzycie() {
        // Jeśli: Mock zwraca różne wyniki
        when(mockModel.dodajFilm(anyString()))
                .thenReturn("ID: F001")
                .thenReturn("ID: F002");

        // Gdy: Używamy strategii wielokrotnie
        String wynik1 = strategia.edytujOferte("F001;Film1;Opis;100;Gatunek;20.0");
        String wynik2 = strategia.edytujOferte("F002;Film2;Opis;110;Gatunek;25.0");

        // Wtedy: Każdy wynik powinien być inny
        assertNotEquals(wynik1, wynik2);

        // Ref. z instrukcji: "times(), atLeast()"
        verify(mockModel, times(2)).dodajFilm(anyString());
        verify(mockModel, atLeast(2)).dodajFilm(anyString());
        verify(mockModel, atMost(5)).dodajFilm(anyString());
    }
}
