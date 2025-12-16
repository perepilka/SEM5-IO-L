package model;

/**
 * Dekorator dla filmów z funkcjami premium VIP.
 * Rozszerza podstawową funkcjonalność filmu o cenę premium i dodatkowe usługi.
 */
public class FilmVIP extends DekoratorFilmu {
	private double doplataPremium;
	private String[] dodatkowe;

	/**
	 * Tworzy FilmVIP opakowujący określony film.
	 * @param filmValue film do udekorowania funkcjami VIP
	 * @param doplataValue kwota dopłaty premium
	 * @param dodatkoweValue tablica dodatkowych usług VIP (np. miejsca premium, darmowy popcorn)
	 */
	public FilmVIP(IFilm filmValue, double doplataValue, String[] dodatkoweValue) {
		super(filmValue);
		doplataPremium = doplataValue;
		dodatkowe = dodatkoweValue;
	}

	/**
	 * Zwraca opis filmu z dołączonymi funkcjami VIP.
	 * @return rozszerzony opis zawierający usługi VIP
	 */
	public String dajOpis() {
		StringBuilder sb = new StringBuilder(film.dajOpis());
		sb.append(" [VIP");
		if (dodatkowe != null && dodatkowe.length > 0) {
			sb.append(": ");
			for (int i = 0; i < dodatkowe.length; i++) {
				sb.append(dodatkowe[i]);
				if (i < dodatkowe.length - 1) sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Zwraca cenę seansu z dopłatą premium VIP.
	 * @return cena podstawowa plus dopłata premium
	 */
	public double dajCeneSeansow() {
		return film.dajCeneSeansow() + doplataPremium;
	}
}