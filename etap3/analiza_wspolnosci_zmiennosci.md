# Analiza Wspólności i Zmienności - Warstwa Kontroler

## Przypadki użycia do analizy:

### PU04 - Edytowanie oferty kina w systemie
**Scenariusz**:
1. Administrator wybiera "Edycja oferty kin"
2. Administrator wybiera opcję:
   - "Dodaj nowy film" → czynność Dodanie nowego filmu
   - "Edytuj film" → czynność Edytowanie filmu
   - "Usuń film" → czynność Usunięcie filmu
3. System wykonuje operację i aktualizuje ofertę

**Pod-przypadki** (z diagramów czynności etapu 2):
- PU05: Dodanie nowego seansu
- PU06: Edytowanie szczegółów seansów
- PU07: Usunięcie seansu

### PU06 - Edytowanie szczegółów seansów w systemie kin
**Scenariusz**:
1. Administrator rozpoczyna edytowanie
2. System prosi o ID filmu
3. Administrator wpisuje ID
4. System sprawdza istnienie filmu w bazie
5. Jeśli istnieje: system pobiera film
6. System wyświetla szczegóły
7. Administrator edytuje dane
8. Administrator zatwierdza
9. System zapisuje zmiany
10. System wyświetla potwierdzenie

---

## Analiza wspólności (COMMONALITY)

### Wspólne kroki dla operacji na seansach/filmach:
1. **Walidacja danych wejściowych** (ID, parametry)
2. **Interakcja z modelem** (pobierz/zapisz/usuń)
3. **Obsługa błędów** (film nie istnieje, błąd walidacji)
4. **Komunikaty zwrotne** (potwierdzenie/błąd)

### Wspólne odpowiedzialności:
- Inicjacja operacji przez interfejs fasady
- Przepływ sterowania między klasami
- Delegacja operacji na dane do Modelu
- Symulacja operacji I/O (View)

---

## Analiza zmienności (VARIABILITY)

### Różnice między operacjami:
- **Dodawanie**: Tworzenie nowego obiektu, walidacja wszystkich pól
- **Edytowanie**: Modyfikacja istniejącego, walidacja ID + nowych danych
- **Usuwanie**: Tylko walidacja ID, sprawdzenie istnienia

### Różne parametry wejściowe:
- Dodawanie: nazwaFilmu, opisFilmu, liczbaSeansow
- Edytowanie: ID + modyfikowane pola
- Usuwanie: tylko ID

---

## Zastosowanie wzorców projektowych

### 1. FASADA (Facade) - WYMAGANY ✅

**Klasa**: `KontrolerAdministratoraSieci`
- **Odpowiedzialność**: Punkt wejścia do warstwy Kontroler
- **Realizuje**: `IKontrolerAdministratoraSieci`
- **Operacje**:
  ```
  + edytujOferteKina() : void
  + dodajNowySeans(nazwaFilmu, opisFilmu, liczbaSeansow) : void
  + edytujSzczegolySeansow(idFilmu) : void
  + usunSeans(idFilmu) : void
  ```
- **Relacje**:
  - Używa → IModel (dependency «use»)
  - Tworzy instancje → ObslugaSeansow i jej klasy pochodne (dependency «instantiate»)

---

### 2. METODA SZABLONOWA (Template Method) - WYMAGANY ✅

**Cel**: Zdefiniować szkielet algorytmu obsługi operacji na seansach, pozostawiając szczegóły implementacji klasom pochodnym.

#### Klasa abstrakcyjna: `ObslugaSeansow`
**Odpowiedzialność**: Szablon ogólnego algorytmu operacji CRUD
**Operacje**:
```
# Metoda szablonowa (final):
+ wykonajOperacje() : void {
    1. walidujDaneWejsciowe()
    2. wykonajOperacjeNaDanych()
    3. wyswietlKomunikat()
}

# Metody abstrakcyjne (do nadpisania):
+ walidujDaneWejsciowe() : boolean  [abstract]
+ wykonajOperacjeNaDanych() : void  [abstract]
+ wyswietlKomunikat() : void  [abstract]

# Metody wspólne:
- pobierzModel() : IModel
```

**Atrybuty**:
```
- model : IModel  (agregacja z IModel)
```

#### Klasy pochodne (konkretne):

##### `DodawanieSeansow` extends ObslugaSeansow
**Odpowiedzialność**: Implementacja dodawania nowego seansu
**Atrybuty**:
```
- nazwaFilmu : String
- opisFilmu : String
- liczbaSeansow : int
```
**Operacje**:
```
+ DodawanieSeansow(nazwaFilmu, opisFilmu, liczbaSeansow, model)
+ walidujDaneWejsciowe() : boolean  [override]
+ wykonajOperacjeNaDanych() : void  [override]
+ wyswietlKomunikat() : void  [override]
```

##### `EdytowanieSeansow` extends ObslugaSeansow
**Odpowiedzialność**: Implementacja edytowania istniejącego seansu
**Atrybuty**:
```
- idFilmu : String
- noweParametry : Map<String, Object>
```
**Operacje**:
```
+ EdytowanieSeansow(idFilmu, model)
+ walidujDaneWejsciowe() : boolean  [override]
+ wykonajOperacjeNaDanych() : void  [override]
+ wyswietlKomunikat() : void  [override]
- pobierzNoweParametry() : void  [private, symuluje I/O]
```

##### `UsuwanieSeansow` extends ObslugaSeansow
**Odpowiedzialność**: Implementacja usuwania seansu
**Atrybuty**:
```
- idFilmu : String
```
**Operacje**:
```
+ UsuwanieSeansow(idFilmu, model)
+ walidujDaneWejsciowe() : boolean  [override]
+ wykonajOperacjeNaDanych() : void  [override]
+ wyswietlKomunikat() : void  [override]
```

---

### 3. ŁAŃCUCH ZOBOWIĄZAŃ (Chain of Responsibility) - ZALECANY ✅

**Cel**: Oddzielić różne aspekty walidacji danych

#### Klasa abstrakcyjna: `WalidatorDanych`
**Odpowiedzialność**: Bazowa klasa dla łańcucha walidatorów
**Atrybuty**:
```
- nastepnyWalidator : WalidatorDanych  (asocjacja do siebie)
```
**Operacje**:
```
+ ustawNastepny(walidator : WalidatorDanych) : void
+ waliduj(dane : Object) : boolean {
    if (!sprawdz(dane)) return false;
    if (nastepnyWalidator != null)
        return nastepnyWalidator.waliduj(dane);
    return true;
}
+ sprawdz(dane : Object) : boolean  [abstract]
```

#### Klasy pochodne:

##### `WalidatorID` extends WalidatorDanych
**Odpowiedzialność**: Sprawdzenie poprawności ID (nie puste, format)
```
+ sprawdz(dane : Object) : boolean  [override]
```

##### `WalidatorIstnienia` extends WalidatorDanych
**Odpowiedzialność**: Sprawdzenie czy obiekt istnieje w bazie
**Atrybuty**:
```
- model : IModel
```
```
+ WalidatorIstnienia(model : IModel)
+ sprawdz(dane : Object) : boolean  [override]
```

##### `WalidatorParametrow` extends WalidatorDanych
**Odpowiedzialność**: Sprawdzenie poprawności parametrów (nie null, zakresy)
```
+ sprawdz(dane : Object) : boolean  [override]
```

---

## Główna klasa aplikacji

### `SystemSprzedazyBiletow`
**Odpowiedzialność**: Główna klasa uruchamiająca system
**Lokalizacja**: Pakiet Kontroler
**Operacje**:
```
+ main(args : String[]) : void {static}
```
**Relacje**:
- Tworzy instancję → KontrolerAdministratoraSieci
- Tworzy instancję → ModelBazyDanych (realizacja IModel)
- Symuluje operacje View (wyświetlanie menu, pobieranie danych od użytkownika)

---

## Interfejsy

### `IKontrolerAdministratoraSieci` (interface)
**Lokalizacja**: Poza pakietem Kontroler (udostępniany na zewnątrz)
**Operacje**: Jak opisano w Fasadzie

### `IModel` (interface)
**Lokalizacja**: Poza pakietem Kontroler (z komponentu Model)
**Operacje**: Jak zdefiniowano w diagramie komponentów

---

## Pakiet Kontroler

**Zawiera klasy**:
- SystemSprzedazyBiletow
- KontrolerAdministratoraSieci (realizuje IKontrolerAdministratoraSieci)
- ObslugaSeansow (abstract)
- DodawanieSeansow
- EdytowanieSeansow
- UsuwanieSeansow
- WalidatorDanych (abstract)
- WalidatorID
- WalidatorIstnienia
- WalidatorParametrow

**Używa interfejsów** (poza pakietem):
- IModel

**Realizuje interfejsy** (poza pakietem):
- IKontrolerAdministratoraSieci

---

## Zastosowanie zasad SOLID

### S - Single Responsibility Principle ✅
Każda klasa ma jedną odpowiedzialność:
- KontrolerAdministratoraSieci: fasada/punkt wejścia
- ObslugaSeansow: szablon algorytmu
- DodawanieSeansow: tylko dodawanie
- WalidatorID: tylko walidacja ID
- itd.

### O - Open/Closed Principle ✅
- ObslugaSeansow otwarta na rozszerzenie (nowe operacje), zamknięta na modyfikację
- WalidatorDanych: łatwo dodać nowy walidator bez zmiany istniejących

### L - Liskov Substitution Principle ✅
- Wszystkie klasy pochodne ObslugaSeansow mogą zastąpić klasę bazową
- Wszystkie walidatory mogą zastąpić WalidatorDanych

### I - Interface Segregation Principle ✅
- IKontrolerAdministratoraSieci: tylko operacje dla Administratora
- IModel: tylko operacje dostępu do danych

### D - Dependency Inversion Principle ✅
- Kontroler zależy od abstrakcji (IModel), nie od konkretnej implementacji
- Klasy zależą od interfejsów, nie od klas konkretnych

---

**Status**: Gotowe do projektowania diagramu klas
**Następny krok**: Stworzenie XML diagramu klas
