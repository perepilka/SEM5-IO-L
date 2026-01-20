package controller;

import model.IModel;

/**
 * Kontroler dla operacji klienckich w systemie zarządzania kinem.
 * Zapewnia funkcjonalność do przeglądania filmów, wybierania seansów i rezerwacji biletów.
 */
public class ClientController {
	private IModel model;

	/**
	 * Tworzy ClientController z określonym modelem.
	 * @param modelValue instancja modelu używana do operacji klienckich
	 */
	public ClientController(IModel modelValue) {
		model = modelValue;
	}

	/**
	 * Przegląda repertuar kina na podstawie kryteriów wyszukiwania.
	 * @param kryteria kryteria wyszukiwania (np. ID filmu lub tytuł)
	 * @return tekstowa reprezentacja pasujących seansów
	 */
	public String przegladajRepertuar(String kryteria) {
		return model.pobierzRepertuar(kryteria);
	}

	/**
	 * Wybiera konkretny seans do przeglądania dostępnych miejsc.
	 * @param idSeansu ID seansu do wyboru
	 * @return informacje o wybranym seansie
	 */
	public String wybierzSeans(String idSeansu) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Wybiera miejsca na seans.
	 * @param daneMiejsc dane wyboru miejsc
	 * @return potwierdzenie wybranych miejsc
	 */
	public String wybierzMiejsca(String daneMiejsc) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Rezerwuje bilet na seans.
	 * @param daneRezerwacji dane rezerwacji w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia ze szczegółami rezerwacji
	 */
	public String rezerwujBilet(String daneRezerwacji) {
		return model.zarezerwujMiejsce(daneRezerwacji);
	}

	/**
	 * Anuluje istniejącą rezerwację.
	 * @param idRezerwacji ID rezerwacji do anulowania
	 * @return komunikat potwierdzenia
	 */
	public String anulujRezerwacje(String idRezerwacji) {
		throw new UnsupportedOperationException();
	}
}