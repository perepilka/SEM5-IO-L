package controller;

/**
 * Interfejs definiujący operacje klienckie dla zarządzania kinem.
 * Zapewnia metody do przeglądania filmów, wybierania seansów i rezerwacji biletów.
 */
public interface IClientController {

	/**
	 * Przegląda repertuar kina na podstawie kryteriów wyszukiwania.
	 * @param kryteria kryteria wyszukiwania (np. ID filmu lub tytuł)
	 * @return tekstowa reprezentacja pasujących seansów
	 */
	public String przegladajRepertuar(String kryteria);

	/**
	 * Wybiera konkretny seans do przeglądania dostępnych miejsc.
	 * @param idSeansu ID seansu do wyboru
	 * @return informacje o wybranym seansie
	 */
	public String wybierzSeans(String idSeansu);

	/**
	 * Wybiera miejsca na seans.
	 * @param daneMiejsc dane wyboru miejsc
	 * @return potwierdzenie wybranych miejsc
	 */
	public String wybierzMiejsca(String daneMiejsc);

	/**
	 * Rezerwuje bilet na seans.
	 * @param daneRezerwacji dane rezerwacji w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia ze szczegółami rezerwacji
	 */
	public String rezerwujBilet(String daneRezerwacji);

	/**
	 * Anuluje istniejącą rezerwację.
	 * @param idRezerwacji ID rezerwacji do anulowania
	 * @return komunikat potwierdzenia
	 */
	public String anulujRezerwacje(String idRezerwacji);
}