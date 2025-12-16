public interface IClientController {

	public String przegladajRepertuar(String aKryteria);

	public String wybierzSeans(String aIdSeansu);

	public String wybierzMiejsca(String aDaneMiejsc);

	public String rezerwujBilet(String aDaneRezerwacji);

	public String anulujRezerwacje(String aIdRezerwacji);
}