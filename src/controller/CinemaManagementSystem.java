package controller;

import model.*;

/**
 * Główna klasa aplikacji dla Systemu Zarządzania Kinem.
 * Demonstruje funkcjonalność systemu poprzez różne scenariusze.
 */
public class CinemaManagementSystem {

	/**
	 * Główny punkt wejścia dla aplikacji Systemu Zarządzania Kinem.
	 * Uruchamia scenariusze demonstracyjne dla operacji administratora i klienta.
	 * @param args argumenty linii poleceń (nieużywane)
	 */
	public static void main(String[] args) {
		System.out.println("=== System Zarządzania Kinem ===\n");
		
		// Inicjalizacja systemu
		IDAO dao = new DAO();
		Oferta oferta = new Oferta(dao);
		IModel model = new Model(oferta, dao);
		
		AdminController adminController = new AdminController(model);
		ClientController clientController = new ClientController(model);
		
		// Scenariusz 1: Administrator dodaje filmy
		System.out.println("--- Scenariusz 1: Administrator dodaje filmy ---");
		String filmData1 = "1;Incepcja;Thriller zaginający umysł;148;Sci-Fi;25.50";
		String result1 = adminController.dodajFilm(filmData1);
		System.out.println("Wynik: " + result1);
		
		String filmData2 = "2;Matrix;Haker odkrywa rzeczywistość;136;Akcja;22.00";
		String result2 = adminController.dodajFilm(filmData2);
		System.out.println("Wynik: " + result2);
		
		String filmData3 = "3;Titanic;Epicka romansowa katastrofa;194;Dramat;20.00";
		String result3 = adminController.dodajFilm(filmData3);
		System.out.println("Wynik: " + result3);
		
		// Scenariusz 2: Administrator dodaje seanse
		System.out.println("\n--- Scenariusz 2: Administrator dodaje seanse ---");
		String seansData1 = "1;2024-12-20 18:00;Sala1;100";
		String seansResult1 = model.dodajSeans(seansData1);
		System.out.println("Wynik: " + seansResult1);
		
		String seansData2 = "1;2024-12-20 21:00;Sala2;80";
		String seansResult2 = model.dodajSeans(seansData2);
		System.out.println("Wynik: " + seansResult2);
		
		String seansData3 = "2;2024-12-21 19:00;Sala1;100";
		String seansResult3 = model.dodajSeans(seansData3);
		System.out.println("Wynik: " + seansResult3);
		
		// Scenariusz 3: Klient przegląda repertuar
		System.out.println("\n--- Scenariusz 3: Klient przegląda repertuar ---");
		String repertuar = clientController.przegladajRepertuar("1");
		System.out.println(repertuar);
		
		// Scenariusz 4: Klient rezerwuje bilety
		System.out.println("--- Scenariusz 4: Klient rezerwuje bilety ---");
		String rezerwacjaData1 = "R1;S1;K001;15;25.50";
		String rezerwacjaResult1 = clientController.rezerwujBilet(rezerwacjaData1);
		System.out.println("Wynik: " + rezerwacjaResult1);
		
		String rezerwacjaData2 = "R2;S1;K002;16;25.50";
		String rezerwacjaResult2 = clientController.rezerwujBilet(rezerwacjaData2);
		System.out.println("Wynik: " + rezerwacjaResult2);
		
		String rezerwacjaData3 = "R3;S2;K003;10;22.00";
		String rezerwacjaResult3 = clientController.rezerwujBilet(rezerwacjaData3);
		System.out.println("Wynik: " + rezerwacjaResult3);
		
		// Scenariusz 5: Testowanie wzorca Dekorator
		System.out.println("\n--- Scenariusz 5: Testowanie wzorca Dekorator ---");
		System.out.println("Obliczanie ceny filmu standardowego:");
		IFabrykaFilmu fabrykaStd = new FabrykaStandardowegoFilmu();
		IFilm filmStd = fabrykaStd.utworzFilm("99;Film Testowy;Opis testowy;120;Akcja;30.00");
		System.out.println("  Standardowy: " + filmStd.dajTytul() + " - " + filmStd.dajCeneSeansow() + " PLN");
		
		System.out.println("\nObliczanie ceny filmu VIP:");
		String[] vipExtras = {"Miejsca premium", "Darmowy popcorn", "Spotkanie z twórcami"};
		IFabrykaFilmu fabrykaVIP = new FabrykaFilmuVIP(15.00, vipExtras);
		IFilm filmVIP = fabrykaVIP.utworzFilm("99;Film Testowy VIP;Opis testowy;120;Akcja;30.00");
		System.out.println("  VIP: " + filmVIP.dajTytul() + " - " + filmVIP.dajCeneSeansow() + " PLN");
		System.out.println("  Opis: " + filmVIP.dajOpis());
		
		System.out.println("\nObliczanie ceny filmu promocyjnego:");
		IFabrykaFilmu fabrykaPromo = new FabrykaFilmuZPromocja(20, "Specjalna oferta weekendowa");
		IFilm filmPromo = fabrykaPromo.utworzFilm("99;Film Testowy Promo;Opis testowy;120;Akcja;30.00");
		System.out.println("  Promocja: " + filmPromo.dajTytul() + " - " + filmPromo.dajCeneSeansow() + " PLN");
		System.out.println("  Opis: " + filmPromo.dajOpis());
		
		System.out.println("\n=== Wszystkie scenariusze zakończone ===");
	}
}