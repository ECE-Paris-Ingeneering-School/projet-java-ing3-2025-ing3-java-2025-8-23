public class Client {
    private int clientId;
    private String clientIdentifiant;
    private String clientMDP;


    // constructeur
    public Client (int clientId, String clientNom, String clientMail) {
        this.clientId = clientId;
        this.clientIdentifiant = clientNom;
        this.clientMDP = clientMail;
    }

    // getters
    public int getClientId() { return clientId; }
    public String getclientNom() { return clientIdentifiant; }
    public String getclientMail() { return clientMDP; }
}
