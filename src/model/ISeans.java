package model;

/**
 * Interfejs definiujący operacje seansów w systemie zarządzania kinem.
 * Zapewnia metody dostępu do informacji o seansie i dostępnych miejscach.
 */
public interface ISeans {

	/**
	 * Zwraca unikalny identyfikator seansu.
	 * @return ID seansu
	 */
	public String dajId();

	/**
	 * Zwraca film wyświetlany na seansie.
	 * @return obiekt filmu
	 */
	public IFilm dajFilm();

	/**
	 * Zwraca datę i godzinę seansu.
	 * @return data seansu
	 */
	public String dajDate();

	/**
	 * Zwraca tablicę wszystkich dostępnych numerów miejsc.
	 * @return tablica dostępnych numerów miejsc
	 */
	public int[] dajWolneMiejsca();
}