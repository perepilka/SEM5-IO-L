package model;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object do zarządzania trwałością danych kinowych.
 * Zapewnia przechowywanie i pobieranie w pamięci dla filmów, seansów,
 * rezerwacji i klientów.
 */
public class DAO implements IDAO {

  private Map<String, String> bazyFilmow;
  private Map<String, String> bazySeans;
  private Map<String, String> bazyRezerwacji;
  private Map<String, String> bazyKlientow;
  private List<String> logi;
  private int nextSeansId;
  private int nextRezerwacjaId;
  private int nextKlientId;

  /**
   * Tworzy nowy DAO z pustymi strukturami danych.
   * Inicjalizuje wszystkie wewnętrzne mapy przechowywania i liczniki.
   */
  public DAO() {
    bazyFilmow = new HashMap<>();
    bazySeans = new HashMap<>();
    bazyRezerwacji = new HashMap<>();
    bazyKlientow = new HashMap<>();
    logi = new ArrayList<>();
    nextSeansId = 1;
    nextRezerwacjaId = 1;
    nextKlientId = 1;
  }

  /**
   * Dodaje wpis zdarzenia do dziennika systemowego.
   * 
   * @param zdarzenie opis zdarzenia do zapisania
   */
  public void dodajWpisDoLogu(String zdarzenie) {
    logi.add(zdarzenie);
    System.out.println("[LOG] " + zdarzenie);
  }

  /**
   * Pobiera dane filmu według ID.
   * 
   * @param idFilmu ID filmu do znalezienia
   * @return dane filmu jako string, lub null jeśli nie znaleziono
   */
  public String znajdzFilm(String idFilmu) {
    return bazyFilmow.get(idFilmu);
  }

  /**
   * Dodaje nowy film do bazy danych.
   * 
   * @param filmData dane filmu do zapisania (pierwsze pole to ID)
   * @return ID filmu z danych
   */
  public String dodajFilm(String filmData) {
    String[] parts = filmData.split(";");
    String id = parts[0];
    bazyFilmow.put(id, filmData);
    return id;
  }

  /**
   * Aktualizuje istniejące dane filmu.
   * 
   * @param filmData zaktualizowane dane filmu
   */
  public void edytujFilm(String filmData) {
    throw new UnsupportedOperationException();
  }

  /**
   * Usuwa film z bazy danych.
   * 
   * @param idFilmu ID filmu do usunięcia
   */
  public void usunFilm(String idFilmu) {
    throw new UnsupportedOperationException();
  }

  /**
   * Pobiera dane seansu według ID.
   * 
   * @param idSeansu ID seansu do znalezienia
   * @return dane seansu jako string, lub null jeśli nie znaleziono
   */
  public String znajdzSeans(String idSeansu) {
    return bazySeans.get(idSeansu);
  }

  /**
   * Znajduje wszystkie seanse dla określonego filmu.
   * 
   * @param idFilmu ID filmu
   * @return tablica ID seansów dla określonego filmu
   */
  public String[] znajdzSeansyFilmu(String idFilmu) {
    List<String> seansyFilmu = new ArrayList<>();
    for (Map.Entry<String, String> entry : bazySeans.entrySet()) {
      String[] parts = entry.getValue().split(";");
      if (parts.length > 0 && parts[0].equals(idFilmu)) {
        seansyFilmu.add(entry.getKey());
      }
    }
    return seansyFilmu.toArray(new String[0]);
  }

  /**
   * Dodaje nowy seans do bazy danych i generuje unikalny ID.
   * 
   * @param seansData dane seansu do zapisania
   * @return wygenerowane ID seansu
   */
  public String dodajSeans(String seansData) {
    String id = "S" + nextSeansId++;
    bazySeans.put(id, seansData);
    return id;
  }

  /**
   * Aktualizuje istniejące dane seansu.
   * 
   * @param seansData zaktualizowane dane seansu
   */
  public void edytujSeans(String seansData) {
    throw new UnsupportedOperationException();
  }

  /**
   * Usuwa seans z bazy danych.
   * 
   * @param idSeansu ID seansu do usunięcia
   */
  public void usunSeans(String idSeansu) {
    bazySeans.remove(idSeansu);
  }

  /**
   * Pobiera dane rezerwacji według ID.
   * 
   * @param idRezerwacji ID rezerwacji do znalezienia
   * @return dane rezerwacji jako string, lub null jeśli nie znaleziono
   */
  public String znajdzRezerwacje(String idRezerwacji) {
    return bazyRezerwacji.get(idRezerwacji);
  }

  /**
   * Dodaje nową rezerwację do bazy danych i generuje unikalny ID.
   * 
   * @param rezerwacjaData dane rezerwacji do zapisania
   * @return wygenerowane ID rezerwacji
   */
  public String dodajRezerwacje(String rezerwacjaData) {
    String id = "R" + nextRezerwacjaId++;
    bazyRezerwacji.put(id, rezerwacjaData);
    return id;
  }

  /**
   * Usuwa rezerwację z bazy danych.
   * 
   * @param idRezerwacji ID rezerwacji do usunięcia
   */
  public void usunRezerwacje(String idRezerwacji) {
    throw new UnsupportedOperationException();
  }

  /**
   * Pobiera dane klienta według ID.
   * 
   * @param idKlienta ID klienta do znalezienia
   * @return dane klienta jako string, lub null jeśli nie znaleziono
   */
  public String znajdzKlienta(String idKlienta) {
    throw new UnsupportedOperationException();
  }

  /**
   * Dodaje nowego klienta do bazy danych.
   * 
   * @param klientData dane klienta do zapisania
   * @return wygenerowane ID klienta
   */
  public String dodajKlienta(String klientData) {
    throw new UnsupportedOperationException();
  }
}