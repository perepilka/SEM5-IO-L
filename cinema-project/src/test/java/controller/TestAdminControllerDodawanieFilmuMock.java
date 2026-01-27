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
 * Testy jednostkowe dla klasy AdminController - operacja dodawania filmu z
 * użyciem mockowania.
 * Testuje metodę AdminController.dodajFilm() z symulacją zależności (IModel).
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Warstwa: Kontroli (controller)
 * Zadanie: 2 (testy z mockowaniem)
 * 
 * Ref. z instrukcji: "Testy klas modelujących elementarne usługi biznesowe
 * (np. krok realizacji przypadku użycia) w warstwie kontroli"
 */
@DisplayName("Testy klasy AdminController - dodawanie filmu z mockowaniem")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Tag("kontroler")
@Tag("dodawanie")
@Tag("mock")
class TestAdminControllerDodawanieFilmuMock {

    /**
     * Mock obiektu Model - symulacja warstwy modelu.
     * Ref. z instrukcji: "operacja mock() lub adnotacja @Mock"
     */
    @Mock
    private IModel mockModel;

    /**
     * Testowany kontroler - NIE używamy @InjectMocks bo AdminController
     * tworzy wewnętrznie EdytowanieOfertyKina, więc tworzymy go ręcznie.
     */
    private AdminController adminController;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów AdminController z mockowaniem");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Utworzenie kontrolera z mockowanym modelem
        adminController = new AdminController(mockModel);
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście
        reset(mockModel);
        adminController = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów AdminController z mockowaniem");
    }

    // ========== TESTY DELEGACJI DO MODELU ==========

    @Test
    @Order(1)
    @DisplayName("Test że AdminController deleguje do Model.dodajFilm()")
    void testDelegacjaDoModelu() {
        // Jeśli: Mock Model zwraca sukces
        // Ref. z instrukcji: "when().thenReturn()"
        String daneFilmu = "F001;Matrix;Cyberpunk;136;SciFi;28.5";
        when(mockModel.dodajFilm(anyString())).thenReturn("Film dodany pomyslnie. ID: F001");

        // Gdy: Wywołujemy dodajFilm przez kontroler
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Model powinien być wywołany
        assertNotNull(wynik);
        assertTrue(wynik.contains("pomyslnie"));

        // Ref. z instrukcji: "verify(), times()"
        verify(mockModel, times(1)).dodajFilm(anyString());
    }

    @Test
    @Order(2)
    @DisplayName("Test że wynik z modelu jest zwracany bez modyfikacji")
    void testZwracanieWynikuBezModyfikacji() {
        // Jeśli: Model zwraca określony komunikat
        String oczekiwanyWynik = "Film dodany pomyslnie. ID: F001";
        when(mockModel.dodajFilm(anyString())).thenReturn(oczekiwanyWynik);

        // Gdy: Wywołujemy przez kontroler
        String wynik = adminController.dodajFilm("F001;Film;Opis;120;Gatunek;20.0");

        // Wtedy: Wynik powinien być identyczny
        assertEquals(oczekiwanyWynik, wynik, "Wynik powinien być identyczny z tym z modelu");
    }

    @Test
    @Order(3)
    @DisplayName("Test że dane są przekazywane do modelu")
    void testPrzekazywanieDanych() {
        // Jeśli: Dane filmu
        String daneFilmu = "F001;TestFilm;TestOpis;100;TestGatunek;25.0";
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");

        // Gdy: Wywołujemy kontroler
        adminController.dodajFilm(daneFilmu);

        // Wtedy: Dane powinny być przekazane do modelu (choć przez strategię)
        // Model.dodajFilm powinien być wywołany
        verify(mockModel).dodajFilm(anyString());
    }

    // ========== TESTY OBSŁUGI BŁĘDÓW ==========

    @Test
    @Order(4)
    @DisplayName("Test obsługi wyjątku z modelu")
    void testWyjatekZModelu() {
        // Jeśli: Model rzuca wyjątek
        // Ref. z instrukcji: "when().thenThrow()"
        when(mockModel.dodajFilm(anyString()))
                .thenThrow(new RuntimeException("Błąd w modelu"));

        // Gdy/Wtedy: Kontroler powinien propagować wyjątek
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adminController.dodajFilm("F001;Film;Opis;100;Gatunek;20.0"));

        assertTrue(ex.getMessage().contains("Błąd w modelu"));
    }

    @Test
    @Order(5)
    @DisplayName("Test że wyjątek nie powoduje wielokrotnych wywołań")
    void testWyjatekNiePowodujePonownegoWywolania() {
        // Jeśli: Model rzuca wyjątek
        when(mockModel.dodajFilm(anyString()))
                .thenThrow(new RuntimeException("Error"));

        // Gdy: Próba dodania filmu
        try {
            adminController.dodajFilm("FX;Film;Opis;100;Gatunek;20.0");
        } catch (RuntimeException e) {
            // Oczekiwany wyjątek
        }

        // Wtedy: Model powinien być wywołany tylko raz
        // Ref. z instrukcji: "atMostOnce()"
        verify(mockModel, atMostOnce()).dodajFilm(anyString());
    }

    // ========== TESTY KOLEJNOŚCI - InOrder ==========

    @Test
    @Order(6)
    @DisplayName("Test kolejności wywołań z InOrder")
    void testKolejnoscWywolan() {
        // Jeśli: Mock modelu
        // Ref. z instrukcji: "klasa InOrder w Mockito"
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");
        InOrder inOrder = inOrder(mockModel);

        // Gdy: Wywołujemy dodajFilm
        adminController.dodajFilm("F001;Film;Opis;100;Gatunek;20.0");

        // Wtedy: Model.dodajFilm powinien być wywołany
        inOrder.verify(mockModel).dodajFilm(anyString());
    }

    // ========== TESTY WERYFIKACJI NEVER ==========

    @Test
    @Order(7)
    @DisplayName("Test że inne metody modelu nie są wywoływane")
    void testNieWywolywanieInnychMetod() {
        // Jeśli: Mock modelu
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");

        // Gdy: Wywołujemy dodajFilm
        adminController.dodajFilm("F001;Film;Opis;100;Gatunek;20.0");

        // Wtedy: Inne metody nie powinny być wywołane
        // Ref. z instrukcji: "never()"
        verify(mockModel, never()).edytujFilm(anyString(), anyString());
        verify(mockModel, never()).usunFilm(anyString());
        verify(mockModel, never()).pobierzRepertuar(anyString());
    }

    // ========== TESTY PARAMETRYZOWANE ==========

    @ParameterizedTest
    @Order(8)
    @DisplayName("Test dodawania różnych filmów - @CsvSource")
    @CsvSource({
            "F001, Matrix, SciFi, 136, SciFi, 28.0",
            "F002, Avatar, Fantasy, 162, SciFi, 35.0",
            "F003, Titanic, Romans, 195, Dramat, 25.0"
    })
    void testDodajFilmParametryzowany(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Mock zwraca sukces
        String expectedResult = "Film dodany pomyslnie. ID: " + id;
        when(mockModel.dodajFilm(anyString())).thenReturn(expectedResult);

        // Gdy: Wywołujemy z parametrami
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;
        String wynik = adminController.dodajFilm(daneFilmu);

        // Wtedy: Wynik powinien zawierać ID
        assertTrue(wynik.contains(id), "Wynik powinien zawierać ID: " + id);
        verify(mockModel).dodajFilm(anyString());
    }

    @ParameterizedTest
    @Order(9)
    @DisplayName("Test dodawania filmów z różnymi komunikatami - @ValueSource")
    @ValueSource(strings = {
            "Film dodany pomyslnie. ID: F001",
            "Sukces - film F002 został dodany",
            "OK"
    })
    void testRozneKomunikatyZwrotne(String komunikatZModelu) {
        // Jeśli: Model zwraca różne komunikaty
        when(mockModel.dodajFilm(anyString())).thenReturn(komunikatZModelu);

        // Gdy: Wywołujemy kontroler
        String wynik = adminController.dodajFilm("FX;Film;Opis;100;Gatunek;20.0");

        // Wtedy: Komunikat powinien być przekazany bez zmian
        assertEquals(komunikatZModelu, wynik);
    }

    // ========== TESTY WIELOKROTNYCH WYWOŁAŃ ==========

    @Test
    @Order(10)
    @DisplayName("Test wielokrotnego dodawania filmów")
    void testWielokrotneDodawanieFilmow() {
        // Jeśli: Model zwraca różne odpowiedzi
        when(mockModel.dodajFilm(anyString()))
                .thenReturn("Film dodany. ID: F001")
                .thenReturn("Film dodany. ID: F002")
                .thenReturn("Film dodany. ID: F003");

        // Gdy: Dodajemy 3 filmy
        String wynik1 = adminController.dodajFilm("F001;Film1;Opis;100;Gatunek;20.0");
        String wynik2 = adminController.dodajFilm("F002;Film2;Opis;110;Gatunek;25.0");
        String wynik3 = adminController.dodajFilm("F003;Film3;Opis;120;Gatunek;30.0");

        // Wtedy: Każdy wynik powinien być inny
        assertTrue(wynik1.contains("F001"));
        assertTrue(wynik2.contains("F002"));
        assertTrue(wynik3.contains("F003"));

        // Ref. z instrukcji: "times(), atLeast()"
        verify(mockModel, times(3)).dodajFilm(anyString());
        verify(mockModel, atLeast(3)).dodajFilm(anyString());
    }

    @Test
    @Order(11)
    @DisplayName("Test weryfikacji atMost")
    void testAtMostWywolania() {
        // Jeśli: Mock modelu
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");

        // Gdy: Dodajemy 2 filmy
        adminController.dodajFilm("F001;Film1;Opis;100;Gatunek;20.0");
        adminController.dodajFilm("F002;Film2;Opis;110;Gatunek;25.0");

        // Wtedy: Weryfikacja atMost
        // Ref. z instrukcji: "atMost()"
        verify(mockModel, atMost(5)).dodajFilm(anyString());
    }

    // ========== TEST ARGUMENT CAPTOR ==========

    @Test
    @Order(12)
    @DisplayName("Test przechwytywania argumentów przekazanych do modelu")
    void testPrzechwytywanieDanych() {
        // Jeśli: Mock modelu
        when(mockModel.dodajFilm(anyString())).thenReturn("OK");

        // Gdy: Wywołujemy z konkretnymi danymi
        String daneFilmu = "FTEST;CaptorTest;Opis testowy;99;TestGatunek;19.99";
        adminController.dodajFilm(daneFilmu);

        // Wtedy: Weryfikujemy przekazane dane
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockModel).dodajFilm(captor.capture());

        // Dane przekazane przez strategię powinny zawierać elementy oryginalne
        String captured = captor.getValue();
        assertNotNull(captured);
    }
}
