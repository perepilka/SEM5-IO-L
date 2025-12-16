package controller;

import model.IModel;

/**
 * Bazowa klasa strategii do edycji oferty kina.
 * Implementuje wzorzec Strategia dla różnych typów modyfikacji oferty kina.
 */
public class IStrategiaEdycjiOfertyKina {
	protected IModel model;

	/**
	 * Tworzy strategię z określonym modelem.
	 * @param modelValue instancja modelu używana do operacji edycji
	 */
	public IStrategiaEdycjiOfertyKina(IModel modelValue) {
		model = modelValue;
	}

	/**
	 * Edytuje ofertę kina na podstawie danych operacji.
	 * Ta metoda powinna być nadpisana przez konkretne implementacje strategii.
	 * @param daneOperacji dane operacji określające co edytować
	 * @return wynik operacji edycji
	 */
	public String edytujOferte(String daneOperacji) {
		throw new UnsupportedOperationException();
	}
}