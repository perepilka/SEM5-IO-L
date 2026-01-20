# Testy jednostkowe - Dodawanie filmu

## Utworzone testy

Zgodnie z instrukcją utworzono testy w kolejności od operacji niezależnych do zależnych:

### 1. Warstwa Model - Klasy niezależne (Encje)

#### test/model/TestFabrykaStandardowegoFilmu.java
**Testowana klasa:** `model.FabrykaStandardowegoFilmu`
**Zależności:** Tylko Film (podstawowa encja)
**Operacje testowane:**
- Tworzenie filmu z danych CSV
- Parsowanie danych wejściowych
- Poprawność utworzonych obiektów Film

**Wykorzystane funkcje JUnit:**
- `@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll` - przygotowanie i sprzątanie
- `@Order` - kolejność wykonania testów
- `assertEquals()`, `assertNotNull()`, `assertTrue()`, `assertFalse()`, `assertThrows()` - różne asercje
- `@ParameterizedTest` z `@CsvSource` - parametryzacja z wartościami CSV
- `@ParameterizedTest` z `@MethodSource` - parametryzacja z metody źródłowej

#### test/model/TestDAO.java
**Testowana klasa:** `model.DAO`
**Zależności:** Brak (niezależna warstwa dostępu do danych)
**Operacje testowane:**
- Dodawanie filmu do bazy
- Wyszukiwanie filmu po ID
- Automatyczne generowanie ID
- Dodawanie wpisów do logu

**Wykorzystane funkcje JUnit:**
- `@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll`
- `@Order`, `@TestMethodOrder`
- `assertEquals()`, `assertNotNull()`, `assertNull()`, `assertNotEquals()`, `assertTrue()`, `assertDoesNotThrow()`
- `@ParameterizedTest` z `@CsvSource`
- `@ParameterizedTest` z `@ValueSource`

### 2. Warstwa Model - Klasy zależne (Logika biznesowa)

#### test/model/TestModelDodawanieFilmu.java
**Testowana klasa:** `model.Model`
**Zależności:** DAO, Oferta, FabrykaStandardowegoFilmu
**Operacje testowane:**
- Dodawanie filmu przez warstwę modelu
- Integracja z fabryką filmów
- Integracja z DAO
- Format komunikatów zwrotnych
- Dodawanie wielu filmów

**Wykorzystane funkcje JUnit:**
- `@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll`
- `@Order`, `@TestMethodOrder`
- `assertEquals()`, `assertNotNull()`, `assertTrue()`, `assertFalse()`, `assertNotEquals()`
- `@ParameterizedTest` z `@CsvSource`
- `@ParameterizedTest` z `@MethodSource`

### 3. Warstwa Controller - Klasy zależne (Usługi biznesowe)

#### test/controller/TestAdminControllerDodawanieFilmu.java
**Testowana klasa:** `controller.AdminController`
**Zależności:** Model, DAO, Oferta, EdytowanieOfertyKina, DodanieNowegoFilmu
**Operacje testowane:**
- Dodawanie filmu przez kontroler administratora
- Wzorzec Strategy (EdytowanieOfertyKina)
- Pełny przepływ: Controller -> Strategy -> Model -> Fabryka -> DAO
- Weryfikacja komunikatów
- Niezmienność danych

**Wykorzystane funkcje JUnit:**
- `@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll`
- `@Order`, `@TestMethodOrder`
- `assertEquals()`, `assertNotNull()`, `assertTrue()`, `assertFalse()`
- `@ParameterizedTest` z `@CsvSource`
- `@ParameterizedTest` z `@ValueSource`

## Uruchomienie testów

### Wymagania:
1. JUnit 6 (Jupiter) w classpath
2. Java 8 lub wyższa

### Instrukcja kompilacji i uruchomienia:

```bash
# 1. Pobierz JUnit 6 (jeśli nie ma):
mkdir -p lib
cd lib
wget https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter/5.10.1/junit-jupiter-5.10.1.jar
wget https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar
cd ..

# 2. Kompiluj źródła:
javac -d bin src/model/*.java src/controller/*.java

# 3. Kompiluj testy:
javac -cp "bin:lib/*" -d bin test/model/*.java test/controller/*.java

# 4. Uruchom wszystkie testy:
java -jar lib/junit-platform-console-standalone-1.10.1.jar \
  --class-path bin \
  --scan-class-path

# 5. Lub uruchom pojedyncze klasy testów:
java -jar lib/junit-platform-console-standalone-1.10.1.jar \
  --class-path bin \
  --select-class model.TestFabrykaStandardowegoFilmu

java -jar lib/junit-platform-console-standalone-1.10.1.jar \
  --class-path bin \
  --select-class model.TestDAO

java -jar lib/junit-platform-console-standalone-1.10.1.jar \
  --class-path bin \
  --select-class model.TestModelDodawanieFilmu

java -jar lib/junit-platform-console-standalone-1.10.1.jar \
  --class-path bin \
  --select-class controller.TestAdminControllerDodawanieFilmu
```

## Struktura testów

Każdy test składa się z trzech etapów (komentarze w kodzie):

1. **Jeśli (Given):** Przygotowanie danych testowych
2. **Gdy (When):** Wykonanie testowanej operacji
3. **Wtedy (Then):** Weryfikacja wyniku za pomocą asercji

## Kolejność testowania

Testy wykonywane są w kolejności zgodnej z architekturą n-warstwową:

1. **Warstwa niezależna (Encje):**
   - FabrykaStandardowegoFilmu
   - DAO

2. **Warstwa zależna od encji (Model biznesowy):**
   - Model (używa Fabryki i DAO)

3. **Warstwa kontrolera (Usługi biznesowe):**
   - AdminController (używa Model, Strategy)

## Wykorzystane wzorce testowania

- **Testy jednostkowe bez mockowania** - wszystkie zależności są rzeczywiste
- **Parametryzacja testów** - różne wartości wejściowe (@CsvSource, @ValueSource, @MethodSource)
- **Setup/Teardown** - przygotowanie i czyszczenie przed/po testach
- **Różnorodne asercje** - 8 różnych typów asercji
- **Kolejność wykonania** - kontrolowana przez @Order
- **Nazwy opisowe** - @DisplayName dla lepszej czytelności
