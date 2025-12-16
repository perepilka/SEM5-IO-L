package controller;

import model.IModel;

/**
 * Kontroler dla operacji administracyjnych w systemie zarządzania kinem.
 * Zapewnia funkcjonalność do zarządzania filmami, seansami i ofertą kina.
 */
public class AdminController {

  private IModel model;
  private EdytowanieOfertyKina edytorOferty;

  /**
   * Tworzy AdminController z określonym modelem.
   * @param Model instancja modelu używana do operacji administracyjnych
   */
  public AdminController(IModel Model) {
    model = Model;
  }

  /**
   * Dodaje nowy film do systemu kina.
   * @param DaneFilmu dane filmu w formacie rozdzielanym średnikami
   * @return komunikat potwierdzenia z ID filmu
   */
  public String dodajFilm(String DaneFilmu) {
    EdytowanieOfertyKina context = new EdytowanieOfertyKina(model);
    context.wybierzOpcje("dodanie");
    return context.wykonajEdycje(DaneFilmu);
  }

  /**
   * Edytuje istniejący film w systemie kina.
   * @param idFilmu ID filmu do edycji
   * @param daneFilmu nowe dane filmu
   * @return komunikat potwierdzenia
   */
  public String edytujFilm(String idFilmu, String daneFilmu) {
    throw new UnsupportedOperationException();
  }

  /**
   * Usuwa film z systemu kina.
   * @param idFilmu ID filmu do usunięcia
   * @return komunikat potwierdzenia
   */
  public String usunFilm(String idFilmu) {
    throw new UnsupportedOperationException();
  }

  /**
   * Dodaje nowy seans do systemu kina.
   * @param daneSeansu dane seansu w formacie rozdzielanym średnikami
   * @return komunikat potwierdzenia z ID seansu
   */
  public String dodajSeans(String daneSeansu) {
    throw new UnsupportedOperationException();
  }

  /**
   * Edytuje istniejący seans w systemie kina.
   * @param idSeansu ID seansu do edycji
   * @param daneSeansu nowe dane seansu
   * @return komunikat potwierdzenia
   */
  public String edytujSeans(String idSeansu, String daneSeansu) {
    throw new UnsupportedOperationException();
  }

  /**
   * Usuwa seans z systemu kina.
   * @param idSeansu ID seansu do usunięcia
   * @return komunikat potwierdzenia
   */
  public String usunSeans(String idSeansu) {
    throw new UnsupportedOperationException();
  }

  /**
   * Edytuje ofertę kina używając określonych danych operacji.
   * @param daneOperacji dane operacji określające typ edycji
   * @return komunikat potwierdzenia
   */
  public String edytujOferteKina(String daneOperacji) {
    throw new UnsupportedOperationException();
  }
}