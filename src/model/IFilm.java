package model;

/**
 * Interfejs definiujący operacje filmowe w systemie zarządzania kinem.
 * Wszystkie typy filmów (standardowe, VIP, promocyjne) implementują ten interfejs.
 */
public interface IFilm {

	/**
	 * Zwraca unikalny identyfikator filmu.
	 * @return ID filmu
	 */
	public String dajId();

	/**
	 * Zwraca tytuł filmu.
	 * @return tytuł filmu
	 */
	public String dajTytul();

	/**
	 * Zwraca opis filmu.
	 * Może zawierać dodatkowe informacje dla udekorowanych filmów (VIP, promocyjne).
	 * @return opis filmu
	 */
	public String dajOpis();

	/**
	 * Zwraca czas trwania filmu w minutach.
	 * @return czas trwania w minutach
	 */
	public int dajCzasTrwania();

	/**
	 * Zwraca cenę seansów dla tego filmu.
	 * Cena może być modyfikowana przez dekoratory (dopłata VIP, zniżka promocyjna).
	 * @return cena seansu
	 */
	public double dajCeneSeansow();
}