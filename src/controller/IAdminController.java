package controller;

/**
 * Interfejs definiujący operacje administracyjne dla zarządzania kinem.
 * Zapewnia metody do zarządzania filmami, seansami i ofertą kina.
 */
public interface IAdminController {

	/**
	 * Dodaje nowy film do systemu kina.
	 * @param daneFilmu dane filmu w formacie rozdzielanym średnikami
	 */
	public void dodajFilm(String daneFilmu);

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
	 * @return komunikat potwierdzenia
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
	 * Edytuje ofertę kina używając określonych danych operacji.
	 * @param daneOperacji dane operacji określające typ edycji
	 * @return komunikat potwierdzenia
	 */
	public String edytujOferteKina(String daneOperacji);
}