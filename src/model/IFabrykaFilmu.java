package model;

/**
 * Interfejs fabryki do tworzenia instancji filmów.
 * Implementuje wzorzec Fabryka pozwalający na różne strategie tworzenia filmów.
 */
public interface IFabrykaFilmu {

	/**
	 * Tworzy instancję filmu z danych rozdzielonych średnikami.
	 * Implementacja określa typ tworzonego filmu (standardowy, VIP, promocyjny).
	 * @param daneFilmu dane filmu w formacie: id;tytuł;opis;czas_trwania;gatunek;cena
	 * @return nowa instancja filmu
	 */
	public IFilm utworzFilm(String daneFilmu);
}