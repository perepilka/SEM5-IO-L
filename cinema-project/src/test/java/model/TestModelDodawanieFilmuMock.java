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
 * Testy jednostkowe dla klasy Model - operacja dodawania filmu z użyciem
 * mockowania.
 * Testuje metodę Model.dodajFilm() z symulacją zależności (IDAO).
 * 
 * Przypadek użycia: Dodanie filmu do oferty
 * Warstwa: Encja (model)
 * Zadanie: 2 (testy z mockowaniem)
 * 
 * Ref. z instrukcji: "Symulować należy te fragmenty kodu (obiekty, operacje),
 * od których zależy testowana operacja"
 */
@DisplayName("Testy klasy Model - dodawanie filmu z mockowaniem")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Tag("encja")
@Tag("dodawanie")
@Tag("mock")
class TestModelDodawanieFilmuMock {

    /**
     * Mock obiektu DAO - symulacja warstwy dostępu do danych.
     * Ref. z instrukcji: "operacja mock() lub adnotacja @Mock"
     */
    @Mock
    private IDAO mockDao;

    /**
     * Mock obiektu Oferta (nie używany bezpośrednio w dodajFilm,
     * ale wymagany przez konstruktor Model)
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
        System.out.println("Rozpoczęcie testów Model z mockowaniem");
    }

    @BeforeEach
    void setUp() {
        // Jeśli: Inicjalizacja mocków - wykonywana automatycznie przez
        // @ExtendWith(MockitoExtension.class)
        // MockitoAnnotations.openMocks(this) - alternatywa dla @ExtendWith
    }

    @AfterEach
    void tearDown() {
        // Sprzątanie po każdym teście - resetowanie mocków
        reset(mockDao, mockOferta);
    }

    @AfterAll
    static void tearDownAfterClass() {
        // Sprzątanie po wszystkich testach
        System.out.println("Zakończenie testów Model z mockowaniem");
    }

    // ========== TESTY Z WHEN().THENRETURN() ==========

    @Test
    @Order(1)
    @DisplayName("Test dodawania filmu - sukces z mockowanym DAO")
    void testDodajFilmSukces() {
        // Jeśli: Mock DAO zwraca ID filmu po dodaniu
        // Ref. z instrukcji: "when().thenReturn() w Mockito"
        String daneFilmu = "F001;Matrix;Cyberpunk thriller;136;SciFi;28.5";
        when(mockDao.dodajFilm(anyString())).thenReturn("F001");

        // Gdy: Wywołujemy dodajFilm na modelu
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Weryfikujemy wynik i użycie mocka
        assertNotNull(wynik, "Wynik nie powinien być null");
        assertTrue(wynik.contains("pomyslnie"), "Wynik powinien zawierać 'pomyslnie'");
        assertTrue(wynik.contains("F001"), "Wynik powinien zawierać ID filmu");

        // Ref. z instrukcji: "verify(), times()"
        verify(mockDao, times(1)).dodajFilm(anyString());
    }

    @Test
    @Order(2)
    @DisplayName("Test że DAO.dodajFilm jest wywoływane dokładnie raz")
    void testDodajFilmWywolanieDaoJednorazowe() {
        // Jeśli: Przygotowanie mocka
        String daneFilmu = "F002;Avatar;Fantasy;162;SciFi;35.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("F002");

        // Gdy: Wywołujemy dodajFilm
        model.dodajFilm(daneFilmu);

        // Wtedy: DAO.dodajFilm powinno być wywołane dokładnie raz
        // Ref. z instrukcji: "verify(), times()"
        verify(mockDao, times(1)).dodajFilm(anyString());
        verify(mockDao, never()).edytujFilm(anyString());
        verify(mockDao, never()).usunFilm(anyString());
    }

    @Test
    @Order(3)
    @DisplayName("Test że DAO.dodajWpisDoLogu jest wywoływane po dodaniu filmu")
    void testDodajFilmLogowanie() {
        // Jeśli: Mock DAO zwraca ID
        String daneFilmu = "F003;Inception;Thriller;148;Thriller;32.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("F003");
        // doNothing() dla metod void - ref. z instrukcji: "doNothing().when()"
        doNothing().when(mockDao).dodajWpisDoLogu(anyString());

        // Gdy: Wywołujemy dodajFilm
        model.dodajFilm(daneFilmu);

        // Wtedy: Logowanie powinno nastąpić
        verify(mockDao).dodajWpisDoLogu(contains("F003"));
        verify(mockDao, atLeastOnce()).dodajWpisDoLogu(anyString());
    }

    // ========== TESTY KOLEJNOŚCI WYWOŁAŃ - InOrder ==========

    @Test
    @Order(4)
    @DisplayName("Test kolejności wywołań: najpierw dodajFilm, potem log")
    void testKolejnoscWywolanDAO() {
        // Jeśli: Określamy kolejność wywołań
        // Ref. z instrukcji: "klasa InOrder w Mockito"
        String daneFilmu = "F004;Titanic;Romans;195;Dramat;25.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("F004");
        InOrder inOrder = inOrder(mockDao);

        // Gdy: Wywołujemy dodajFilm
        model.dodajFilm(daneFilmu);

        // Wtedy: Najpierw dodajFilm, potem log
        inOrder.verify(mockDao).dodajFilm(anyString());
        inOrder.verify(mockDao).dodajWpisDoLogu(anyString());
    }

    // ========== TESTY Z WHEN().THENTHROW() ==========

    @Test
    @Order(5)
    @DisplayName("Test obsługi wyjątku z DAO.dodajFilm")
    void testDodajFilmWyjatekDAO() {
        // Jeśli: Mock DAO rzuca wyjątek
        // Ref. z instrukcji: "when().thenThrow() w Mockito"
        String daneFilmu = "F005;Error;Opis;120;Gatunek;20.0";
        when(mockDao.dodajFilm(anyString()))
                .thenThrow(new RuntimeException("Błąd zapisu do bazy danych"));

        // Gdy/Wtedy: Operacja powinna propagować wyjątek
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> model.dodajFilm(daneFilmu),
                "Powinien wystąpić RuntimeException");

        assertTrue(exception.getMessage().contains("Błąd zapisu"),
                "Komunikat wyjątku powinien zawierać informację o błędzie");
    }

    // ========== TESTY WERYFIKACJI PARAMETRÓW ==========

    @Test
    @Order(6)
    @DisplayName("Test że dane filmu są przekazywane do DAO w poprawnym formacie")
    void testFormatDanychPrzekzywanychDoDAO() {
        // Jeśli: Dane filmu
        String daneFilmu = "F006;TestFilm;TestOpis;100;Gatunek;20.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("F006");

        // Gdy: Wywołujemy dodajFilm
        model.dodajFilm(daneFilmu);

        // Wtedy: Weryfikujemy że dane zawierają kluczowe elementy
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockDao).dodajFilm(captor.capture());

        String przekazaneDane = captor.getValue();
        assertTrue(przekazaneDane.contains("F006"), "Dane powinny zawierać ID");
        assertTrue(przekazaneDane.contains("TestFilm"), "Dane powinny zawierać tytuł");
    }

    // ========== TESTY PARAMETRYZOWANE Z MOCKOWANIEM ==========

    @ParameterizedTest
    @Order(7)
    @DisplayName("Test dodawania różnych filmów - parametryzowany")
    @CsvSource({
            "F010, Parasite, Dramat, 132, Dramat, 28.0",
            "F011, Joker, Psychologiczny, 122, Thriller, 30.0",
            "F012, 1917, Wojenny, 119, Wojenny, 27.5"
    })
    void testDodajFilmParametryzowany(String id, String tytul, String opis,
            int czas, String gatunek, double cena) {
        // Jeśli: Mock DAO zwraca odpowiednie ID
        String daneFilmu = id + ";" + tytul + ";" + opis + ";" + czas + ";" + gatunek + ";" + cena;
        when(mockDao.dodajFilm(anyString())).thenReturn(id);

        // Gdy: Wywołujemy dodajFilm
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Film powinien być "dodany" z poprawnym ID
        assertNotNull(wynik);
        assertTrue(wynik.contains(id), "Wynik powinien zawierać ID: " + id);
        verify(mockDao).dodajFilm(contains(tytul));
    }

    @ParameterizedTest
    @Order(8)
    @DisplayName("Test dodawania filmów z różnymi ID - @ValueSource")
    @ValueSource(strings = { "F100", "F200", "F300", "F400" })
    void testDodajFilmRozneId(String id) {
        // Jeśli: Mock DAO zwraca określone ID
        String daneFilmu = id + ";Film;Opis;120;Gatunek;25.0";
        when(mockDao.dodajFilm(anyString())).thenReturn(id);

        // Gdy: Wywołujemy dodajFilm
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Wynik powinien zawierać to ID
        assertTrue(wynik.contains(id));
        verify(mockDao, atMostOnce()).dodajFilm(anyString());
    }

    // ========== TESTY WERYFIKACJI LICZBY WYWOŁAŃ ==========

    @Test
    @Order(9)
    @DisplayName("Test że dodanie wielu filmów wywołuje DAO odpowiednią liczbę razy")
    void testDodanieWieluFilmow() {
        // Jeśli: Mock DAO zwraca różne ID
        when(mockDao.dodajFilm(anyString()))
                .thenReturn("F001")
                .thenReturn("F002")
                .thenReturn("F003");

        // Gdy: Dodajemy 3 filmy
        model.dodajFilm("F001;Film1;Opis1;90;Gatunek;20.0");
        model.dodajFilm("F002;Film2;Opis2;100;Gatunek;25.0");
        model.dodajFilm("F003;Film3;Opis3;110;Gatunek;30.0");

        // Wtedy: DAO.dodajFilm powinno być wywołane 3 razy
        // Ref. z instrukcji: "atLeast(), times()"
        verify(mockDao, times(3)).dodajFilm(anyString());
        verify(mockDao, atLeast(3)).dodajWpisDoLogu(anyString());
    }

    @Test
    @Order(10)
    @DisplayName("Test że atMost weryfikuje maksymalną liczbę wywołań")
    void testAtMostWywolania() {
        // Jeśli: Mock DAO
        when(mockDao.dodajFilm(anyString())).thenReturn("FX");

        // Gdy: Dodajemy 2 filmy
        model.dodajFilm("FX;Film1;Opis;90;Gatunek;20.0");
        model.dodajFilm("FX;Film2;Opis;100;Gatunek;25.0");

        // Wtedy: Weryfikacja atMost
        // Ref. z instrukcji: "atMost(), atMostOnce()"
        verify(mockDao, atMost(5)).dodajFilm(anyString());
        verify(mockDao, atMost(5)).dodajWpisDoLogu(anyString());
    }

    // ========== TEST DORETURN().WHEN() DLA VOID ==========

    @Test
    @Order(11)
    @DisplayName("Test użycia doNothing().when() dla metody void")
    void testDoNothingDlaMetodyVoid() {
        // Jeśli: Konfigurujemy mock dla metody void
        // Ref. z instrukcji: "doNothing().when() w Mockito"
        String daneFilmu = "FV;VoidTest;Opis;90;Gatunek;15.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("FV");
        doNothing().when(mockDao).dodajWpisDoLogu(anyString());

        // Gdy: Wywołujemy dodajFilm
        String wynik = model.dodajFilm(daneFilmu);

        // Wtedy: Operacja powinna się zakończyć sukcesem
        assertNotNull(wynik);
        verify(mockDao).dodajWpisDoLogu(anyString());
    }

    // ========== TEST DOTHROW().WHEN() DLA VOID ==========

    @Test
    @Order(12)
    @DisplayName("Test użycia doThrow().when() dla metody void")
    void testDoThrowDlaMetodyVoid() {
        // Jeśli: Mock dla metody void rzuca wyjątek
        // Ref. z instrukcji: "doThrow().when() w Mockito"
        String daneFilmu = "FE;ErrorLog;Opis;90;Gatunek;15.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("FE");
        doThrow(new RuntimeException("Błąd logowania"))
                .when(mockDao).dodajWpisDoLogu(anyString());

        // Gdy/Wtedy: Wyjątek powinien być propagowany
        assertThrows(RuntimeException.class,
                () -> model.dodajFilm(daneFilmu),
                "Wyjątek z logowania powinien być propagowany");
    }

    // ========== TEST NEVER() ==========

    @Test
    @Order(13)
    @DisplayName("Test że metody niepowiązane nie są wywoływane")
    void testNeverWywolania() {
        // Jeśli: Mock DAO
        String daneFilmu = "FN;NeverTest;Opis;90;Gatunek;15.0";
        when(mockDao.dodajFilm(anyString())).thenReturn("FN");

        // Gdy: Wywołujemy dodajFilm
        model.dodajFilm(daneFilmu);

        // Wtedy: Metody edycji i usuwania nie powinny być wywołane
        // Ref. z instrukcji: "never() w Mockito"
        verify(mockDao, never()).edytujFilm(anyString());
        verify(mockDao, never()).usunFilm(anyString());
        verify(mockDao, never()).znajdzFilm(anyString());
    }
}
