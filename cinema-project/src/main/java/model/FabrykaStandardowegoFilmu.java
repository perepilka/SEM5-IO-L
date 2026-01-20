package model;

/**
 * Fabryka do tworzenia standardowych filmów bez żadnych dekoracji.
 * Implementuje wzorzec Fabryka do tworzenia filmów.
 */
public class FabrykaStandardowegoFilmu implements IFabrykaFilmu {

	/**
	 * Tworzy standardowy film z danych rozdzielonych średnikami.
	 * @param daneFilmu dane filmu w formacie: id;tytuł;opis;czas_trwania;gatunek;cena
	 * @return nowa instancja Film z określonymi danymi
	 */
	public IFilm utworzFilm(String daneFilmu) {
		String[] dane = daneFilmu.split(";");
		String id = dane[0];
		String tytul = dane[1];
		String opis = dane[2];
		int czasTrwania = Integer.parseInt(dane[3]);
		String gatunek = dane[4];
		double cena = Double.parseDouble(dane[5]);
		
		return new Film(id, tytul, opis, czasTrwania, gatunek, cena);
	}
}