package controller;

import model.IModel;

/**
 * Konkretna strategia do edycji istniejących filmów w ofercie kina.
 * Implementuje wzorzec Strategia dla operacji edycji filmów.
 */
public class EdytowanieFilmu extends IStrategiaEdycjiOfertyKina {

	/**
	 * Tworzy strategię EdytowanieFilmu z określonym modelem.
	 * @param modelValue instancja modelu używana do edycji filmów
	 */
	public EdytowanieFilmu(IModel modelValue) {
		super(modelValue);
	}

	/**
	 * Edytuje istniejący film w ofercie kina.
	 * @param daneOperacji dane operacji zawierające ID filmu i nowe dane
	 * @return komunikat potwierdzenia
	 */
	public String edytujOferte(String daneOperacji) {
		throw new UnsupportedOperationException();
	}
}