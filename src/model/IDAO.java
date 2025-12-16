package model;

/**
 * Interfejs Data Access Object dla trwałości danych kinowych.
 * Definiuje metody dla operacji CRUD na filmach, seansach, rezerwacjach i klientach.
 */
public interface IDAO {

	/**
	 * Dodaje wpis zdarzenia do dziennika systemowego.
	 * @param zdarzenie opis zdarzenia do zapisania
	 */
	public void dodajWpisDoLogu(String zdarzenie);

	/**
	 * Pobiera dane filmu według ID.
	 * @param idFilmu ID filmu do znalezienia
	 * @return dane filmu jako string, lub null jeśli nie znaleziono
	 */
	public String znajdzFilm(String idFilmu);

	/**
	 * Dodaje nowy film do bazy danych i generuje unikalny ID.
	 * @param filmData dane filmu do zapisania
	 * @return wygenerowane ID filmu
	 */
	public String dodajFilm(String filmData);

	/**
	 * Aktualizuje istniejące dane filmu.
	 * @param filmData zaktualizowane dane filmu
	 */
	public void edytujFilm(String filmData);

	/**
	 * Usuwa film z bazy danych.
	 * @param idFilmu ID filmu do usunięcia
	 */
	public void usunFilm(String idFilmu);

	/**
	 * Pobiera dane seansu według ID.
	 * @param idSeansu ID seansu do znalezienia
	 * @return dane seansu jako string, lub null jeśli nie znaleziono
	 */
	public String znajdzSeans(String idSeansu);

	/**
	 * Znajduje wszystkie seanse dla określonego filmu.
	 * @param idFilmu ID filmu
	 * @return tablica ID seansów dla określonego filmu
	 */
	public String[] znajdzSeansyFilmu(String idFilmu);

	/**
	 * Dodaje nowy seans do bazy danych i generuje unikalny ID.
	 * @param seansData dane seansu do zapisania
	 * @return wygenerowane ID seansu
	 */
	public String dodajSeans(String seansData);

	/**
	 * Aktualizuje istniejące dane seansu.
	 * @param seansData zaktualizowane dane seansu
	 */
	public void edytujSeans(String seansData);

	/**
	 * Usuwa seans z bazy danych.
	 * @param idSeansu ID seansu do usunięcia
	 */
	public void usunSeans(String idSeansu);

	/**
	 * Pobiera dane rezerwacji według ID.
	 * @param idRezerwacji ID rezerwacji do znalezienia
	 * @return dane rezerwacji jako string, lub null jeśli nie znaleziono
	 */
	public String znajdzRezerwacje(String idRezerwacji);

	/**
	 * Dodaje nową rezerwację do bazy danych i generuje unikalny ID.
	 * @param rezerwacjaData dane rezerwacji do zapisania
	 * @return wygenerowane ID rezerwacji
	 */
	public String dodajRezerwacje(String rezerwacjaData);

	/**
	 * Usuwa rezerwację z bazy danych.
	 * @param idRezerwacji ID rezerwacji do usunięcia
	 */
	public void usunRezerwacje(String idRezerwacji);

	/**
	 * Pobiera dane klienta według ID.
	 * @param idKlienta ID klienta do znalezienia
	 * @return dane klienta jako string, lub null jeśli nie znaleziono
	 */
	public String znajdzKlienta(String idKlienta);

	/**
	 * Dodaje nowego klienta do bazy danych.
	 * @param klientData dane klienta do zapisania
	 * @return wygenerowane ID klienta
	 */
	public String dodajKlienta(String klientData);
}