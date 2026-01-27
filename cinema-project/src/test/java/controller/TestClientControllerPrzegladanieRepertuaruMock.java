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
 * Testy jednostkowe dla klasy ClientController - przeglądanie repertuaru z
 * mockowaniem.
 * Testuje metodę ClientController.przegladajRepertuar() z symulacją zależności
 * (IModel).
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Warstwa: Kontroli (controller)
 * Zadanie: 2 (testy z mockowaniem)
 * 
 * Ref. z instrukcji: "Testy klas modelujących elementarne usługi biznesowe
 * w warstwie kontroli"
 */
@DisplayName("Testy klasy ClientController - przeglądanie repertuaru z mockowaniem")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Tag("kontroler")
@Tag("repertuar")
@Tag("mock")
class TestClientControllerPrzegladanieRepertuaruMock {

    /**
     * Mock obiektu Model - symulacja warstwy modelu.
     * Ref. z instrukcji: "adnotacja @Mock"
     */
    @Mock
    private IModel mockModel;

    /**
     * Testowany kontroler z wstrzykniętą symulacją modelu.
     * Ref. z instrukcji: "adnotacja @InjectMocks"
     */
    @InjectMocks
    private ClientController clientController;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów ClientController z mockowaniem - przeglądanie repertuaru");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Inicjalizacja mocków - wykonywana automatycznie przez
        // @ExtendWith(MockitoExtension.class)
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście - resetowanie mocków
        reset(mockModel);
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów ClientController z mockowaniem - przeglądanie repertuaru");
    }

    // ========== TESTY DELEGACJI DO MODELU ==========

    @Test
    @Order(1)
    @DisplayName("Test że ClientController deleguje do Model.pobierzRepertuar()")
    void testDelegacjaDoModelu() {
        // Jeśli: Mock Model zwraca repertuar
        // Ref. z instrukcji: "when().thenReturn()"
        String oczekiwanyRepertuar = "Repertuar dla filmu F1:\n  - Seans: dane";
        when(mockModel.pobierzRepertuar("F1")).thenReturn(oczekiwanyRepertuar);

        // Gdy: Wywołujemy przegladajRepertuar przez kontroler
        String wynik = clientController.przegladajRepertuar("F1");

        // Wtedy: Model powinien być wywołany
        assertNotNull(wynik);
        assertEquals(oczekiwanyRepertuar, wynik);

        // Ref. z instrukcji: "verify(), times()"
        verify(mockModel, times(1)).pobierzRepertuar("F1");
    }

    @Test
    @Order(2)
    @DisplayName("Test że wynik z modelu jest zwracany bez modyfikacji")
    void testZwracanieWynikuBezModyfikacji() {
        // Jeśli: Model zwraca określony repertuar
        String repertuarZModelu = "Repertuar testowy:\n  - Seans 1\n  - Seans 2";
        when(mockModel.pobierzRepertuar("F1")).thenReturn(repertuarZModelu);

        // Gdy: Wywołujemy przez kontroler
        String wynik = clientController.przegladajRepertuar("F1");

        // Wtedy: Wynik powinien być identyczny
        assertEquals(repertuarZModelu, wynik,
                "Wynik powinien być identyczny z tym z modelu");
    }

    @Test
    @Order(3)
    @DisplayName("Test że kryteria są przekazywane do modelu bez modyfikacji")
    void testPrzekazywanieDanych() {
        // Jeśli: Mock modelu
        when(mockModel.pobierzRepertuar(anyString())).thenReturn("repertuar");

        // Gdy: Wywołujemy z konkretnymi kryteriami
        clientController.przegladajRepertuar("FILM_KRYTERIA_123");

        // Wtedy: Kryteria powinny być przekazane bez zmian
        verify(mockModel).pobierzRepertuar(eq("FILM_KRYTERIA_123"));
    }

    // ========== TESTY OBSŁUGI BŁĘDÓW ==========

    @Test
    @Order(4)
    @DisplayName("Test obsługi wyjątku z modelu")
    void testWyjatekZModelu() {
        // Jeśli: Model rzuca wyjątek
        // Ref. z instrukcji: "when().thenThrow()"
        when(mockModel.pobierzRepertuar("FERROR"))
                .thenThrow(new RuntimeException("Błąd w modelu"));

        // Gdy/Wtedy: Kontroler powinien propagować wyjątek
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clientController.przegladajRepertuar("FERROR"));

        assertTrue(ex.getMessage().contains("Błąd w modelu"));
    }

    @Test
    @Order(5)
    @DisplayName("Test że wyjątek nie powoduje wielokrotnych wywołań")
    void testWyjatekNiePowodujePonownegoWywolania() {
        // Jeśli: Model rzuca wyjątek
        when(mockModel.pobierzRepertuar(anyString()))
                .thenThrow(new RuntimeException("Error"));

        // Gdy: Próba pobrania repertuaru
        try {
            clientController.przegladajRepertuar("FX");
        } catch (RuntimeException e) {
            // Oczekiwany wyjątek
        }

        // Wtedy: Model powinien być wywołany tylko raz
        // Ref. z instrukcji: "atMostOnce()"
        verify(mockModel, atMostOnce()).pobierzRepertuar(anyString());
    }

    // ========== TESTY KOLEJNOŚCI - InOrder ==========

    @Test
    @Order(6)
    @DisplayName("Test kolejności wywołań z InOrder")
    void testKolejnoscWywolan() {
        // Jeśli: Mock modelu
        // Ref. z instrukcji: "klasa InOrder w Mockito"
        when(mockModel.pobierzRepertuar(anyString())).thenReturn("repertuar");
        InOrder inOrder = inOrder(mockModel);

        // Gdy: Wywołujemy przegladajRepertuar
        clientController.przegladajRepertuar("F1");

        // Wtedy: Model.pobierzRepertuar powinien być wywołany
        inOrder.verify(mockModel).pobierzRepertuar("F1");
    }

    // ========== TESTY WERYFIKACJI NEVER ==========

    @Test
    @Order(7)
    @DisplayName("Test że inne metody modelu nie są wywoływane")
    void testNieWywolywanieInnychMetod() {
        // Jeśli: Mock modelu
        when(mockModel.pobierzRepertuar(anyString())).thenReturn("repertuar");

        // Gdy: Wywołujemy przegladajRepertuar
        clientController.przegladajRepertuar("F1");

        // Wtedy: Inne metody nie powinny być wywołane
        // Ref. z instrukcji: "never()"
        verify(mockModel, never()).dodajFilm(anyString());
        verify(mockModel, never()).usunFilm(anyString());
        verify(mockModel, never()).zarezerwujMiejsce(anyString());
        verify(mockModel, never()).dodajSeans(anyString());
    }

    // ========== TESTY PARAMETRYZOWANE ==========

    @ParameterizedTest
    @Order(8)
    @DisplayName("Test przeglądania repertuaru różnych filmów - @CsvSource")
    @CsvSource({
            "F1, Repertuar dla F1",
            "F2, Repertuar dla F2",
            "F3, Repertuar dla F3"
    })
    void testPrzegladajRepertuarParametryzowany(String idFilmu, String oczekiwanyRepertuar) {
        // Jeśli: Mock zwraca odpowiedni repertuar
        when(mockModel.pobierzRepertuar(idFilmu)).thenReturn(oczekiwanyRepertuar);

        // Gdy: Wywołujemy z parametrami
        String wynik = clientController.przegladajRepertuar(idFilmu);

        // Wtedy: Wynik powinien odpowiadać oczekiwanemu
        assertEquals(oczekiwanyRepertuar, wynik);
        verify(mockModel).pobierzRepertuar(idFilmu);
    }

    @ParameterizedTest
    @Order(9)
    @DisplayName("Test przeglądania repertuaru z różnymi komunikatami - @ValueSource")
    @ValueSource(strings = {
            "Repertuar dla filmu F1:\n  - Seans: 18:00",
            "Repertuar dla filmu F2:\n  - Seans: 20:00\n  - Seans: 22:00",
            "Brak seansow dla filmu F99"
    })
    void testRozneKomunikatyZwrotne(String komunikatZModelu) {
        // Jeśli: Model zwraca różne komunikaty
        when(mockModel.pobierzRepertuar(anyString())).thenReturn(komunikatZModelu);

        // Gdy: Wywołujemy kontroler
        String wynik = clientController.przegladajRepertuar("FX");

        // Wtedy: Komunikat powinien być przekazany bez zmian
        assertEquals(komunikatZModelu, wynik);
    }

    // ========== TESTY WIELOKROTNYCH WYWOŁAŃ ==========

    @Test
    @Order(10)
    @DisplayName("Test wielokrotnego przeglądania repertuaru")
    void testWielokrotnePrzegladanieRepertuaru() {
        // Jeśli: Model zwraca różne odpowiedzi
        when(mockModel.pobierzRepertuar("F1")).thenReturn("Repertuar F1");
        when(mockModel.pobierzRepertuar("F2")).thenReturn("Repertuar F2");
        when(mockModel.pobierzRepertuar("F3")).thenReturn("Repertuar F3");

        // Gdy: Przeglądamy 3 repertuary
        String wynik1 = clientController.przegladajRepertuar("F1");
        String wynik2 = clientController.przegladajRepertuar("F2");
        String wynik3 = clientController.przegladajRepertuar("F3");

        // Wtedy: Każdy wynik powinien być inny
        assertEquals("Repertuar F1", wynik1);
        assertEquals("Repertuar F2", wynik2);
        assertEquals("Repertuar F3", wynik3);

        // Ref. z instrukcji: "times(), atLeast()"
        verify(mockModel, times(3)).pobierzRepertuar(anyString());
        verify(mockModel, atLeast(3)).pobierzRepertuar(anyString());
    }

    @Test
    @Order(11)
    @DisplayName("Test wielokrotnego przeglądania tego samego repertuaru")
    void testWielokrotnePrzegladanieTegoSamego() {
        // Jeśli: Model zwraca ten sam wynik
        when(mockModel.pobierzRepertuar("F1")).thenReturn("Repertuar F1");

        // Gdy: Przeglądamy ten sam repertuar wielokrotnie
        String wynik1 = clientController.przegladajRepertuar("F1");
        String wynik2 = clientController.przegladajRepertuar("F1");
        String wynik3 = clientController.przegladajRepertuar("F1");

        // Wtedy: Wszystkie wyniki powinny być identyczne
        assertEquals(wynik1, wynik2);
        assertEquals(wynik2, wynik3);

        // Model powinien być wywołany 3 razy
        verify(mockModel, times(3)).pobierzRepertuar("F1");
    }

    @Test
    @Order(12)
    @DisplayName("Test weryfikacji atMost")
    void testAtMostWywolania() {
        // Jeśli: Mock modelu
        when(mockModel.pobierzRepertuar(anyString())).thenReturn("repertuar");

        // Gdy: Przeglądamy 2 repertuary
        clientController.przegladajRepertuar("F1");
        clientController.przegladajRepertuar("F2");

        // Wtedy: Weryfikacja atMost
        // Ref. z instrukcji: "atMost()"
        verify(mockModel, atMost(5)).pobierzRepertuar(anyString());
    }

    // ========== TEST ARGUMENT CAPTOR ==========

    @Test
    @Order(13)
    @DisplayName("Test przechwytywania argumentów przekazanych do modelu")
    void testPrzechwytywanieDanych() {
        // Jeśli: Mock modelu
        when(mockModel.pobierzRepertuar(anyString())).thenReturn("repertuar");

        // Gdy: Wywołujemy z konkretnymi kryteriami
        clientController.przegladajRepertuar("CAPTOR_TEST_FILM");

        // Wtedy: Weryfikujemy przekazane dane
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockModel).pobierzRepertuar(captor.capture());

        String przekazane = captor.getValue();
        assertEquals("CAPTOR_TEST_FILM", przekazane,
                "Przekazane kryteria powinny być identyczne");
    }

    // ========== TESTY EDGE CASES ==========

    @Test
    @Order(14)
    @DisplayName("Test przeglądania repertuaru z pustym ID")
    void testPrzegladajRepertuarPusteId() {
        // Jeśli: Model obsługuje puste ID
        when(mockModel.pobierzRepertuar("")).thenReturn("Brak seansow dla filmu: ");

        // Gdy: Wywołujemy z pustym ID
        String wynik = clientController.przegladajRepertuar("");

        // Wtedy: Powinniśmy otrzymać odpowiedni komunikat
        assertNotNull(wynik);
        verify(mockModel).pobierzRepertuar("");
    }

    @Test
    @Order(15)
    @DisplayName("Test gdy model zwraca null")
    void testModelZwracaNull() {
        // Jeśli: Model zwraca null
        when(mockModel.pobierzRepertuar("FNULL")).thenReturn(null);

        // Gdy: Wywołujemy kontroler
        String wynik = clientController.przegladajRepertuar("FNULL");

        // Wtedy: Wynik powinien być null (przekazany bez modyfikacji)
        assertNull(wynik, "Jeśli model zwraca null, kontroler powinien zwrócić null");
        verify(mockModel).pobierzRepertuar("FNULL");
    }

    @Test
    @Order(16)
    @DisplayName("Test że kontroler nie modyfikuje danych z modelu")
    void testNieModyfikujeDanych() {
        // Jeśli: Model zwraca konkretny tekst z wieloma liniami
        String oryginalnyRepertuar = "Repertuar dla filmu F1:\n" +
                "  - Seans: F1;2024-12-20 18:00;Sala1;100\n" +
                "  - Seans: F1;2024-12-20 21:00;Sala2;80\n";
        when(mockModel.pobierzRepertuar("F1")).thenReturn(oryginalnyRepertuar);

        // Gdy: Wywołujemy kontroler
        String wynik = clientController.przegladajRepertuar("F1");

        // Wtedy: Wynik powinien być identyczny co do znaku
        assertEquals(oryginalnyRepertuar, wynik,
                "Kontroler nie powinien modyfikować danych");
        assertEquals(oryginalnyRepertuar.length(), wynik.length(),
                "Długość tekstu powinna być identyczna");
    }
}
