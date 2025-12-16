package controller;

import model.IModel;

/**
 * Klasa kontekstowa do edycji oferty kina używając wzorca Strategia.
 * Umożliwia wybór różnych strategii edycji w czasie działania programu.
 */
public class EdytowanieOfertyKina {
	private IModel model;
	private IStrategiaEdycjiOfertyKina strategia;

	/**
	 * Tworzy EdytowanieOfertyKina z określonym modelem.
	 * @param modelValue instancja modelu używana do operacji edycji
	 */
	public EdytowanieOfertyKina(IModel modelValue) {
		model = modelValue;
	}

	/**
	 * Wybiera odpowiednią strategię edycji na podstawie typu operacji.
	 * @param typOperacji typ operacji ("dodanie", "edycja", lub "usuniecie")
	 */
	public void wybierzOpcje(String typOperacji) {
		if ("dodanie".equals(typOperacji)) {
			strategia = new DodanieNowegoFilmu(model);
		} else if ("edycja".equals(typOperacji)) {
			strategia = new EdytowanieFilmu(model);
		} else if ("usuniecie".equals(typOperacji)) {
			strategia = new UsuniecieFilmu(model);
		}
	}

	/**
	 * Wykonuje wybraną strategię edycji.
	 * @param daneFilmu dane filmu do przetworzenia
	 * @return wynik operacji edycji
	 */
	public String wykonajEdycje(String daneFilmu) {
		return strategia.edytujOferte(daneFilmu);
	}
}