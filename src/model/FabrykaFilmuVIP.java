package model;

/**
 * Fabryka do tworzenia filmów VIP z funkcjami premium.
 * Implementuje wzorzec Fabryka do tworzenia filmów udekorowanych ulepszeniami VIP.
 */
public class FabrykaFilmuVIP implements IFabrykaFilmu {
	private double doplataPremium;
	private String[] dodatkowe;

	/**
	 * Tworzy FabrykaFilmuVIP z określoną konfiguracją VIP.
	 * @param doplataValue dopłata premium do zastosowania dla wszystkich tworzonych filmów
	 * @param dodatkoweValue tablica dodatkowych usług VIP
	 */
	public FabrykaFilmuVIP(double doplataValue, String[] dodatkoweValue) {
		doplataPremium = doplataValue;
		dodatkowe = dodatkoweValue;
	}

	/**
	 * Tworzy film VIP z danych rozdzielonych średnikami.
	 * @param daneFilmu dane filmu w formacie: id;tytuł;opis;czas_trwania;gatunek;cena
	 * @return nowa instancja FilmVIP z określonymi danymi i funkcjami VIP
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
		return new FilmVIP(baseFilm, doplataPremium, dodatkowe);
	}
}