package model;

/**
 * Bazowa klasa dekoratora dla filmów implementująca wzorzec Dekorator.
 * Zapewnia opakowanie wokół IFilm, które deleguje wszystkie wywołania do opakowanego filmu.
 */
public class DekoratorFilmu implements IFilm {
	protected IFilm film;

	/**
	 * Tworzy DekoratorFilmu opakowujący określony film.
	 * @param filmValue film do udekorowania
	 */
	public DekoratorFilmu(IFilm filmValue) {
		film = filmValue;
	}

	/**
	 * Zwraca unikalny identyfikator filmu.
	 * @return ID filmu z opakowanego filmu
	 */
	public String dajId() {
		return film.dajId();
	}

	/**
	 * Zwraca tytuł filmu.
	 * @return tytuł filmu z opakowanego filmu
	 */
	public String dajTytul() {
		return film.dajTytul();
	}

	/**
	 * Zwraca opis filmu.
	 * @return opis filmu z opakowanego filmu
	 */
	public String dajOpis() {
		return film.dajOpis();
	}

	/**
	 * Zwraca czas trwania filmu w minutach.
	 * @return czas trwania z opakowanego filmu
	 */
	public int dajCzasTrwania() {
		return film.dajCzasTrwania();
	}

	/**
	 * Zwraca cenę seansów dla tego filmu.
	 * @return cena seansu z opakowanego filmu
	 */
	public double dajCeneSeansow() {
		return film.dajCeneSeansow();
	}
}