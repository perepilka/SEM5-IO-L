package model;

/**
 * Reprezentuje rezerwację biletu w systemie zarządzania kinem.
 * Przechowuje szczegóły rezerwacji, w tym seans, klient, miejsce i informacje o cenie.
 */
public class Rezerwacja {
	private String id;
	private String idSeansu;
	private String idKlienta;
	private int nrMiejsca;
	private double cena;

	/**
	 * Tworzy Rezerwację z określonymi szczegółami.
	 * @param idValue unikalny identyfikator rezerwacji
	 * @param idSeansuwValue ID seansu
	 * @param idKlientaValue ID klienta dokonującego rezerwacji
	 * @param nrMiejscaValue numer miejsca
	 * @param cenaValue cena biletu
	 */
	public Rezerwacja(String idValue, String idSeansuwValue, String idKlientaValue, int nrMiejscaValue, double cenaValue) {
		id = idValue;
		idSeansu = idSeansuwValue;
		idKlienta = idKlientaValue;
		nrMiejsca = nrMiejscaValue;
		cena = cenaValue;
	}

	/**
	 * Zwraca unikalny identyfikator rezerwacji.
	 * @return ID rezerwacji
	 */
	public String dajId() {
		return id;
	}

	/**
	 * Zwraca cenę biletu.
	 * @return cena biletu
	 */
	public double dajCene() {
		return cena;
	}

	/**
	 * Zwraca tekstową reprezentację rezerwacji rozdzieloną średnikami.
	 * @return opis rezerwacji w formacie: id;idSeansu;idKlienta;nrMiejsca;cena
	 */
	public String dajOpis() {
		return id + ";" + idSeansu + ";" + idKlienta + ";" + nrMiejsca + ";" + cena;
	}
}