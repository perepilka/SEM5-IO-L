package model;

/**
 * Główna klasa modelu koordynująca operacje zarządzania kinem.
 * Działa jako fasada między kontrolerami a warstwą dostępu do danych.
 */
public class Model implements IModel {
	private Oferta oferta;
	private IDAO dao;

	/**
	 * Tworzy Model z określoną ofertą i DAO.
	 * 
	 * @param ofertaValue instancja oferty kina
	 * @param daoValue    obiekt dostępu do danych dla trwałości
	 */
	public Model(Oferta ofertaValue, IDAO daoValue) {
		oferta = ofertaValue;
		dao = daoValue;
	}

	/**
	 * Dodaje nowy film do systemu kina.
	 * Tworzy film za pomocą fabryki, zapisuje go i loguje operację.
	 * 
	 * @param daneFilmu dane filmu w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia z ID filmu
	 */
	public String dodajFilm(String daneFilmu) {
		IFabrykaFilmu fabryka = new FabrykaStandardowegoFilmu();
		IFilm film = fabryka.utworzFilm(daneFilmu);

		String opis = film.dajId() + ";" + film.dajTytul() + ";" +
				film.dajOpis() + ";" + film.dajCzasTrwania() + ";" +
				film.dajGatunek() + ";" + film.dajCeneSeansow();

		String idFilmu = dao.dodajFilm(opis);
		dao.dodajWpisDoLogu("Dodano film: " + idFilmu);

		return "Film dodany pomyslnie. ID: " + idFilmu;
	}

	/**
	 * Edytuje istniejący film w systemie kina.
	 * 
	 * @param idFilmu   ID filmu do edycji
	 * @param daneFilmu nowe dane filmu
	 * @return komunikat potwierdzenia
	 */
	public String edytujFilm(String idFilmu, String daneFilmu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Usuwa film z systemu kina.
	 * 
	 * @param idFilmu ID filmu do usunięcia
	 * @return komunikat potwierdzenia
	 */
	public String usunFilm(String idFilmu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Dodaje nowy seans do systemu kina.
	 * 
	 * @param daneSeansu dane seansu w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia z ID seansu
	 */
	public String dodajSeans(String daneSeansu) {
		String id = dao.dodajSeans(daneSeansu);
		dao.dodajWpisDoLogu("Dodano seans: " + id);
		return "Seans dodany pomyslnie. ID: " + id;
	}

	/**
	 * Edytuje istniejący seans w systemie kina.
	 * 
	 * @param idSeansu   ID seansu do edycji
	 * @param daneSeansu nowe dane seansu
	 * @return komunikat potwierdzenia
	 */
	public String edytujSeans(String idSeansu, String daneSeansu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Usuwa seans z systemu kina.
	 * 
	 * @param idSeansu ID seansu do usunięcia
	 * @return komunikat potwierdzenia
	 */
	public String usunSeans(String idSeansu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Pobiera repertuar kina na podstawie kryteriów wyszukiwania.
	 * 
	 * @param kryteria kryteria wyszukiwania (np. ID filmu)
	 * @return sformatowany ciąg znaków pasujących seansów lub komunikat jeśli nie
	 *         znaleziono
	 */
	public String pobierzRepertuar(String kryteria) {
		String[] seansyIds = dao.znajdzSeansyFilmu(kryteria);

		if (seansyIds == null || seansyIds.length == 0) {
			return "Brak seansow dla filmu: " + kryteria;
		}

		StringBuilder repertuar = new StringBuilder();
		repertuar.append("Repertuar dla filmu ").append(kryteria).append(":\n");

		for (String seansId : seansyIds) {
			String seansData = dao.znajdzSeans(seansId);
			if (seansData != null) {
				repertuar.append("  - Seans: ").append(seansData).append("\n");
			}
		}

		return repertuar.toString();
	}

	/**
	 * Pobiera pełny repertuar dla określonego kina.
	 * 
	 * @param idKina ID kina
	 * @return pełny repertuar kina
	 */
	public String pobierzRepertuarKina(String idKina) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Tworzy rezerwację na seans.
	 * Parsuje dane rezerwacji, tworzy obiekt rezerwacji, zapisuje go i loguje
	 * operację.
	 * 
	 * @param daneRezerwacji dane rezerwacji w formacie:
	 *                       idSeansu;idKlienta;nrMiejsca;cena
	 * @return komunikat potwierdzenia ze szczegółami rezerwacji
	 */
	public String zarezerwujMiejsce(String daneRezerwacji) {
		String[] dane = daneRezerwacji.split(";");
		String idSeansu = dane[0];
		String idKlienta = dane[1];
		int nrMiejsca = Integer.parseInt(dane[2]);
		double cena = Double.parseDouble(dane[3]);

		// Sprawdzenie czy seans istnieje (Fix #6)
		String seansData = dao.znajdzSeans(idSeansu);
		if (seansData == null) {
			return "Blad: Seans o ID " + idSeansu + " nie istnieje";
		}

		// ID rezerwacji będzie wygenerowane przez DAO (Fix #4)
		Rezerwacja rezerwacja = new Rezerwacja(null, idSeansu, idKlienta, nrMiejsca, cena);

		String opis = rezerwacja.dajOpis();
		String id = dao.dodajRezerwacje(opis);
		dao.dodajWpisDoLogu("Zarezerwowano bilet: " + id + " dla klienta: " + idKlienta);

		return "Rezerwacja wykonana pomyslnie. ID: " + id + ", cena: " + cena + " PLN";
	}

	/**
	 * Anuluje istniejącą rezerwację.
	 * 
	 * @param idRezerwacji ID rezerwacji do anulowania
	 * @return komunikat potwierdzenia
	 */
	public String anulujRezerwacje(String idRezerwacji) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Finalizuje transakcję zakupu.
	 * 
	 * @param daneZakupu dane zakupu
	 * @return komunikat potwierdzenia
	 */
	public String finalizujZakup(String daneZakupu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Rejestruje zdarzenie systemowe.
	 * 
	 * @param zdarzenie opis zdarzenia do zarejestrowania
	 */
	public void zarejestrujZdarzenie(String zdarzenie) {
		throw new UnsupportedOperationException();
	}
}