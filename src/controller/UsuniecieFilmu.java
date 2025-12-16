package controller;

import model.IModel;

/**
 * Konkretna strategia do usuwania filmów z oferty kina.
 * Implementuje wzorzec Strategia dla operacji usuwania filmów.
 */
public class UsuniecieFilmu extends IStrategiaEdycjiOfertyKina {

	/**
	 * Tworzy strategię UsuniecieFilmu z określonym modelem.
	 * @param modelValue instancja modelu używana do usuwania filmów
	 */
	public UsuniecieFilmu(IModel modelValue) {
		super(modelValue);
	}

	/**
	 * Usuwa film z oferty kina.
	 * @param daneOperacji dane operacji zawierające ID filmu do usunięcia
	 * @return komunikat potwierdzenia
	 */
	public String edytujOferte(String daneOperacji) {
		throw new UnsupportedOperationException();
	}
}