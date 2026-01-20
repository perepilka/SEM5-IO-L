package controller;

import model.IModel;

/**
 * Konkretna strategia do dodawania nowych filmów do oferty kina.
 * Implementuje wzorzec Strategia dla operacji dodawania filmów.
 */
public class DodanieNowegoFilmu extends IStrategiaEdycjiOfertyKina {

	/**
	 * Tworzy strategię DodanieNowegoFilmu z określonym modelem.
	 * @param modelValue instancja modelu używana do dodawania filmów
	 */
	public DodanieNowegoFilmu(IModel modelValue) {
		super(modelValue);
	}

	/**
	 * Dodaje nowy film do oferty kina.
	 * @param daneFilmu dane filmu w formacie rozdzielanym średnikami
	 * @return komunikat potwierdzenia z ID filmu
	 */
	public String edytujOferte(String daneFilmu) {
		return model.dodajFilm(daneFilmu);
	}
}