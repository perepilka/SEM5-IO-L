package model;

/**
 * Dekorator dla filmów z promocyjnymi zniżkami.
 * Rozszerza podstawową funkcjonalność filmu o procentową redukcję ceny i opis promocji.
 */
public class FilmZPromocja extends DekoratorFilmu {
	private int procentZnizki;
	private String opisPromocji;

	/**
	 * Tworzy FilmZPromocja opakowujący określony film.
	 * @param filmValue film do udekorowania promocyjną ceną
	 * @param znizkaValue procent zniżki (np. 20 dla 20% zniżki)
	 * @param opisValue opis promocji
	 */
	public FilmZPromocja(IFilm filmValue, int znizkaValue, String opisValue) {
		super(filmValue);
		procentZnizki = znizkaValue;
		opisPromocji = opisValue;
	}

	/**
	 * Zwraca opis filmu z dołączoną informacją promocyjną.
	 * @return rozszerzony opis zawierający szczegóły promocji
	 */
	public String dajOpis() {
		return film.dajOpis() + " [PROMOCJA: " + opisPromocji + " -" + procentZnizki + "%]";
	}

	/**
	 * Zwraca cenę seansu z promocyjną zniżką.
	 * @return cena podstawowa pomniejszona o procentową zniżkę promocyjną
	 */
	public double dajCeneSeansow() {
		return film.dajCeneSeansow() * (1 - procentZnizki / 100.0);
	}
}