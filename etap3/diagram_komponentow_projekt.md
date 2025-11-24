# Projekt Diagramu Komponentów - Architektura MVC

## Struktura architektury 3-warstwowej

### Komponenty:

1. **Widok** (View)
   - Warstwa prezentacji
   - Odpowiada za GUI/interfejs użytkownika
   - Używa interfejsu: IKontrolerAdministratoraSieci
   - Nie będzie implementowana (symulowana przez Kontroler)

2. **Kontroler** (Controller)
   - Warstwa logiki biznesowej i koordynacji
   - Realizuje interfejs: IKontrolerAdministratoraSieci
   - Używa interfejsu: IModel
   - Zawiera główną klasę: SystemSprzedazyBiletow

3. **Model** (Model)
   - Warstwa danych i dostępu do bazy
   - Realizuje interfejs: IModel
   - Zarządza encjami: Film, Seans, Kino

### Interfejsy:

#### IKontrolerAdministratoraSieci
Operacje inicjujące przypadki użycia:
- edytujOferteKina() : void
- dodajNowySeans(nazwaFilmu: String, opisFilmu: String, liczbaSeansow: int) : void
- edytujSzczegolySeansow(idFilmu: String) : void
- usunSeans(idFilmu: String) : void

#### IModel
Operacje dostępu do danych:
- pobierzFilm(id: String) : Film
- zapiszFilm(film: Film) : void
- usunFilm(id: String) : boolean
- pobierzSeans(id: String) : Seans
- zapiszSeans(seans: Seans) : void
- usunSeans(id: String) : boolean
- walidujParametrySeansu(seans: Seans) : boolean

### Połączenia (zgodnie z wzorcem Fasada):

```
Widok ----( IKontrolerAdministratoraSieci )---- Kontroler
                                                      |
                                                      |
                                         Kontroler ----( IModel )---- Model
```

## Klasy graniczne (Facade):

### W komponencie Kontroler:
- **KontrolerAdministratoraSieci** (realizuje IKontrolerAdministratoraSieci)
  - Fasada dla warstwy kontrolera
  - Punkt wejścia dla operacji administracyjnych

### W komponencie Model:
- **ModelBazyDanych** (realizuje IModel)
  - Fasada dla warstwy modelu
  - Zarządza dostępem do danych

## Diagram komponentów (tekstowa reprezentacja):

```
┌─────────────────────────────────────────────────────────────┐
│                                                               │
│  ┌────────────┐                                               │
│  │   Widok    │                                               │
│  └─────┬──────┘                                               │
│        │ uses                                                 │
│        └──────( IKontrolerAdministratoraSieci )              │
│                           │                                   │
│                           │ provided by                       │
│                    ┌──────▼──────┐                           │
│                    │  Kontroler  │                           │
│                    └──────┬──────┘                           │
│                           │ uses                              │
│                           └──────( IModel )                   │
│                                      │                        │
│                                      │ provided by            │
│                               ┌──────▼──────┐                │
│                               │    Model    │                │
│                               └─────────────┘                │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

---
**Status**: Gotowe do generacji XML
