package model;

/**
 * Reprezentuje standardowy film w systemie zarządzania kinem.
 * Przechowuje podstawowe informacje o filmie, w tym tytuł, opis, czas trwania,
 * gatunek i cenę.
 */
public class Film implements IFilm {
	private String id;
	private String tytul;
	private String opis;
	private int czasTrwania;
	private String gatunek;
	private double cenaPodstawowa;

	/**
	 * Tworzy Film z określonymi szczegółami.
	 * 
	 * @param idValue      unikalny identyfikator filmu
	 * @param tytulValue   tytuł filmu
	 * @param opisValue    opis filmu
	 * @param czasValue    czas trwania w minutach
	 * @param gatunekValue gatunek filmu
	 * @param cenaValue    cena podstawowa seansów
	 */
	public Film(String idValue, String tytulValue, String opisValue, int czasValue, String gatunekValue,
			double cenaValue) {
		id = idValue;
		tytul = tytulValue;
		opis = opisValue;
		czasTrwania = czasValue;
		gatunek = gatunekValue;
		cenaPodstawowa = cenaValue;
	}

	/**
	 * Zwraca unikalny identyfikator filmu.
	 * 
	 * @return ID filmu
	 */
	public String dajId() {
		return id;
	}

	/**
	 * Zwraca tytuł filmu.
	 * 
	 * @return tytuł filmu
	 */
	public String dajTytul() {
		return tytul;
	}

	/**
	 * Zwraca opis filmu.
	 * 
	 * @return opis filmu
	 */
	public String dajOpis() {
		return opis;
	}

	/**
	 * Zwraca czas trwania filmu w minutach.
	 * 
	 * @return czas trwania w minutach
	 */
	public int dajCzasTrwania() {
		return czasTrwania;
	}

	/**
	 * Zwraca cenę podstawową seansów dla tego filmu.
	 * 
	 * @return cena seansu
	 */
	public double dajCeneSeansow() {
		return cenaPodstawowa;
	}

	/**
	 * Zwraca gatunek filmu.
	 * 
	 * @return gatunek filmu
	 */
	public String dajGatunek() {
		return gatunek;
	}
}