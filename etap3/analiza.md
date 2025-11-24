# Analiza projektu - Etap 3

## Informacje o projekcie
- **Nazwa**: Zintegrowany System Sprzedaży Biletów dla Sieci Multikin (OnlineCinema)
- **Autorzy**: Oleksandr Radionenko, Yaroslav Perepilka
- **Architektura**: MVC (Model-View-Controller)

## Przypadki użycia z Etapu 2

Z dokumentacji etapu 2 wynika, że wybrano **4 diagramy czynności**:

### PU04 - Edytowanie oferty kina w systemie
- **Złożoność**: WYSOKA (zawiera 3 pod-przypadki użycia)
- **Aktor**: Administrator Sieci
- **Pod-przypadki**:
  - Dodanie nowego filmu
  - Edytowanie filmu
  - Usunięcie filmu
- **Status**: ✅ Wybrany jako jeden z 2 najbardziej złożonych

### PU05 - Dodanie nowego seansu do systemu
- **Złożoność**: ŚREDNIA
- **Aktor**: Administrator Sieci
- **Operacje**:
  - Wprowadzenie danych seansu
  - Walidacja parametrów
  - Dodanie do bazy
- **Status**: ⚪ Pomocniczy

### PU06 - Edytowanie szczegółów seansów w systemie kin
- **Złożoność**: ŚREDNIA/WYSOKA
- **Aktor**: Administrator Sieci
- **Operacje**:
  - Wyszukiwanie filmu po ID
  - Edycja danych
  - Walidacja i zapis
- **Status**: ⚪ Potencjalnie wybrany

### PU07 - Usunięcie seansu z systemu
- **Złożoność**: ŚREDNIA
- **Aktor**: Administrator Sieci
- **Operacje**:
  - Wprowadzenie ID filmu
  - Sprawdzenie istnienia
  - Usunięcie lub komunikat błędu
- **Status**: ⚪ Pomocniczy

## Dodatkowe przypadki użycia (z kontekstu etapu 1)

Z dokumentacji wynika, że system obsługuje również:
- Zarządzanie sieciami kin
- Sprzedaż biletów
- Rezerwacje
- Inne funkcje administracyjne

**Uwaga**: Pełna lista przypadków użycia znajduje się w OnlineCinema.vpp (plik binarny)

## Wybór 2 najbardziej złożonych przypadków użycia

Zgodnie z instrukcją etapu 3, wybieramy:

1. **PU04 - Edytowanie oferty kina w systemie** ✅
   - Najbardziej złożony (zawiera relacje «include»/«extend»)
   - 3 scenariusze alternatywne
   
2. **PU06 - Edytowanie szczegółów seansów w systemie kin** ✅
   - Drugi co do złożoności
   - Zawiera obsługę błędów i walidację

## Architektura MVC - 3 warstwy

### Warstwa WIDOK (View)
- **Odpowiedzialność**: Interfejs użytkownika, GUI
- **Interfejs udostępniany**: Brak (warstwa zewnętrzna)
- **Interfejs używany**: IKontrolerAdministratoraSieci
- **Uwaga**: NIE będzie modelowana ani implementowana (symulowana przez Kontroler)

### Warstwa KONTROLER (Controller)
- **Odpowiedzialność**: Logika biznesowa, koordynacja, realizacja przypadków użycia
- **Interfejs udostępniany**: IKontrolerAdministratoraSieci
- **Interfejs używany**: IModel
- **Główna klasa**: SystemSprzedazyBiletow (z operacją main())
- **Status**: ✅ DO MODELOWANIA (Zadanie 2)

### Warstwa MODEL (Model)
- **Odpowiedzialność**: Zarządzanie danymi, dostęp do bazy danych
- **Interfejs udostępniany**: IModel
- **Interfejs używany**: Brak
- **Uwaga**: Będzie zawierać operacje CRUD dla filmów i seansów

## Wzorce projektowe do zastosowania

### Wymagane:
1. **Fasada** (Facade)
   - Klasa: KontrolerAdministratoraSieci
   - Cel: Uproszczenie dostępu do funkcji kontrolera
   - Realizuje: IKontrolerAdministratoraSieci

2. **Metoda Szablonowa** (Template Method) LUB **Strategia** (Strategy)
   - Decyzja: **Metoda Szablonowa**
   - Klasa abstrakcyjna: ObslugaSeansow
   - Klasy pochodne: DodawanieSeansow, EdytowanieSeansow, UsuwanieSeansow
   - Cel: Wspólny algorytm obsługi seansów z modyfikowalnymi krokami

### Zalecane:
3. **Łańcuch Zobowiązań** (Chain of Responsibility)
   - Potencjalne użycie: Walidacja danych seansów
   - Klasy: WalidatorDanych → WalidatorID → WalidatorParametrow

## Plan dalszych kroków

### Faza 2: Projekt architektury ✅ ZAKOŃCZONA
- [x] Stworzenie diagramu komponentów (3 komponenty + 2 interfejsy)
- [x] Definicja interfejsów IKontrolerAdministratoraSieci i IModel
- [x] Generacja XML dla diagramu komponentów

### Faza 3: Projekt warstwy Kontroler (NASTĘPNA)
- [ ] Analiza wspólności i zmienności PU04 i PU06
- [ ] Zaprojektowanie hierarchii klas z wzorcami
- [ ] Stworzenie diagramu klas dla komponentu Kontroler
- [ ] Generacja XML dla diagramu klas

---
**Data analizy**: 2025-11-24
**Status**: Faza 1 zakończona ✅
