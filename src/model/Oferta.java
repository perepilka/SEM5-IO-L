package model;

/**
 * Reprezentuje ofertę kina zawierającą filmy, seanse, rezerwacje i klientów.
 * Zapewnia metody do zarządzania i odpytywania dostępnej oferty kina.
 */
public class Oferta {
	private IDAO dao;
	private IFilm[] filmy;
	private ISeans[] seansy;
	private Rezerwacja[] rezerwacje;
	private Klient[] klienci;

	/**
	 * Tworzy Ofertę z określoną instancją DAO.
	 * @param daoInstance obiekt dostępu do danych dla operacji trwałości
	 */
	public Oferta(IDAO daoInstance) {
		dao = daoInstance;
	}

	/**
	 * Pobiera film według jego ID.
	 * @param idFilmu ID filmu do pobrania
	 * @return film o określonym ID
	 */
	public IFilm dajFilm(String idFilmu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Pobiera seans według jego ID.
	 * @param idSeansu ID seansu do pobrania
	 * @return seans o określonym ID
	 */
	public ISeans dajSeans(String idSeansu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Pobiera wszystkie seanse dla określonego filmu.
	 * @param idFilmu ID filmu
	 * @return tablica seansów dla określonego filmu
	 */
	public ISeans[] dajSeansyFilmu(String idFilmu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Pobiera rezerwację według jej ID.
	 * @param idRezerwacji ID rezerwacji do pobrania
	 * @return rezerwacja o określonym ID
	 */
	public Rezerwacja dajRezerwacje(String idRezerwacji) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Pobiera klienta według jego ID.
	 * @param idKlienta ID klienta do pobrania
	 * @return klient o określonym ID
	 */
	public Klient dajKlienta(String idKlienta) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Usuwa film z oferty.
	 * @param idFilmu ID filmu do usunięcia
	 */
	public void usunFilm(String idFilmu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Dodaje nowy seans do oferty.
	 * @param seans seans do dodania
	 */
	public void dodajSeans(ISeans seans) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Usuwa seans z oferty.
	 * @param idSeansu ID seansu do usunięcia
	 */
	public void usunSeans(String idSeansu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Anuluje rezerwację.
	 * @param idRezerwacji ID rezerwacji do anulowania
	 */
	public void anulujRezerwacje(String idRezerwacji) {
		throw new UnsupportedOperationException();
	}
}