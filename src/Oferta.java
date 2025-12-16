public class Oferta {
	private IDAO _dao;
	private IFilm[] _filmy;
	private ISeans[] _seansy;
	private Rezerwacja[] _rezerwacje;
	private Klient[] _klienci;

	public Oferta(IDAO aDao) {
		throw new UnsupportedOperationException();
	}

	public IFilm dajFilm(String aIdFilmu) {
		throw new UnsupportedOperationException();
	}

	public ISeans dajSeans(String aIdSeansu) {
		throw new UnsupportedOperationException();
	}

	public ISeans[] dajSeansyFilmu(String aIdFilmu) {
		throw new UnsupportedOperationException();
	}

	public IRezerwacja dajRezerwacje(String aIdRezerwacji) {
		throw new UnsupportedOperationException();
	}

	public Klient dajKlienta(String aIdKlienta) {
		throw new UnsupportedOperationException();
	}

	public void usunFilm(String aIdFilmu) {
		throw new UnsupportedOperationException();
	}

	public void dodajSeans(ISeans aSeans) {
		throw new UnsupportedOperationException();
	}

	public void usunSeans(String aIdSeansu) {
		throw new UnsupportedOperationException();
	}

	public void anulujRezerwacje(String aIdRezerwacji) {
		throw new UnsupportedOperationException();
	}
}