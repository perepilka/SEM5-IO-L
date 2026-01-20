package model;

/**
 * Reprezentuje klienta w systemie zarządzania kinem.
 * Przechowuje informacje o kliencie, w tym identyfikację i dane kontaktowe.
 */
public class Klient {
	private String id;
	private String email;
	private String imie;
	private String nazwisko;

	/**
	 * Tworzy Klienta z określonymi szczegółami.
	 * @param idValue unikalny identyfikator klienta
	 * @param emailValue adres email klienta
	 * @param imieValue imię klienta
	 * @param nazwiskoValue nazwisko klienta
	 */
	public Klient(String idValue, String emailValue, String imieValue, String nazwiskoValue) {
		id = idValue;
		email = emailValue;
		imie = imieValue;
		nazwisko = nazwiskoValue;
	}

	/**
	 * Zwraca unikalny identyfikator klienta.
	 * @return ID klienta
	 */
	public String dajId() {
		return id;
	}

	/**
	 * Zwraca adres email klienta.
	 * @return email klienta
	 */
	public String dajEmail() {
		return email;
	}

	/**
	 * Zwraca tekstową reprezentację klienta rozdzieloną średnikami.
	 * @return opis klienta w formacie: id;email;imię;nazwisko
	 */
	public String dajOpis() {
		return id + ";" + email + ";" + imie + ";" + nazwisko;
	}
}