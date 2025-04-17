public class Main {
    public static void main(String[] args) {
        try {
            ClientDAO dao = new ClientDAO();
            LoginView view = new LoginView();
            new LoginControleur(view, dao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
