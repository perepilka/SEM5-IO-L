package model;

/**
 * Fabryka do tworzenia filmów promocyjnych ze zniżką cenową.
 * Implementuje wzorzec Fabryka do tworzenia filmów udekorowanych promocyjnymi zniżkami.
 */
public class FabrykaFilmuZPromocja implements IFabrykaFilmu {
	private int procentZnizki;
	private String opisPromocji;

	/**
	 * Tworzy FabrykaFilmuZPromocja z określoną konfiguracją promocji.
	 * @param znizkaValue procent zniżki do zastosowania dla wszystkich tworzonych filmów
	 * @param opisValue opis promocji
	 */
	public FabrykaFilmuZPromocja(int znizkaValue, String opisValue) {
		procentZnizki = znizkaValue;
		opisPromocji = opisValue;
	}

	/**
	 * Tworzy film promocyjny z danych rozdzielonych średnikami.
	 * @param daneFilmu dane filmu w formacie: id;tytuł;opis;czas_trwania;gatunek;cena
	 * @return nowa instancja FilmZPromocja z określonymi danymi i promocyjną zniżką
	 */
	public IFilm utworzFilm(String daneFilmu) {
		String[] dane = daneFilmu.split(";");
		String id = dane[0];
		String tytul = dane[1];
		String opis = dane[2];
		int czasTrwania = Integer.parseInt(dane[3]);
		String gatunek = dane[4];
		double cena = Double.parseDouble(dane[5]);
		
		Film baseFilm = new Film(id, tytul, opis, czasTrwania, gatunek, cena);
		return new FilmZPromocja(baseFilm, procentZnizki, opisPromocji);
	}
}