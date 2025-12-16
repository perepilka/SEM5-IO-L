package model;

import java.util.Set;
import java.util.HashSet;

/**
 * Reprezentuje seans filmowy w systemie zarządzania kinem.
 * Zarządza dostępnością miejsc i informacjami o seansie.
 */
public class Seans implements ISeans {
	private String id;
	private IFilm film;
	private String idSali;
	private Set<Integer> miejscaZajete;
	private int liczbaMiejsc;
	private String data;

	/**
	 * Tworzy Seans z określonymi szczegółami.
	 * @param idValue unikalny identyfikator seansu
	 * @param filmValue film wyświetlany na seansie
	 * @param dataValue data i godzina seansu
	 * @param salaValue identyfikator sali/pokoju
	 * @param miejscaValue całkowita liczba dostępnych miejsc
	 */
	public Seans(String idValue, IFilm filmValue, String dataValue, String salaValue, int miejscaValue) {
		id = idValue;
		film = filmValue;
		data = dataValue;
		idSali = salaValue;
		liczbaMiejsc = miejscaValue;
		miejscaZajete = new HashSet<>();
	}

	/**
	 * Zwraca unikalny identyfikator seansu.
	 * @return ID seansu
	 */
	public String dajId() {
		return id;
	}

	/**
	 * Zwraca film wyświetlany na seansie.
	 * @return obiekt filmu
	 */
	public IFilm dajFilm() {
		return film;
	}

	/**
	 * Zwraca datę i godzinę seansu.
	 * @return data seansu
	 */
	public String dajDate() {
		return data;
	}

	/**
	 * Próbuje zarezerwować określone miejsce na tym seansie.
	 * @param nrMiejsca numer miejsca do zarezerwowania
	 * @return true jeśli miejsce zostało pomyślnie zarezerwowane, false jeśli nieprawidłowe lub już zajęte
	 */
	public boolean zarezerwujMiejsce(int nrMiejsca) {
		if (nrMiejsca < 1 || nrMiejsca > liczbaMiejsc) {
			return false;
		}
		if (miejscaZajete.contains(nrMiejsca)) {
			return false;
		}
		miejscaZajete.add(nrMiejsca);
		return true;
	}

	/**
	 * Zwalnia wcześniej zarezerwowane miejsce.
	 * @param nrMiejsca numer miejsca do zwolnienia
	 */
	public void zwolnijMiejsce(int nrMiejsca) {
		miejscaZajete.remove(nrMiejsca);
	}

	/**
	 * Zwraca tablicę wszystkich dostępnych numerów miejsc.
	 * @return tablica dostępnych numerów miejsc
	 */
	public int[] dajWolneMiejsca() {
		int wolnych = liczbaMiejsc - miejscaZajete.size();
		int[] wolne = new int[wolnych];
		int idx = 0;
		for (int i = 1; i <= liczbaMiejsc; i++) {
			if (!miejscaZajete.contains(i)) {
				wolne[idx++] = i;
			}
		}
		return wolne;
	}
}