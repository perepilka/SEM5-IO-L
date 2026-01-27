# Plan Testów Jednostkowych - Etap 6

## Informacje ogólne
- **Przypadki użycia**: Dodanie filmu do oferty, Przeglądanie repertuaru
- **Technologie**: JUnit6, Mockito
- **Autor**: [Imię, Nazwisko, Nr albumu]
- **Temat**: Testowanie jednostkowe operacji klas

---

# ✅ Przypadek użycia 1: Dodanie filmu do oferty - WYKONANE

## Status wykonania
| Zadanie | Status |
|---------|--------|
| Zadanie 1 (testy bez mockowania) | ✅ Wykonane |
| Zadanie 2 (testy z mockowaniem) | ✅ Wykonane |
| Zadanie 3 (zestawy testów) | ✅ Wykonane |

## Utworzone pliki testów

### Warstwa encji (model) - Zadanie 1
| Plik | Status | Opis |
|------|--------|------|
| `model/TestFilm.java` | ✅ NOWY | Test encji Film - wszystkie gettery, @CsvSource, @ValueSource |
| `model/TestFabrykaStandardowegoFilmu.java` | ✅ Zaktualizowany | Dodano @Tag("encja"), @Tag("dodawanie") |
| `model/TestDAO.java` | ✅ Zaktualizowany | Dodano @Tag("encja"), @Tag("dodawanie") |
| `model/TestModelDodawanieFilmu.java` | ✅ Zaktualizowany | Dodano @Tag("encja"), @Tag("dodawanie") |

### Warstwa kontroli (controller) - Zadanie 1
| Plik | Status | Opis |
|------|--------|------|
| `controller/TestAdminControllerDodawanieFilmu.java` | ✅ Zaktualizowany | Dodano @Tag("kontroler"), @Tag("dodawanie") |

### Warstwa encji (model) - Zadanie 2 (mockowanie)
| Plik | Status | Opis |
|------|--------|------|
| `model/TestModelDodawanieFilmuMock.java` | ✅ NOWY | Testy z @Mock, @InjectMocks, when/thenReturn, when/thenThrow, doNothing, doThrow, InOrder, verify |

### Warstwa kontroli (controller) - Zadanie 2 (mockowanie)
| Plik | Status | Opis |
|------|--------|------|
| `controller/TestAdminControllerDodawanieFilmuMock.java` | ✅ NOWY | Testy kontrolera z mockowanym IModel |
| `controller/TestDodanieNowegoFilmuMock.java` | ✅ NOWY | Testy strategii z mockowanym IModel |

### Warstwa kontroli (controller)
| Klasa | Odpowiedzialność |
|-------|------------------|
| `AdminController` | Kontroler dla operacji administratora |
| `EdytowanieOfertyKina` | Kontekst wzorca Strategy |
| `DodanieNowegoFilmu` | Strategia dodawania filmu |

---

## Zadanie 1: Testy jednostkowe bez mockowania
> **Ref. z instrukcji**: "Testy powinny bezpośrednio sprawdzać poprawność działania operacji klas bez symulacji zależności"

### Test 1.1: `TestFilm` (warstwa encji)
**Plik**: `src/test/java/model/TestFilm.java`

**Co testuję**: Klasę `Film` - encję danych filmu

**Dlaczego ta klasa**:
- Nie ma zależności od innych klas (proste testy jednostkowe)
- Zgodnie z instrukcją: "Testy klas modelujących encje danych w warstwie encji"

**Operacje do przetestowania**:
1. Konstruktor - poprawne tworzenie obiektu
2. `dajId()` - zwracanie ID
3. `dajTytul()` - zwracanie tytułu
4. `dajOpis()` - zwracanie opisu
5. `dajCzasTrwania()` - zwracanie czasu trwania
6. `dajCeneSeansow()` - zwracanie ceny
7. `dajGatunek()` - zwracanie gatunku

**Wymagane elementy** (z instrukcji):
- `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)` - określona kolejność testów
- `@BeforeEach` - przygotowanie danych przed każdym testem
- Minimum 3 różne asercje: `assertEquals`, `assertNotNull`, `assertTrue`
- Minimum 2 sposoby parametryzacji: `@CsvSource`, `@ValueSource` lub `@MethodSource`
- Komentarze etapów: `// Jeśli:`, `// Gdy:`, `// Wtedy:`

**Przykładowe testy**:
```java
@Test
@Order(1)
void testTworzenieFilmu() {
    // Jeśli: Dane filmu
    String id = "F001";
    String tytul = "Matrix";
    ...
    
    // Gdy: Tworzymy film
    Film film = new Film(id, tytul, opis, czas, gatunek, cena);
    
    // Wtedy: Wszystkie pola powinny być poprawne
    assertEquals("F001", film.dajId());
    assertNotNull(film.dajTytul());
    assertTrue(film.dajCeneSeansow() > 0);
}

@ParameterizedTest
@CsvSource({"F1,Matrix,120,25.0", "F2,Inception,148,30.0"})
void testFilmyZRoznymiDanymi(String id, String tytul, int czas, double cena) {
    // ...
}

@ParameterizedTest
@ValueSource(doubles = {10.0, 20.5, 35.99})
void testFilmyZRoznyCena(double cena) {
    // ...
}
```

---

### Test 1.2: `TestFabrykaStandardowegoFilmu` (warstwa encji)
**Plik**: `src/test/java/model/TestFabrykaStandardowegoFilmu.java` ✅ (już istnieje)

**Status**: Ten test już istnieje w projekcie. Należy sprawdzić czy spełnia wszystkie wymagania:
- ✅ Ma `@TestMethodOrder`
- ✅ Ma `@BeforeEach`
- ✅ Ma komentarze etapów
- ✅ Ma parametryzację `@CsvSource` i `@MethodSource`
- ✅ Używa różnych asercji

**Ewentualne uzupełnienia**:
- Dodać tag `@Tag("encja")` dla zestawu testów

---

### Test 1.3: `TestDAO` (warstwa encji)
**Plik**: `src/test/java/model/TestDAO.java` ✅ (już istnieje)

**Status**: Ten test już istnieje. Testuje:
- `dodajFilm()` - ✅
- `znajdzFilm()` - ✅
- `dodajWpisDoLogu()` - ✅

**Ewentualne uzupełnienia**:
- Dodać tag `@Tag("encja")` dla zestawu testów

---

### Test 1.4: `TestModelDodawanieFilmu` (warstwa encji)
**Plik**: `src/test/java/model/TestModelDodawanieFilmu.java` ✅ (już istnieje)

**Status**: Test istnieje, ale używa **prawdziwych** obiektów (DAO, Oferta) - odpowiedni dla Zadania 1.

**Ewentualne uzupełnienia**:
- Dodać tag `@Tag("encja")` dla zestawu testów

---

### Test 1.5: `TestAdminControllerDodawanieFilmu` (warstwa kontroli)
**Plik**: `src/test/java/controller/TestAdminControllerDodawanieFilmu.java` ✅ (już istnieje)

**Status**: Test istnieje i testuje pełny przepływ przez kontroler.

**Ewentualne uzupełnienia**:
- Dodać tag `@Tag("kontroler")` dla zestawu testów

---

## Zadanie 2: Testy jednostkowe z mockowaniem

> **Ref. z instrukcji**: "Symulować należy te fragmenty kodu (obiekty, operacje), od których zależy testowana operacja"

### Test 2.1: `TestModelDodawanieFilmuMock` (warstwa encji)
**Plik**: `src/test/java/model/TestModelDodawanieFilmuMock.java` ⚠️ (do utworzenia)

**Co testuję**: Klasę `Model.dodajFilm()` z mockowaniem zależności

**Zależności do zamockowania** (zgodnie z diagramem klas):
- `IDAO dao` - mockujemy operacje zapisu do bazy
- `Oferta oferta` - mockujemy agregację danych

**Dlaczego mockowanie**:
- `Model.dodajFilm()` zależy od `DAO.dodajFilm()` i `DAO.dodajWpisDoLogu()`
- Zgodnie z instrukcją: "Symulować należy te fragmenty kodu, od których zależy testowana operacja"

**Wymagane elementy Mockito** (z instrukcji):
```java
@Mock
private IDAO mockDao;

@InjectMocks
private Model model;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}
```

**Scenariusze testowe**:

1. **Test sukcesu dodawania filmu**:
   ```java
   // Jeśli: Mock DAO zwraca ID
   when(mockDao.dodajFilm(anyString())).thenReturn("F001");
   doNothing().when(mockDao).dodajWpisDoLogu(anyString());
   
   // Gdy: Wywołujemy dodajFilm
   String wynik = model.dodajFilm(daneFilmu);
   
   // Wtedy: Sprawdzamy użycie mocka
   verify(mockDao, times(1)).dodajFilm(anyString());
   verify(mockDao, times(1)).dodajWpisDoLogu(contains("F001"));
   ```

2. **Test obsługi wyjątku z DAO**:
   ```java
   // Jeśli: Mock DAO rzuca wyjątek
   when(mockDao.dodajFilm(anyString())).thenThrow(new RuntimeException("DB Error"));
   
   // Gdy/Wtedy: Operacja powinna obsłużyć wyjątek
   assertThrows(RuntimeException.class, () -> model.dodajFilm(daneFilmu));
   ```

3. **Test kolejności wywołań**:
   ```java
   // Jeśli: Określamy kolejność wywołań
   InOrder inOrder = inOrder(mockDao);
   
   // Gdy: Dodajemy film
   model.dodajFilm(daneFilmu);
   
   // Wtedy: Weryfikujemy kolejność
   inOrder.verify(mockDao).dodajFilm(anyString());
   inOrder.verify(mockDao).dodajWpisDoLogu(anyString());
   ```

---

### Test 2.2: `TestAdminControllerDodawanieFilmuMock` (warstwa kontroli)  
**Plik**: `src/test/java/controller/TestAdminControllerDodawanieFilmuMock.java` ⚠️ (do utworzenia)

**Co testuję**: Klasę `AdminController.dodajFilm()` z mockowaniem Model

**Zależności do zamockowania**:
- `IModel model` - mockujemy warstwę modelu

**Dlaczego mockowanie**:
- Testujemy tylko logikę kontrolera
- Izolujemy od warstwy modelu (zgodnie z zasadą testów jednostkowych)

**Wymagane elementy Mockito**:
```java
@Mock
private IModel mockModel;

private AdminController adminController;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    adminController = new AdminController(mockModel);
}
```

**Scenariusze testowe**:

1. **Test wywołania strategii**:
   ```java
   // Jeśli: Mock Model zwraca sukces
   when(mockModel.dodajFilm(anyString())).thenReturn("Film dodany pomyslnie. ID: F001");
   
   // Gdy: Wywołujemy dodajFilm przez kontroler
   String wynik = adminController.dodajFilm(daneFilmu);
   
   // Wtedy: Model powinien być wywołany raz
   verify(mockModel, times(1)).dodajFilm(daneFilmu);
   ```

2. **Test że kontroler przekazuje dane bez modyfikacji**:
   ```java
   // Jeśli: Dane filmu
   String daneFilmu = "F001;Matrix;Opis;120;SciFi;25.0";
   when(mockModel.dodajFilm(daneFilmu)).thenReturn("OK");
   
   // Gdy: Wywołujemy
   adminController.dodajFilm(daneFilmu);
   
   // Wtedy: Dane przekazane do modelu powinny być identyczne
   verify(mockModel).dodajFilm(eq(daneFilmu));
   ```

3. **Test obsługi błędu z modelu**:
   ```java
   // Jeśli: Model rzuca wyjątek
   when(mockModel.dodajFilm(anyString())).thenThrow(new RuntimeException("Error"));
   
   // Gdy/Wtedy: Kontroler powinien propagować wyjątek
   assertThrows(RuntimeException.class, () -> adminController.dodajFilm(daneFilmu));
   verify(mockModel, atMostOnce()).dodajFilm(anyString());
   ```

---

### Test 2.3: `TestDodanieNowegoFilmuMock` (warstwa kontroli)
**Plik**: `src/test/java/controller/TestDodanieNowegoFilmuMock.java` ⚠️ (do utworzenia)

**Co testuję**: Strategię `DodanieNowegoFilmu.edytujOferte()`

**Zależności do zamockowania**:
- `IModel model` - mockujemy model

**Scenariusze testowe**:
1. Test że strategia wywołuje `model.dodajFilm()`
2. Test że strategia przekazuje dane bez modyfikacji
3. Test obsługi błędu z modelu

---

# Przypadek użycia 2: Przeglądanie repertuaru

## Opis przepływu operacji
Przepływ przeglądania repertuaru przebiega następująco:
1. `ClientController.przegladajRepertuar(kryteria)` → wywołuje `Model.pobierzRepertuar()`
2. `Model.pobierzRepertuar(kryteria)`:
   - Wywołuje `dao.znajdzSeansyFilmu(kryteria)` → zwraca tablicę ID seansów
   - Dla każdego seansu wywołuje `dao.znajdzSeans(seansId)` → pobiera dane seansu
   - Buduje sformatowany string repertuaru
   - Zwraca repertuar lub komunikat "Brak seansów"

## Klasy zaangażowane (według warstw)

### Warstwa encji (model)
| Klasa | Odpowiedzialność |
|-------|------------------|
| `Seans` | Encja danych seansu |
| `DAO` | Wyszukiwanie seansów i filmów |
| `Model` | Pobieranie i formatowanie repertuaru |

### Warstwa kontroli (controller)
| Klasa | Odpowiedzialność |
|-------|------------------|
| `ClientController` | Kontroler dla klienta |

---

## Zadanie 1: Testy jednostkowe bez mockowania

### Test 1.6: `TestSeans` (warstwa encji)
**Plik**: `src/test/java/model/TestSeans.java` ⚠️ (do utworzenia)

**Co testuję**: Klasę `Seans` - encję danych seansu

**Operacje do przetestowania**:
1. Konstruktor
2. Gettery: `dajId()`, `dajIdFilmu()`, `dajDataCzas()`, `dajSala()`, `dajMiejscaDostepne()`

**Wymagane elementy**:
- `@TestMethodOrder`
- `@BeforeEach`
- Minimum 3 asercje
- Minimum 2 sposoby parametryzacji

---

### Test 1.7: `TestDAOSeansy` (warstwa encji)
**Plik**: `src/test/java/model/TestDAOSeansy.java` ⚠️ (do utworzenia)

**Co testuję**: Operacje DAO związane z seansami

**Operacje do przetestowania**:
1. `dodajSeans()` - dodawanie seansu i generowanie ID
2. `znajdzSeans()` - wyszukiwanie seansu po ID
3. `znajdzSeansyFilmu()` - wyszukiwanie seansów dla filmu

**Przykładowe testy**:
```java
@Test
void testDodajSeansGenerujeId() {
    // Jeśli: Dane seansu
    String daneSeansu = "F1;2024-12-20 18:00;Sala1;100";
    
    // Gdy: Dodajemy seans
    String id = dao.dodajSeans(daneSeansu);
    
    // Wtedy: ID powinno być wygenerowane
    assertNotNull(id);
    assertTrue(id.startsWith("S"));
}

@Test
void testZnajdzSeansyFilmu() {
    // Jeśli: Dodano kilka seansów dla tego samego filmu
    dao.dodajSeans("F1;2024-12-20 18:00;Sala1;100");
    dao.dodajSeans("F1;2024-12-20 21:00;Sala2;80");
    dao.dodajSeans("F2;2024-12-21 19:00;Sala1;100");
    
    // Gdy: Szukamy seansów dla filmu F1
    String[] seansyF1 = dao.znajdzSeansyFilmu("F1");
    
    // Wtedy: Powinny być 2 seanse
    assertEquals(2, seansyF1.length);
}
```

---

### Test 1.8: `TestModelPobierzRepertuar` (warstwa encji)
**Plik**: `src/test/java/model/TestModelPobierzRepertuar.java` ⚠️ (do utworzenia)

**Co testuję**: `Model.pobierzRepertuar()` bez mockowania (z prawdziwym DAO)

**Scenariusze testowe**:
1. Pobieranie repertuaru gdy są seanse dla filmu
2. Pobieranie repertuaru gdy brak seansów dla filmu
3. Formatowanie wyniku
4. Obsługa pustych kryteriów

---

### Test 1.9: `TestClientControllerPrzeglądanieRepertuaru` (warstwa kontroli)
**Plik**: `src/test/java/controller/TestClientControllerPrzegladanieRepertuaru.java` ⚠️ (do utworzenia)

**Co testuję**: `ClientController.przegladajRepertuar()` - pełny przepływ

---

## Zadanie 2: Testy jednostkowe z mockowaniem

### Test 2.4: `TestModelPobierzRepertuarMock` (warstwa encji)
**Plik**: `src/test/java/model/TestModelPobierzRepertuarMock.java` ⚠️ (do utworzenia)

**Co testuję**: `Model.pobierzRepertuar()` z mockowaniem DAO

**Zależności do zamockowania**:
- `IDAO dao` - mockujemy wyszukiwanie seansów

**Scenariusze testowe**:

1. **Test pobierania repertuaru z seansami**:
   ```java
   // Jeśli: Mock DAO zwraca seanse
   when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[]{"S1", "S2"});
   when(mockDao.znajdzSeans("S1")).thenReturn("F1;2024-12-20 18:00;Sala1;100");
   when(mockDao.znajdzSeans("S2")).thenReturn("F1;2024-12-20 21:00;Sala2;80");
   
   // Gdy: Pobieramy repertuar
   String repertuar = model.pobierzRepertuar("F1");
   
   // Wtedy: Sprawdzamy wynik
   verify(mockDao).znajdzSeansyFilmu("F1");
   verify(mockDao, times(2)).znajdzSeans(anyString());
   assertTrue(repertuar.contains("Seans"));
   ```

2. **Test pobierania repertuaru bez seansów**:
   ```java
   // Jeśli: Mock DAO zwraca pustą tablicę
   when(mockDao.znajdzSeansyFilmu("F99")).thenReturn(new String[]{});
   
   // Gdy: Pobieramy repertuar
   String repertuar = model.pobierzRepertuar("F99");
   
   // Wtedy: Komunikat o braku seansów
   assertTrue(repertuar.contains("Brak seansow"));
   verify(mockDao, never()).znajdzSeans(anyString());
   ```

3. **Test pobierania repertuaru gdy seans nie istnieje**:
   ```java
   // Jeśli: Mock DAO zwraca ID seansu, ale seans nie istnieje
   when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[]{"S999"});
   when(mockDao.znajdzSeans("S999")).thenReturn(null);
   
   // Gdy: Pobieramy repertuar
   String repertuar = model.pobierzRepertuar("F1");
   
   // Wtedy: Nie powinno być błędu
   assertNotNull(repertuar);
   ```

4. **Test kolejności wywołań DAO**:
   ```java
   // Jeśli: InOrder dla sprawdzenia kolejności
   InOrder inOrder = inOrder(mockDao);
   when(mockDao.znajdzSeansyFilmu("F1")).thenReturn(new String[]{"S1"});
   when(mockDao.znajdzSeans("S1")).thenReturn("dane seansu");
   
   // Gdy: Pobieramy repertuar
   model.pobierzRepertuar("F1");
   
   // Wtedy: Najpierw szukamy seansów filmu, potem szczegóły seansu
   inOrder.verify(mockDao).znajdzSeansyFilmu("F1");
   inOrder.verify(mockDao).znajdzSeans("S1");
   ```

---

### Test 2.5: `TestClientControllerPrzegladanieRepertuaruMock` (warstwa kontroli)
**Plik**: `src/test/java/controller/TestClientControllerPrzegladanieRepertuaruMock.java` ⚠️ (do utworzenia)

**Co testuję**: `ClientController.przegladajRepertuar()` z mockowaniem Model

**Zależności do zamockowania**:
- `IModel model` - mockujemy pobranie repertuaru

**Scenariusze testowe**:

1. **Test delegacji do modelu**:
   ```java
   // Jeśli: Mock Model zwraca repertuar
   when(mockModel.pobierzRepertuar("F1")).thenReturn("Repertuar dla filmu F1...");
   
   // Gdy: Wywołujemy przez kontroler
   String wynik = clientController.przegladajRepertuar("F1");
   
   // Wtedy: Model powinien być wywołany z tym samym parametrem
   verify(mockModel, times(1)).pobierzRepertuar("F1");
   assertEquals("Repertuar dla filmu F1...", wynik);
   ```

2. **Test przekazywania wyniku bez modyfikacji**:
   ```java
   // Jeśli: Model zwraca określony string
   String oczekiwanyWynik = "Szczegółowy repertuar";
   when(mockModel.pobierzRepertuar(anyString())).thenReturn(oczekiwanyWynik);
   
   // Gdy: Wywołujemy kontroler
   String wynik = clientController.przegladajRepertuar("F1");
   
   // Wtedy: Wynik powinien być identyczny
   assertSame(oczekiwanyWynik, wynik);
   ```

---

## Zadanie 3: Zestawy testów (Test Suites)

> **Ref. z instrukcji**: "Zestawy testów oznaczonych wybranymi tagami"

### Wymagane zestawy

#### 3.1 `SuiteEncji.java`
**Plik**: `src/test/java/model/SuiteEncji.java` ⚠️ (do utworzenia)

```java
@Suite
@SuiteDisplayName("Zestaw testów warstwy encji")
@SelectPackages("model")
// lub
@IncludeTags("encja")
public class SuiteEncji {
}
```

**Testy włączone**:
- `TestFilm`
- `TestSeans`
- `TestDAO`
- `TestDAOSeansy`
- `TestFabrykaStandardowegoFilmu`
- `TestModelDodawanieFilmu`
- `TestModelPobierzRepertuar`
- `TestModelDodawanieFilmuMock`
- `TestModelPobierzRepertuarMock`

---

#### 3.2 `SuiteKontroli.java`
**Plik**: `src/test/java/controller/SuiteKontroli.java` ⚠️ (do utworzenia)

```java
@Suite
@SuiteDisplayName("Zestaw testów warstwy kontroli")
@SelectPackages("controller")
// lub
@IncludeTags("kontroler")
public class SuiteKontroli {
}
```

**Testy włączone**:
- `TestAdminControllerDodawanieFilmu`
- `TestAdminControllerDodawanieFilmuMock`
- `TestDodanieNowegoFilmuMock`
- `TestClientControllerPrzegladanieRepertuaru`
- `TestClientControllerPrzegladanieRepertuaruMock`

---

#### 3.3 `SuiteDodawanieFilmu.java` (tagi: "dodawanie", nie "mock")
**Plik**: `src/test/java/SuiteDodawanieFilmu.java` ⚠️ (do utworzenia)

**Praktyczne zastosowanie**: Uruchamianie wszystkich testów związanych z dodawaniem filmu BEZ mockowania - przydatne do testów integracyjnych.

```java
@Suite
@SuiteDisplayName("Zestaw testów dodawania filmu bez mockowania")
@IncludeTags("dodawanie")
@ExcludeTags("mock")
@SelectPackages({"model", "controller"})
public class SuiteDodawanieFilmu {
}
```

---

#### 3.4 `SuiteRepertuar.java` (tagi: "repertuar", nie "mock")
**Plik**: `src/test/java/SuiteRepertuar.java` ⚠️ (do utworzenia)

**Praktyczne zastosowanie**: Uruchamianie wszystkich testów związanych z przeglądaniem repertuaru BEZ mockowania.

```java
@Suite
@SuiteDisplayName("Zestaw testów przeglądania repertuaru bez mockowania")
@IncludeTags("repertuar")
@ExcludeTags("mock")
@SelectPackages({"model", "controller"})
public class SuiteRepertuar {
}
```

---

## Tagi do użycia w testach

| Tag | Opis | Przykład użycia |
|-----|------|-----------------|
| `@Tag("encja")` | Testy warstwy encji | `TestFilm`, `TestDAO` |
| `@Tag("kontroler")` | Testy warstwy kontroli | `TestAdminController` |
| `@Tag("dodawanie")` | Testy przypadku "Dodanie filmu" | Wszystkie testy dodawania |
| `@Tag("repertuar")` | Testy przypadku "Przeglądanie repertuaru" | Wszystkie testy repertuaru |
| `@Tag("mock")` | Testy z mockowaniem | `TestModelMock` |
| `@Tag("integracja")` | Testy integracyjne | Testy pełnego przepływu |

---

## Podsumowanie: Lista plików do utworzenia

### Zadanie 1 (bez mockowania)
| Plik | Status |
|------|--------|
| `model/TestFilm.java` | ⚠️ Do utworzenia |
| `model/TestFabrykaStandardowegoFilmu.java` | ✅ Istnieje |
| `model/TestDAO.java` | ✅ Istnieje |
| `model/TestModelDodawanieFilmu.java` | ✅ Istnieje |
| `controller/TestAdminControllerDodawanieFilmu.java` | ✅ Istnieje |
| `model/TestSeans.java` | ⚠️ Do utworzenia |
| `model/TestDAOSeansy.java` | ⚠️ Do utworzenia |
| `model/TestModelPobierzRepertuar.java` | ⚠️ Do utworzenia |
| `controller/TestClientControllerPrzegladanieRepertuaru.java` | ⚠️ Do utworzenia |

### Zadanie 2 (z mockowaniem)
| Plik | Status |
|------|--------|
| `model/TestModelDodawanieFilmuMock.java` | ⚠️ Do utworzenia |
| `controller/TestAdminControllerDodawanieFilmuMock.java` | ⚠️ Do utworzenia |
| `controller/TestDodanieNowegoFilmuMock.java` | ⚠️ Do utworzenia |
| `model/TestModelPobierzRepertuarMock.java` | ⚠️ Do utworzenia |
| `controller/TestClientControllerPrzegladanieRepertuaruMock.java` | ⚠️ Do utworzenia |

### Zadanie 3 (zestawy testów)
| Plik | Status |
|------|--------|
| `model/SuiteEncji.java` | ⚠️ Do utworzenia |
| `controller/SuiteKontroli.java` | ⚠️ Do utworzenia |
| `SuiteDodawanieFilmu.java` | ⚠️ Do utworzenia |
| `SuiteRepertuar.java` | ⚠️ Do utworzenia |

---

## Wymagania z instrukcji - Checklist

### Zadanie 1
- [ ] Jednoznacznie określona kolejność testów (`@TestMethodOrder`)
- [ ] Przygotowanie danych przed każdym testem (`@BeforeEach`)
- [ ] Ponowne przygotowanie danych do kolejnego testu
- [ ] Minimum 3 różne asercje
- [ ] Minimum 2 różne sposoby parametryzacji

### Zadanie 2
- [ ] `mock()` lub `@Mock` + `MockitoAnnotations.initMocks()`
- [ ] `@InjectMocks` dla wstrzykiwania symulacji
- [ ] `when().thenReturn()` dla operacji zwracających wartość
- [ ] `when().thenThrow()` dla symulacji wyjątków
- [ ] `doReturn().when()` lub `doNothing().when()` dla operacji void
- [ ] `InOrder` dla sprawdzenia kolejności
- [ ] `verify()`, `times()`, `never()`, `atLeast()`, `atMost()` dla weryfikacji

### Zadanie 3
- [ ] `@Suite` + `@SuiteDisplayName`
- [ ] `@SelectPackages` lub `@SelectClasses`
- [ ] `@IncludeTags` + `@ExcludeTags`
- [ ] Zestawy mają praktyczne zastosowanie

### Formatowanie sprawozdania
- [ ] Komentarze do etapów testów (`// Jeśli:`, `// Gdy:`, `// Wtedy:`)
- [ ] Numeracja linii
- [ ] Wcięcia i formatowanie
- [ ] Pełne nazwy plików z pakietami
