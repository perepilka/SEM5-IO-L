public interface IAdminController {

	public void dodajFilm(String aDaneFilmu);

	public String edytujFilm(String aIdFilmu, String aDaneFilmu);

	public String usunFilm(String aIdFilmu);

	public String dodajSeans(String aDaneSeansu);

	public String edytujSeans(String aIdSeansu, String aDaneSeansu);

	public String usunSeans(String aIdSeansu);

	public String edytujOferteKina(String aDaneOperacji);
}