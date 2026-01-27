# Звіт про логічні помилки в проекті Cinema Management System

## Дата аналізу: 2026-01-23

---

## ❌ КРИТИЧНА ПОМИЛКА #1: Конфлікт ID фільмів

### Місце помилки:
- **Файл**: `src/main/java/model/Model.java` (рядки 27-38)
- **Файл**: `src/main/java/model/DAO.java` (рядки 63-67)
- **Файл**: `src/main/java/model/FabrykaStandardowegoFilmu.java` (рядки 14-24)

### Опис проблеми:
Існує **дублювання та конфлікт ID фільмів**. Система використовує ID у двох місцях одночасно:

1. **У фабриці фільмів** (`FabrykaStandardowegoFilmu.java:16`):
   ```java
   String id = dane[0];  // ID береться з вхідних даних
   ```

2. **У DAO** (`DAO.java:64`):
   ```java
   String id = "F" + nextFilmId++;  // DAO генерує нове ID
   ```

3. **У Model** (`Model.java:31-35`):
   ```java
   String opis = film.dajId() + ";" + ...  // Використовується ID з фабрики
   String idFilmu = dao.dodajFilm(opis);   // Але DAO генерує НОВЕ ID
   ```

### Наслідки:
- ID фільму з фабрики (наприклад, "1", "2", "F001") **ігнорується**
- DAO генерує власне ID ("F1", "F2", "F3")
- У базі зберігаються **два різні ID**: один в рядку даних, інший як ключ Map
- Неможливість знайти фільм за оригінальним ID
- Дані в DAO містять неправильний ID

### Як виправити:
**Варіант 1 (Рекомендований)**: DAO не має генерувати ID автоматично
```java
// У DAO.java - змінити метод dodajFilm:
public String dodajFilm(String filmData) {
    String[] parts = filmData.split(";");
    String id = parts[0];  // Взяти ID з даних
    bazyFilmow.put(id, filmData);
    return id;
}

// Видалити nextFilmId з DAO, бо ID генерується зовні
```

**Варіант 2**: Генерувати ID тільки в DAO
```java
// У Model.java - НЕ передавати ID до фабрики:
String daneFilmu = ";тут_без_ID;" + решта_даних;

// У FabrykaStandardowegoFilmu.java - не брати ID з даних:
public IFilm utworzFilm(String daneFilmu) {
    String[] dane = daneFilmu.split(";");
    String tytul = dane[0];  // Без ID
    String opis = dane[1];
    // ... інші поля
    return new Film(null, tytul, opis, ...);  // ID буде null
}

// У Model.java - використовувати ID з DAO:
String idFilmu = dao.dodajFilm(opis_без_ID);
```

---

## ⚠️ ПОМИЛКА #2: ID фільму втрачається при збереженні в DAO

### Місце помилки:
- **Файл**: `src/main/java/model/Model.java` (рядки 31-35)

### Опис проблеми:
```java
String opis = film.dajId() + ";" + film.dajTytul() + ";" + 
              film.dajOpis() + ";" + film.dajCzasTrwania() + ";" + 
              film.dajCeneSeansow();  // ❌ Немає gatunku!
```

У рядку даних **відсутнє поле `gatunek`**, хоча воно є в класі `Film`.

### Наслідки:
- При збереженні фільму в DAO втрачається інформація про жанр
- Дані неповні: формат "id;tytul;opis;czas;cena" замість "id;tytul;opis;czas;gatunek;cena"
- Неможливість відновити повний об'єкт фільму з бази

### Як виправити:
```java
// У Model.java, метод dodajFilm:
String opis = film.dajId() + ";" + film.dajTytul() + ";" + 
              film.dajOpis() + ";" + film.dajCzasTrwania() + ";" + 
              "GATUNEK_PLACEHOLDER" + ";" +  // ❗ Треба додати метод dajGatunek() в IFilm
              film.dajCeneSeansow();

// АБО додати метод в інтерфейс IFilm:
public interface IFilm {
    String dajId();
    String dajTytul();
    String dajOpis();
    int dajCzasTrwania();
    double dajCeneSeansow();
    String dajGatunek();  // ← ДОДАТИ
}

// І в Film.java:
public String dajGatunek() {
    return gatunek;
}

// Тоді в Model.java:
String opis = film.dajId() + ";" + film.dajTytul() + ";" + 
              film.dajOpis() + ";" + film.dajCzasTrwania() + ";" + 
              film.dajGatunek() + ";" +
              film.dajCeneSeansow();
```

---

## ⚠️ ПОМИЛКА #3: Невідповідність формату даних сеансу

### Місце помилки:
- **Файл**: `src/main/java/model/Model.java` (рядок 66)
- **Файл**: `src/main/java/controller/CinemaManagementSystem.java` (рядки 43, 47, 51)

### Опис проблеми:
При додаванні сеансу формат даних **не містить ID фільму на першій позиції**:

```java
// У CinemaManagementSystem.java:
String seansData1 = "1;2024-12-20 18:00;Sala1;100";
//                   ↑ це ID фільму, але конструктор Seans очікує інший порядок
```

Але конструктор `Seans` очікує:
```java
public Seans(String idValue, IFilm filmValue, String dataValue, String salaValue, int miejscaValue)
//           ID сеансу,   об'єкт Film,    дата,          sala,        кількість місць
```

### Наслідки:
- Формат даних не відповідає конструктору класу `Seans`
- Неможливо створити об'єкт сеансу з таких даних
- ID фільму передається як строка, але потрібен об'єкт `IFilm`

### Як виправити:
```java
// Варіант 1: Змінити формат вхідних даних
String seansData1 = "S1;F1;2024-12-20 18:00;Sala1;100";
//                   ↑   ↑
//                   ID сеансу, ID фільму

// У Model.java метод dodajSeans:
public String dodajSeans(String daneSeansu) {
    String[] dane = daneSeansu.split(";");
    String idSeansu = dane[0];
    String idFilmu = dane[1];
    String data = dane[2];
    String sala = dane[3];
    int miejsca = Integer.parseInt(dane[4]);
    
    // Отримати фільм з DAO
    String filmData = dao.znajdzFilm(idFilmu);
    if (filmData == null) {
        return "Blad: Film o ID " + idFilmu + " nie istnieje";
    }
    
    // Створити об'єкт Film (використовуючи фабрику)
    IFabrykaFilmu fabryka = new FabrykaStandardowegoFilmu();
    IFilm film = fabryka.utworzFilm(filmData);
    
    // Створити сеанс
    Seans seans = new Seans(idSeansu, film, data, sala, miejsca);
    
    // Зберегти в DAO
    String id = dao.dodajSeans(daneSeansu);
    dao.dodajWpisDoLogu("Dodano seans: " + id);
    
    return "Seans dodany pomyslnie. ID: " + id;
}
```

---

## ⚠️ ПОМИЛКА #4: ID генерується в DAO, але очікується у вхідних даних

### Місце помилки:
- **Файл**: `src/main/java/model/DAO.java` (метод `dodajSeans`, рядки 114-118)
- **Файл**: `src/main/java/model/Model.java` (метод `zarezerwujMiejsce`, рядки 130-144)

### Опис проблеми:
Непослідовна логіка генерації ID:

1. **Для сеансів**: DAO генерує ID автоматично ✓
2. **Для резервацій**: ID передається у вхідних даних (не генерується) ✗

```java
// Model.java, метод zarezerwujMiejsce:
String[] dane = daneRezerwacji.split(";");
String idRezerwacji = dane[0];  // ← ID береться з параметра
```

Але потім:
```java
String id = dao.dodajRezerwacje(opis);  // ← DAO також генерує ID
```

### Наслідки:
- ID резервації дублюється: один у даних, другий генерує DAO
- Непослідовна логіка (для фільмів і сеансів DAO генерує ID, для резервацій - ні)

### Як виправити:
**Варіант 1**: Завжди генерувати ID в DAO
```java
// У Model.java, метод zarezerwujMiejsce - НЕ передавати ID:
public String zarezerwujMiejsce(String daneRezerwacji) {
    String[] dane = daneRezerwacji.split(";");
    // String idRezerwacji = dane[0];  ← ВИДАЛИТИ
    String idSeansu = dane[0];  // ← Зсунути індекси
    String idKlienta = dane[1];
    int nrMiejsca = Integer.parseInt(dane[2]);
    double cena = Double.parseDouble(dane[3]);
    
    // ID буде null, згенерується в DAO
    Rezerwacja rezerwacja = new Rezerwacja(null, idSeansu, idKlienta, nrMiejsca, cena);
    
    String id = dao.dodajRezerwacje(rezerwacja.dajOpis());
    // ...
}

// У CinemaManagementSystem.java:
String rezerwacjaData1 = "S1;K001;15;25.50";  // Без ID резервації
```

---

## ⚠️ ПОМИЛКА #5: Пошук сеансів за ID фільму використовує `contains()`

### Місце помилки:
- **Файл**: `src/main/java/model/DAO.java` (метод `znajdzSeansyFilmu`, рядки 99-107)

### Опис проблеми:
```java
public String[] znajdzSeansyFilmu(String idFilmu) {
    List<String> seansyFilmu = new ArrayList<>();
    for (Map.Entry<String, String> entry : bazySeans.entrySet()) {
        if (entry.getValue().contains(idFilmu)) {  // ❌ НЕБЕЗПЕЧНО!
            seansyFilmu.add(entry.getKey());
        }
    }
    return seansyFilmu.toArray(new String[0]);
}
```

### Наслідки:
Метод `contains()` може знайти **хибні збіги**:
- Шукаємо фільм "1" → знайде також "10", "11", "21" тощо
- Шукаємо фільм "F1" → знайде також "F10", "F11", "F12"
- Може знайти ID у інших полях (дата "2024-01-15" містить "1")

### Приклад проблеми:
```
Сеанси в базі:
S1 → "F1;2024-12-20 18:00;Sala1;100"
S2 → "F10;2024-12-21 19:00;Sala2;80"
S3 → "F2;2024-01-15 20:00;Sala1;100"  ← містить "1" в даті

Шукаємо сеанси для фільму "F1":
Результат: S1, S2, S3 ❌ (має бути тільки S1)
```

### Як виправити:
```java
public String[] znajdzSeansyFilmu(String idFilmu) {
    List<String> seansyFilmu = new ArrayList<>();
    for (Map.Entry<String, String> entry : bazySeans.entrySet()) {
        String[] parts = entry.getValue().split(";");
        if (parts.length > 0 && parts[0].equals(idFilmu)) {  // ✓ Перевіряємо тільки перше поле
            seansyFilmu.add(entry.getKey());
        }
    }
    return seansyFilmu.toArray(new String[0]);
}
```

---

## ⚠️ ПОМИЛКА #6: Резервація не перевіряє доступність місця

### Місце помилки:
- **Файл**: `src/main/java/model/Model.java` (метод `zarezerwujMiejsce`, рядки 130-145)

### Опис проблеми:
При створенні резервації **не перевіряється**:
- Чи існує такий сеанс
- Чи доступне місце
- Чи не перевищує номер місця ліміт

```java
public String zarezerwujMiejsce(String daneRezerwacji) {
    // ... парсинг даних ...
    
    Rezerwacja rezerwacja = new Rezerwacja(...);
    String id = dao.dodajRezerwacje(opis);  // ❌ Жодних перевірок!
    
    return "Rezerwacja wykonana pomyslnie...";
}
```

### Наслідки:
- Можна зарезервувати неіснуючий сеанс
- Можна зарезервувати зайняте місце
- Можна зарезервувати місце з номером 9999 у залі на 100 місць
- Об'єкт `Seans` має метод `zarezerwujMiejsce()`, але він не використовується

### Як виправити:
```java
public String zarezerwujMiejsce(String daneRezerwacji) {
    String[] dane = daneRezerwacji.split(";");
    String idRezerwacji = dane[0];
    String idSeansu = dane[1];
    String idKlienta = dane[2];
    int nrMiejsca = Integer.parseInt(dane[3]);
    double cena = Double.parseDouble(dane[4]);
    
    // 1. Перевірити існування сеансу
    String seansData = dao.znajdzSeans(idSeansu);
    if (seansData == null) {
        return "Blad: Seans o ID " + idSeansu + " nie istnieje";
    }
    
    // 2. Отримати об'єкт сеансу і перевірити доступність місця
    // (Потрібно додати логіку створення об'єкта Seans з даних)
    ISeans seans = utworzSeansZDanych(seansData);
    
    if (!seans.zarezerwujMiejsce(nrMiejsca)) {
        return "Blad: Miejsce " + nrMiejsca + " jest zajete lub nie istnieje";
    }
    
    // 3. Створити резервацію
    Rezerwacja rezerwacja = new Rezerwacja(idRezerwacji, idSeansu, idKlienta, nrMiejsca, cena);
    String id = dao.dodajRezerwacje(rezerwacja.dajOpis());
    dao.dodajWpisDoLogu("Zarezerwowano bilet: " + id + " dla klienta: " + idKlienta);
    
    return "Rezerwacja wykonana pomyslnie. ID: " + id + ", cena: " + cena + " PLN";
}
```

---

## ⚠️ ПОМИЛКА #7: Дані сеансу не містять ID фільму при збереженні

### Місце помилки:
- **Файл**: `src/main/java/controller/CinemaManagementSystem.java` (рядки 43-53)

### Опис проблеми:
При додаванні сеансу передається:
```java
String seansData1 = "1;2024-12-20 18:00;Sala1;100";
```

Це означає:
- "1" - можливо ID фільму
- "2024-12-20 18:00" - дата/час
- "Sala1" - зала
- "100" - кількість місць

Але **відсутній ID самого сеансу**, і формат не відповідає жодній логічній структурі для збереження.

### Як виправити:
Визначити чіткий формат даних сеансу:
```java
// Формат: idSeansu;idFilmu;data;sala;liczbaMiejsc
String seansData1 = "S1;F1;2024-12-20 18:00;Sala1;100";
String seansData2 = "S2;F1;2024-12-20 21:00;Sala2;80";
String seansData3 = "S3;F2;2024-12-21 19:00;Sala1;100";
```

---

## 📊 Підсумок

**Всього знайдено логічних помилок: 7**

### Критичність:
- 🔴 **Критичні**: 1 (Помилка #1 - конфлікт ID)
- 🟡 **Високий пріоритет**: 4 (Помилки #2, #3, #4, #6)
- 🟢 **Середній пріоритет**: 2 (Помилки #5, #7)

### Основні проблеми:
1. ✗ Дублювання та конфлікт ID фільмів між фабрикою та DAO
2. ✗ Втрата поля "gatunek" при збереженні фільму
3. ✗ Невідповідність формату даних сеансу
4. ✗ Непослідовна генерація ID для різних сутностей
5. ✗ Небезпечний пошук за допомогою `contains()`
6. ✗ Відсутність перевірки доступності місць при резервації
7. ✗ Неповні/некоректні дані сеансів

### Рекомендації:
1. **ТЕРМІНОВО** виправити Помилку #1 - це блокує коректну роботу всієї системи
2. Додати метод `dajGatunek()` до інтерфейсу `IFilm`
3. Стандартизувати генерацію ID (тільки в DAO або тільки зовні)
4. Визначити чіткі формати даних для всіх сутностей
5. Додати валідацію даних перед збереженням
6. Використовувати точні перевірки замість `contains()`
7. Інтегрувати логіку резервації місць з класом `Seans`

---

**Кінець звіту**
