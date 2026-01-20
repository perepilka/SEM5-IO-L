# Cinema Management System - Maven Project

## Struktura projektu

```
cinema-project/
├── pom.xml                          # Maven configuration
├── README.md                        # Ten plik
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── model/               # Warstwa modelu (18 klas)
│   │       │   ├── Film.java
│   │       │   ├── Seans.java
│   │       │   ├── Klient.java
│   │       │   ├── Rezerwacja.java
│   │       │   ├── DAO.java
│   │       │   ├── Model.java
│   │       │   ├── Oferta.java
│   │       │   ├── FabrykaStandardowegoFilmu.java
│   │       │   └── ...
│   │       └── controller/          # Warstwa kontrolera (10 klas)
│   │           ├── AdminController.java
│   │           ├── ClientController.java
│   │           ├── DodanieNowegoFilmu.java
│   │           ├── EdytowanieOfertyKina.java
│   │           └── ...
│   └── test/
│       └── java/
│           ├── model/               # Testy warstwy modelu
│           │   ├── TestFabrykaStandardowegoFilmu.java
│           │   ├── TestDAO.java
│           │   └── TestModelDodawanieFilmu.java
│           └── controller/          # Testy warstwy kontrolera
│               └── TestAdminControllerDodawanieFilmu.java
└── target/                          # Skompilowane pliki (generowane)
```

## Wymagania

- **Java 11 lub wyższa** (projekt używa Java 21)
- **Maven 3.6 lub wyższa**

## Instalacja Maven (jeśli nie zainstalowany)

### Ubuntu/Debian:
```bash
sudo apt update
sudo apt install maven
```

### Weryfikacja:
```bash
mvn --version
```

## Budowanie projektu

```bash
cd cinema-project

# Kompilacja kodu źródłowego
mvn compile

# Kompilacja testów
mvn test-compile

# Uruchomienie wszystkich testów
mvn test

# Czyszczenie + kompilacja + testy
mvn clean test

# Pełne budowanie z pakietowaniem
mvn clean package
```

## Uruchomienie testów

### Wszystkie testy:
```bash
mvn test
```

### Konkretna klasa testowa:
```bash
mvn test -Dtest=TestFabrykaStandardowegoFilmu
mvn test -Dtest=TestDAO
mvn test -Dtest=TestModelDodawanieFilmu
mvn test -Dtest=TestAdminControllerDodawanieFilmu
```

### Wszystkie testy z pakietu:
```bash
# Wszystkie testy warstwy model
mvn test -Dtest=model.**

# Wszystkie testy warstwy controller
mvn test -Dtest=controller.**
```

## Bez Maven (kompilacja ręczna)

Jeśli Maven nie jest dostępny, można skompilować ręcznie:

```bash
cd cinema-project

# 1. Pobierz JUnit
mkdir -p lib
wget -P lib https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar

# 2. Kompiluj źródła
mkdir -p target/classes
javac -d target/classes src/main/java/model/*.java src/main/java/controller/*.java

# 3. Kompiluj testy
mkdir -p target/test-classes
javac -cp "target/classes:lib/*" -d target/test-classes src/test/java/model/*.java src/test/java/controller/*.java

# 4. Uruchom testy
java -jar lib/junit-platform-console-standalone-1.10.1.jar \
  --class-path target/classes:target/test-classes \
  --scan-class-path
```

## Utworzone klasy testowe

### 1. Warstwa Model - Klasy niezależne
- **TestFabrykaStandardowegoFilmu** (5 testów + parametryzowane)
  - Testuje tworzenie filmów z danych CSV
  - Weryfikuje parsowanie danych
  
- **TestDAO** (7 testów + parametryzowane)
  - Testuje dodawanie filmów do bazy
  - Testuje wyszukiwanie po ID
  - Testuje automatyczne generowanie ID

### 2. Warstwa Model - Klasy zależne
- **TestModelDodawanieFilmu** (7 testów + parametryzowane)
  - Testuje integrację Fabryka + DAO
  - Testuje logikę biznesową
  - Weryfikuje komunikaty

### 3. Warstwa Controller
- **TestAdminControllerDodawanieFilmu** (9 testów + parametryzowane)
  - Testuje pełny przepływ przez kontroler
  - Weryfikuje wzorzec Strategy
  - Testuje integrację wszystkich warstw

**Łącznie: 28+ testów** (z parametryzowanymi wariantami: 50+ wykonań)

## Zgodność z wymaganiami instrukcji

✅ **Kolejność testowania**: Od operacji niezależnych do zależnych  
✅ **@BeforeAll, @BeforeEach**: Przygotowanie danych  
✅ **@AfterEach, @AfterAll**: Sprzątanie  
✅ **@Order, @TestMethodOrder**: Kontrolowana kolejność  
✅ **@DisplayName**: Opisowe nazwy testów  
✅ **Co najmniej 3 asercje**: 8 różnych typów użytych:
  - assertEquals()
  - assertNotNull()
  - assertTrue()
  - assertFalse()
  - assertThrows()
  - assertNotEquals()
  - assertNull()
  - assertDoesNotThrow()

✅ **Co najmniej 2 parametryzacje**: 3 użyte:
  - @ValueSource
  - @CsvSource
  - @MethodSource

✅ **Komentarze**: Jeśli/Gdy/Wtedy (Given/When/Then)  
✅ **Bez mockowania**: Wszystkie zależności rzeczywiste

## Raporty testów

Po uruchomieniu `mvn test`, raporty dostępne w:
- `target/surefire-reports/` - szczegółowe raporty XML i TXT
- Wyniki w konsoli z kolorowaniem

## Przykładowe wywołanie testu

```bash
cd cinema-project
mvn test -Dtest=TestAdminControllerDodawanieFilmu
```

## Struktura testów

Każdy test wykorzystuje pattern **Given-When-Then**:

```java
@Test
void testDodajFilm() {
    // Jeśli: Przygotowanie danych
    String daneFilmu = "F001;Avengers;...";
    
    // Gdy: Wykonanie operacji
    String wynik = adminController.dodajFilm(daneFilmu);
    
    // Wtedy: Weryfikacja wyniku
    assertNotNull(wynik);
    assertTrue(wynik.contains("pomyslnie"));
}
```
