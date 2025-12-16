public interface IDAO {

	public void dodajWpisDoLogu(String aZdarzenie);

	public String znajdzFilm(String aIdFilmu);

	public String dodajFilm(String aFilm);

	public void edytujFilm(String aFilm);

	public void usunFilm(String aIdFilmu);

	public String znajdzSeans(String aIdSeansu);

	public String[] znajdzSeansyFilmu(String aIdFilmu);

	public String dodajSeans(String aSeans);

	public void edytujSeans(String aSeans);

	public void usunSeans(String aIdSeansu);

	public String znajdzRezerwacje(String aIdRezerwacji);

	public String dodajRezerwacje(String aRezerwacja);

	public void usunRezerwacje(String aIdRezerwacji);

	public String znajdzKlienta(String aIdKlienta);

	public String dodajKlienta(String aKlient);
}