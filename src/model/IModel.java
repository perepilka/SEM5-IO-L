package model;

/**
 * Główny interfejs modelu dla operacji zarządzania kinem.
 * Definiuje wysokopoziomowe operacje biznesowe dla systemu kinowego.
 */
public interface IModel {

	/**
	 * Dodaje nowy film do systemu kina.
	 * @param daneFilmu dane filmu w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia z ID filmu
	 */
	public String dodajFilm(String daneFilmu);

	/**
	 * Edytuje istniejący film w systemie kina.
	 * @param idFilmu ID filmu do edycji
	 * @param daneFilmu nowe dane filmu
	 * @return komunikat potwierdzenia
	 */
	public String edytujFilm(String idFilmu, String daneFilmu);

	/**
	 * Usuwa film z systemu kina.
	 * @param idFilmu ID filmu do usunięcia
	 * @return komunikat potwierdzenia
	 */
	public String usunFilm(String idFilmu);

	/**
	 * Dodaje nowy seans do systemu kina.
	 * @param daneSeansu dane seansu w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia z ID seansu
	 */
	public String dodajSeans(String daneSeansu);

	/**
	 * Edytuje istniejący seans w systemie kina.
	 * @param idSeansu ID seansu do edycji
	 * @param daneSeansu nowe dane seansu
	 * @return komunikat potwierdzenia
	 */
	public String edytujSeans(String idSeansu, String daneSeansu);

	/**
	 * Usuwa seans z systemu kina.
	 * @param idSeansu ID seansu do usunięcia
	 * @return komunikat potwierdzenia
	 */
	public String usunSeans(String idSeansu);

	/**
	 * Pobiera repertuar kina na podstawie kryteriów wyszukiwania.
	 * @param kryteria kryteria wyszukiwania (np. ID filmu lub tytuł)
	 * @return sformatowany ciąg znaków pasujących seansów
	 */
	public String pobierzRepertuar(String kryteria);

	/**
	 * Tworzy rezerwację na seans.
	 * @param daneRezerwacji dane rezerwacji w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia ze szczegółami rezerwacji
	 */
	public String zarezerwujMiejsce(String daneRezerwacji);

	/**
	 * Anuluje istniejącą rezerwację.
	 * @param idRezerwacji ID rezerwacji do anulowania
	 * @return komunikat potwierdzenia
	 */
	public String anulujRezerwacje(String idRezerwacji);

	/**
	 * Finalizuje transakcję zakupu.
	 * @param daneZakupu dane zakupu
	 * @return komunikat potwierdzenia
	 */
	public String finalizujZakup(String daneZakupu);
}