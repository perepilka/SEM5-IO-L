public class Seans implements ISeans {
	private String _id;
	private IFilm _film;
	private String _idSali;
	private Set _miejscaZajete;
	private int _liczbaMiejsc;

	public Seans(String aId, IFilm aFilm, String aData, String aSala, int aMiejsca) {
		throw new UnsupportedOperationException();
	}

	public String dajId() {
		throw new UnsupportedOperationException();
	}

	public IFilm dajFilm() {
		throw new UnsupportedOperationException();
	}

	public String dajDate() {
		throw new UnsupportedOperationException();
	}

	public boolean zarezerwujMiejsce(int aNrMiejsca) {
		throw new UnsupportedOperationException();
	}

	public void zwolnijMiejsce(int aNrMiejsca) {
		throw new UnsupportedOperationException();
	}

	public int[] dajWolneMiejsca() {
		throw new UnsupportedOperationException();
	}
}