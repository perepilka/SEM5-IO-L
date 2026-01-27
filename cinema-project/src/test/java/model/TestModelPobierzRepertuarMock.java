package model;

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
 * Testy jednostkowe dla klasy Model - pobieranie repertuaru z mockowaniem.
 * Testuje metodę Model.pobierzRepertuar() z symulacją zależności (IDAO).
 * 
 * Przypadek użycia: Przeglądanie repertuaru
 * Warstwa: Encja (model)
 * Zadanie: 2 (testy z mockowaniem)
 * 
 * Ref. z instrukcji: "Symulować należy te fragmenty kodu (obiekty, operacje),
 * od których zależy testowana operacja"
 */
@DisplayName("Testy klasy Model - pobieranie repertuaru z mockowaniem")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Tag("encja")
@Tag("repertuar")
@Tag("mock")
class TestModelPobierzRepertuarMock {

    /**
     * Mock obiektu DAO - symulacja warstwy dostępu do danych.
     * Ref. z instrukcji: "operacja mock() lub adnotacja @Mock"
     */
    @Mock
    private IDAO mockDao;

    /**
     * Mock obiektu Oferta (wymagany przez konstruktor Model).
     */
    @Mock
    private Oferta mockOferta;

    /**
     * Testowany obiekt Model z wstrzykniętymi symulacjami.
     * Ref. z instrukcji: "adnotacja @InjectMocks w Mockito"
     */
    @InjectMocks
    private Model model;

    @BeforeAll
    static void setUpBeforeClass() {
        // Przygotowanie przed wszystkimi testami
        System.out.println("Rozpoczęcie testów Model z mockowaniem - pobieranie repertuaru");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Inicjalizacja mocków - wykonywana automatycznie przez
        // @ExtendWith(MockitoExtension.class)
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście - resetowanie mocków
        reset(mockDao, mockOferta);
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów Model z mockowaniem - pobieranie repertuaru");
    }

    // ========== TESTY Z WHEN().THENRETURN() ==========

    @Test
    @Order(1)
    @DisplayName("Test pobierania repertuaru z seansami - sukces")
    void testPobierzRepertuarZSeansami() {
        // Jeśli: Mock DAO zwraca seanse dla filmu
        // Ref. z instrukcji: "when().thenReturn() w Mockito"
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1", "S2" });
        when(mockDao.znajdzSeans("S1")).thenReturn("F1;2024-12-20 18:00;Sala1;100");
        when(mockDao.znajdzSeans("S2")).thenReturn("F1;2024-12-20 21:00;Sala2;80");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Weryfikujemy wynik i użycie mocka
        assertNotNull(repertuar, "Repertuar nie powinien być null");
        assertTrue(repertuar.contains("Repertuar"), "Repertuar powinien zawierać nagłówek");
        assertTrue(repertuar.contains("Seans"), "Repertuar powinien zawierać seanse");

        // Ref. z instrukcji: "verify(), times()"
        verify(mockDao, times(1)).znajdzSeansyFilmu("F1");
        verify(mockDao, times(2)).znajdzSeans(anyString());
    }

    @Test
    @Order(2)
    @DisplayName("Test pobierania repertuaru bez seansów")
    void testPobierzRepertuarBezSeansow() {
        // Jeśli: Mock DAO zwraca pustą tablicę
        when(mockDao.znajdzSeansyFilmu("F99")).thenReturn(new String[] {});

        // Gdy: Pobieramy repertuar dla filmu bez seansów
        String repertuar = model.pobierzRepertuar("F99");

        // Wtedy: Powinien być komunikat o braku seansów
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"),
                "Repertuar powinien zawierać komunikat o braku seansów");

        // znajdzSeans nie powinien być wywołany
        // Ref. z instrukcji: "never()"
        verify(mockDao, never()).znajdzSeans(anyString());
    }

    @Test
    @Order(3)
    @DisplayName("Test pobierania repertuaru gdy znajdzSeansyFilmu zwraca null")
    void testPobierzRepertuarZwracaNull() {
        // Jeśli: Mock DAO zwraca null
        when(mockDao.znajdzSeansyFilmu("FNULL")).thenReturn(null);

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("FNULL");

        // Wtedy: Powinien być komunikat o braku seansów (nie wyjątek)
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"));
        verify(mockDao, never()).znajdzSeans(anyString());
    }

    @Test
    @Order(4)
    @DisplayName("Test pobierania repertuaru gdy seans nie istnieje w bazie")
    void testPobierzRepertuarSeansNieIstnieje() {
        // Jeśli: Mock DAO zwraca ID seansu, ale seans nie istnieje
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S999" });
        when(mockDao.znajdzSeans("S999")).thenReturn(null);

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar("F1");

        // Wtedy: Nie powinno być błędu, ale seans nie będzie w repertuarze
        assertNotNull(repertuar);
        verify(mockDao).znajdzSeans("S999");
    }

    // ========== TESTY KOLEJNOŚCI WYWOŁAŃ - InOrder ==========

    @Test
    @Order(5)
    @DisplayName("Test kolejności wywołań: najpierw znajdzSeansyFilmu, potem znajdzSeans")
    void testKolejnoscWywolanDAO() {
        // Jeśli: Określamy kolejność wywołań
        // Ref. z instrukcji: "klasa InOrder w Mockito"
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1" });
        when(mockDao.znajdzSeans("S1")).thenReturn("F1;2024-12-20 18:00;Sala1;100");
        InOrder inOrder = inOrder(mockDao);

        // Gdy: Pobieramy repertuar
        model.pobierzRepertuar("F1");

        // Wtedy: Najpierw szukamy seansów filmu, potem szczegóły seansu
        inOrder.verify(mockDao).znajdzSeansyFilmu("F1");
        inOrder.verify(mockDao).znajdzSeans("S1");
    }

    @Test
    @Order(6)
    @DisplayName("Test kolejności wywołań dla wielu seansów")
    void testKolejnoscWywolanWieleSeansow() {
        // Jeśli: Film ma 3 seanse
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1", "S2", "S3" });
        when(mockDao.znajdzSeans(anyString())).thenReturn("F1;2024-12-20 18:00;Sala1;100");
        InOrder inOrder = inOrder(mockDao);

        // Gdy: Pobieramy repertuar
        model.pobierzRepertuar("F1");

        // Wtedy: Najpierw znajdzSeansyFilmu, potem znajdzSeans dla każdego
        inOrder.verify(mockDao).znajdzSeansyFilmu("F1");
        inOrder.verify(mockDao).znajdzSeans("S1");
        inOrder.verify(mockDao).znajdzSeans("S2");
        inOrder.verify(mockDao).znajdzSeans("S3");
    }

    // ========== TESTY WERYFIKACJI LICZBY WYWOŁAŃ ==========

    @Test
    @Order(7)
    @DisplayName("Test że znajdzSeans jest wywoływane dla każdego ID seansu")
    void testZnajdzSeansWywolywaneDlaKazdego() {
        // Jeśli: Mock DAO zwraca 4 seanse
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1", "S2", "S3", "S4" });
        when(mockDao.znajdzSeans(anyString())).thenReturn("dane seansu");

        // Gdy: Pobieramy repertuar
        model.pobierzRepertuar("F1");

        // Wtedy: znajdzSeans powinno być wywołane 4 razy
        // Ref. z instrukcji: "times(), atLeast()"
        verify(mockDao, times(4)).znajdzSeans(anyString());
        verify(mockDao, atLeast(4)).znajdzSeans(anyString());
        verify(mockDao, atMost(4)).znajdzSeans(anyString());
    }

    @Test
    @Order(8)
    @DisplayName("Test że znajdzSeansyFilmu jest wywoływane dokładnie raz")
    void testZnajdzSeansyFilmuJednorazowo() {
        // Jeśli: Mock DAO
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1" });
        when(mockDao.znajdzSeans("S1")).thenReturn("dane");

        // Gdy: Pobieramy repertuar
        model.pobierzRepertuar("F1");

        // Wtedy: znajdzSeansyFilmu powinno być wywołane dokładnie raz
        verify(mockDao, times(1)).znajdzSeansyFilmu("F1");
        verify(mockDao, atMostOnce()).znajdzSeansyFilmu(anyString());
    }

    // ========== TESTY Z WHEN().THENTHROW() ==========

    @Test
    @Order(9)
    @DisplayName("Test obsługi wyjątku z DAO.znajdzSeansyFilmu")
    void testWyjatekZZnajdzSeansyFilmu() {
        // Jeśli: Mock DAO rzuca wyjątek
        // Ref. z instrukcji: "when().thenThrow() w Mockito"
        when(mockDao.znajdzSeansyFilmu("FERROR"))
                .thenThrow(new RuntimeException("Błąd bazy danych"));

        // Gdy/Wtedy: Operacja powinna propagować wyjątek
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> model.pobierzRepertuar("FERROR"),
                "Powinien wystąpić RuntimeException");

        assertTrue(exception.getMessage().contains("Błąd bazy danych"));
    }

    @Test
    @Order(10)
    @DisplayName("Test obsługi wyjątku z DAO.znajdzSeans")
    void testWyjatekZZnajdzSeans() {
        // Jeśli: Mock DAO rzuca wyjątek przy znajdzSeans
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1" });
        when(mockDao.znajdzSeans("S1"))
                .thenThrow(new RuntimeException("Błąd odczytu seansu"));

        // Gdy/Wtedy: Wyjątek powinien być propagowany
        assertThrows(RuntimeException.class,
                () -> model.pobierzRepertuar("F1"));
    }

    // ========== TESTY WERYFIKACJI ARGUMENTÓW ==========

    @Test
    @Order(11)
    @DisplayName("Test że poprawne ID filmu jest przekazywane do DAO")
    void testPoprawneIdFilmuPrzekazywane() {
        // Jeśli: Mock DAO
        when(mockDao.znajdzSeansyFilmu(anyString())).thenReturn(new String[] {});

        // Gdy: Pobieramy repertuar z określonym ID
        model.pobierzRepertuar("FILM_TEST_123");

        // Wtedy: DAO powinno otrzymać dokładnie to ID
        verify(mockDao).znajdzSeansyFilmu(eq("FILM_TEST_123"));
    }

    @Test
    @Order(12)
    @DisplayName("Test przechwytywania argumentów przekazanych do DAO")
    void testPrzechwytywanieDanych() {
        // Jeśli: Mock DAO
        when(mockDao.znajdzSeansyFilmu(anyString())).thenReturn(new String[] { "S1" });
        when(mockDao.znajdzSeans(anyString())).thenReturn("dane");

        // Gdy: Pobieramy repertuar
        model.pobierzRepertuar("F_CAPTOR");

        // Wtedy: Weryfikujemy przekazane ID
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockDao).znajdzSeansyFilmu(captor.capture());

        assertEquals("F_CAPTOR", captor.getValue(),
                "Przekazane ID powinno być F_CAPTOR");
    }

    // ========== TESTY PARAMETRYZOWANE ==========

    @ParameterizedTest
    @Order(13)
    @DisplayName("Test pobierania repertuaru dla różnych filmów - @CsvSource")
    @CsvSource({
            "F1, 2, 2024-12-20 18:00",
            "F2, 1, 2024-12-21 19:00",
            "F3, 3, 2024-12-22 20:00"
    })
    void testPobierzRepertuarParametryzowany(String idFilmu, int liczbaSeansow, String data) {
        // Jeśli: Mock DAO zwraca określoną liczbę seansów
        String[] seansyIds = new String[liczbaSeansow];
        for (int i = 0; i < liczbaSeansow; i++) {
            seansyIds[i] = "S" + (i + 1);
        }
        when(mockDao.znajdzSeansyFilmu(idFilmu)).thenReturn(seansyIds);
        when(mockDao.znajdzSeans(anyString())).thenReturn(idFilmu + ";" + data + ";Sala1;100");

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar(idFilmu);

        // Wtedy: Weryfikujemy wynik
        assertNotNull(repertuar);
        assertTrue(repertuar.contains(idFilmu));
        verify(mockDao, times(liczbaSeansow)).znajdzSeans(anyString());
    }

    @ParameterizedTest
    @Order(14)
    @DisplayName("Test pobierania repertuaru dla różnych ID filmów - @ValueSource")
    @ValueSource(strings = { "F1", "F10", "FILM_2024", "XYZ123" })
    void testPobierzRepertuarRozneIdFilmow(String idFilmu) {
        // Jeśli: Mock DAO zwraca pusty wynik
        when(mockDao.znajdzSeansyFilmu(idFilmu)).thenReturn(new String[] {});

        // Gdy: Pobieramy repertuar
        String repertuar = model.pobierzRepertuar(idFilmu);

        // Wtedy: Powinien być komunikat o braku seansów z ID filmu
        assertNotNull(repertuar);
        assertTrue(repertuar.contains("Brak seansow"));
        assertTrue(repertuar.contains(idFilmu));
        verify(mockDao).znajdzSeansyFilmu(idFilmu);
    }

    // ========== TESTY WIELOKROTNYCH WYWOŁAŃ ==========

    @Test
    @Order(15)
    @DisplayName("Test wielokrotnego pobierania repertuaru")
    void testWielokrotnePobieranieRepertuaru() {
        // Jeśli: Mock DAO zwraca różne wyniki
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] { "S1" });
        when(mockDao.znajdzSeansyFilmu("F2")).thenReturn(new String[] { "S2", "S3" });
        when(mockDao.znajdzSeans(anyString())).thenReturn("dane seansu");

        // Gdy: Pobieramy repertuar dla różnych filmów
        String repertuarF1 = model.pobierzRepertuar("F1");
        String repertuarF2 = model.pobierzRepertuar("F2");

        // Wtedy: Każde wywołanie powinno użyć odpowiedniego ID
        verify(mockDao).znajdzSeansyFilmu("F1");
        verify(mockDao).znajdzSeansyFilmu("F2");
        verify(mockDao, times(3)).znajdzSeans(anyString()); // 1 + 2 = 3
    }

    @Test
    @Order(16)
    @DisplayName("Test że inne metody DAO nie są wywoływane")
    void testNieWywolywanieInnychMetod() {
        // Jeśli: Mock DAO
        when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[] {});

        // Gdy: Pobieramy repertuar
        model.pobierzRepertuar("F1");

        // Wtedy: Inne metody nie powinny być wywołane
        // Ref. z instrukcji: "never()"
        verify(mockDao, never()).dodajSeans(anyString());
        verify(mockDao, never()).usunSeans(anyString());
        verify(mockDao, never()).dodajFilm(anyString());
    }
}
