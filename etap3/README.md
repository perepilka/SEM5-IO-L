# Etap 3 - Modelowanie Obiektowej 3-warstwowej Architektury

## Podsumowanie wykonanej pracy

### ✅ Zadanie 1: Diagram Komponentów (3-warstwowa architektura MVC)

**Plik**: `diagram_komponentow.xml`

**Komponenty**:
1. **Widok** - warstwa prezentacji (nie implementowana, symulowana przez Kontroler)
2. **Kontroler** - warstwa logiki biznesowej
3. **Model** - warstwa danych i dostępu do bazy

**Interfejsy**:
1. **IKontrolerAdministratoraSieci** - udostępniany przez Kontroler, używany przez Widok
   - `edytujOferteKina() : void`
   - `dodajNowySeans(nazwaFilmu, opisFilmu, liczbaSeansow) : void`
   - `edytujSzczegolySeansow(idFilmu) : void`
   - `usunSeans(idFilmu) : void`

2. **IModel** - udostępniany przez Model, używany przez Kontroler
   - `pobierzFilm(id) : Film`
   - `zapiszFilm(film) : void`
   - `usunFilm(id) : boolean`
   - `pobierzSeans(id) : Seans`
   - `zapiszSeans(seans) : void`
   - `usunSeans(id) : boolean`
   - `walidujParametrySeansu(seans) : boolean`

**Połączenia**:
- Widok → (uses) → IKontrolerAdministratoraSieci
- Kontroler → (provides) → IKontrolerAdministratoraSieci
- Kontroler → (uses) → IModel
- Model → (provides) → IModel

---

### ✅ Zadanie 2: Diagram Klas Komponentu Kontroler

**Plik**: `diagram_klas_kontroler.xml`

#### Pakiet: Kontroler

**Główna klasa**:
- `SystemSprzedazyBiletow`
  - `+ main(args: String[]) : void {static}`

**Klasa fasadowa** (wzorzec Facade):
- `KontrolerAdministratoraSieci` implements IKontrolerAdministratoraSieci
  - Atrybuty: `- model : IModel`
  - Operacje: edytujOferteKina(), dodajNowySeans(), edytujSzczegolySeansow(), usunSeans()

**Wzorzec: Metoda Szablonowa (Template Method)**:
- `ObslugaSeansow` (abstract)
  - Atrybuty: `# model : IModel`
  - Operacje:
    - `+ wykonajOperacje() : void` - metoda szablonowa
    - `+ walidujDaneWejsciowe() : boolean {abstract}`
    - `+ wykonajOperacjeNaDanych() : void {abstract}`
    - `+ wyswietlKomunikat() : void {abstract}`
    - `# pobierzModel() : IModel`

- `DodawanieSeansow` extends ObslugaSeansow
  - Atrybuty: nazwaFilmu, opisFilmu, liczbaSeansow
  - Nadpisuje: walidujDaneWejsciowe(), wykonajOperacjeNaDanych(), wyswietlKomunikat()

- `EdytowanieSeansow` extends ObslugaSeansow
  - Atrybuty: idFilmu
  - Operacje: pobierzNoweParametry() (symuluje I/O)
  - Nadpisuje: walidujDaneWejsciowe(), wykonajOperacjeNaDanych(), wyswietlKomunikat()

- `UsuwanieSeansow` extends ObslugaSeansow
  - Atrybuty: idFilmu
  - Nadpisuje: walidujDaneWejsciowe(), wykonajOperacjeNaDanych(), wyswietlKomunikat()

**Wzorzec: Łańcuch Zobowiązań (Chain of Responsibility)**:
- `WalidatorDanych` (abstract)
  - Atrybuty: `# nastepnyWalidator : WalidatorDanych`
  - Operacje:
    - `+ ustawNastepny(walidator) : void`
    - `+ waliduj(dane) : boolean` - przechodzi przez łańcuch
    - `# sprawdz(dane) : boolean {abstract}`

- `WalidatorID` extends WalidatorDanych
  - Sprawdza poprawność formatu ID

- `WalidatorIstnienia` extends WalidatorDanych
  - Atrybuty: `- model : IModel`
  - Sprawdza czy obiekt istnieje w bazie

- `WalidatorParametrow` extends WalidatorDanych
  - Sprawdza poprawność parametrów (zakresy, null)

**Interfejsy** (poza pakietem):
- `IKontrolerAdministratoraSieci` - realizowany przez KontrolerAdministratoraSieci
- `IModel` - używany przez klasy w pakiecie

**Relacje**:
- **Realizacja**: KontrolerAdministratoraSieci → IKontrolerAdministratoraSieci
- **Uogólnienie**: DodawanieSeansow, EdytowanieSeansow, UsuwanieSeansow → ObslugaSeansow
- **Uogólnienie**: WalidatorID, WalidatorIstnienia, WalidatorParametrow → WalidatorDanych
- **Agregacja**: ObslugaSeansow agreguje IModel
- **Asocjacja**: WalidatorDanych → WalidatorDanych (self-association)
- **Zależność «use»**: KontrolerAdministratoraSieci → IModel
- **Zależność «instantiate»**: 
  - SystemSprzedazyBiletow → KontrolerAdministratoraSieci
  - KontrolerAdministratoraSieci → ObslugaSeansow
  - ObslugaSeansow → WalidatorDanych

---

## Zastosowane wzorce projektowe

### ✅ 1. Fasada (Facade) - WYMAGANY
**Klasa**: `KontrolerAdministratoraSieci`
- Upraszcza dostęp do funkcjonalności warstwy Kontroler
- Realizuje interfejs IKontrolerAdministratoraSieci
- Punkt wejścia dla wszystkich operacji administracyjnych

### ✅ 2. Metoda Szablonowa (Template Method) - WYMAGANY
**Hierarchia**: `ObslugaSeansow` → DodawanieSeansow, EdytowanieSeansow, UsuwanieSeansow
- Definiuje szkielet algorytmu obsługi operacji CRUD
- Wspólna część: wykonajOperacje() - metoda szablonowa
- Zmienne części: walidujDaneWejsciowe(), wykonajOperacjeNaDanych(), wyswietlKomunikat()

### ✅ 3. Łańcuch Zobowiązań (Chain of Responsibility) - ZALECANY
**Hierarchia**: `WalidatorDanych` → WalidatorID, WalidatorIstnienia, WalidatorParametrow
- Oddziela różne aspekty walidacji
- Każdy walidator ma jedną odpowiedzialność (SRP)
- Łatwo rozszerzalny o nowe walidatory (OCP)

---

## Zastosowanie zasad SOLID

### ✅ S - Single Responsibility Principle
Każda klasa ma jedną, jasno określoną odpowiedzialność:
- `SystemSprzedazyBiletow` - uruchomienie aplikacji
- `KontrolerAdministratoraSieci` - fasada/punkt wejścia
- `DodawanieSeansow` - tylko dodawanie
- `EdytowanieSeansow` - tylko edytowanie
- `UsuwanieSeansow` - tylko usuwanie
- `WalidatorID` - tylko walidacja ID
- itd.

### ✅ O - Open/Closed Principle
- `ObslugaSeansow` - otwarta na rozszerzenie (nowe operacje), zamknięta na modyfikację
- `WalidatorDanych` - łatwo dodać nowy walidator bez zmiany istniejących

### ✅ L - Liskov Substitution Principle
- Wszystkie klasy pochodne `ObslugaSeansow` mogą zastąpić klasę bazową
- Wszystkie klasy `WalidatorDanych` mogą zastąpić klasę bazową

### ✅ I - Interface Segregation Principle
- `IKontrolerAdministratoraSieci` - tylko operacje dla Administratora Sieci
- `IModel` - tylko operacje dostępu do danych
- Interfejsy są spójne i nie zmuszają do implementacji niepotrzebnych metod

### ✅ D - Dependency Inversion Principle
- Kontroler zależy od abstrakcji (`IModel`), nie od konkretnej implementacji
- Klasy zależą od interfejsów i klas abstrakcyjnych, nie od klas konkretnych

---

## Analiza wspólności i zmienności

**Wspólność** (wybrane przypadki użycia PU04, PU06):
- Walidacja danych wejściowych
- Interakcja z Modelem (CRUD)
- Obsługa błędów
- Wyświetlanie komunikatów

**Zmienność**:
- Różne parametry wejściowe dla każdej operacji
- Różna logika walidacji
- Różne operacje na danych (dodaj/edytuj/usuń)

**Rozwiązanie**: Wzorzec Metoda Szablonowa + Łańcuch Zobowiązań

---

## Pliki do importu do Visual Paradigm

1. **diagram_komponentow.xml** - Diagram komponentów (Zadanie 1)
2. **diagram_klas_kontroler.xml** - Diagram klas komponentu Kontroler (Zadanie 2)

### Instrukcja importu:
1. Otwórz OnlineCinema.vpp w Visual Paradigm
2. File → Import → XMI/XML
3. Wybierz plik XML
4. Potwierdź import

---

## Pliki pomocnicze (dokumentacja)

- `analiza.md` - Ogólna analiza projektu
- `diagram_komponentow_projekt.md` - Projekt diagramu komponentów
- `analiza_wspolnosci_zmiennosci.md` - Szczegółowa analiza wspólności i zmienności

---

**Autorzy**: Oleksandr Radionenko, Yaroslav Perepilka  
**Projekt**: Zintegrowany System Sprzedaży Biletów dla Sieci Multikin  
**Data**: 24.11.2025  
**Status**: ✅ UKOŃCZONE
