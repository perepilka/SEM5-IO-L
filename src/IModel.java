public interface IModel {

	public String dodajFilm(String aDaneFilmu);

	public String edytujFilm(String aIdFilmu, String aDaneFilmu);

	public String usunFilm(String aIdFilmu);

	public String dodajSeans(String aDaneSeansu);

	public String edytujSeans(String aIdSeansu, String aDaneSeansu);

	public String usunSeans(String aIdSeansu);

	public String__ pobierzRepertuar(String aKryteria);

	public String zarezerwujMiejsce(String aDaneRezerwacji);

	public String anulujRezerwacje(String aIdRezerwacji);

	public String finalizujZakup(String aDaneZakupu);
}