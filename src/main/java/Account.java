import java.util.Scanner;

public class Account
{
    Scanner scanner = new Scanner(System.in);

    public Account()
    {
        login = "empty";
        password = "empty";
        firstName = "empty";
    }

    public boolean signIn()
    {
        System.out.print("Podaj login: ");
        setLogin(scanner.nextLine());

        System.out.print("Podaj hasło: ");
        setPassword(scanner.nextLine());
        //TODO checkIfInDataBase
        return true;
    }

    public boolean register()
    {
        System.out.print("Podaj login: ");
        setLogin(scanner.nextLine());

        System.out.print("Podaj hasło: ");
        setPassword(scanner.nextLine());

        System.out.print("Podaj imie: ");
        setFirstName(scanner.nextLine());

        //TODO sprawdzic czy taki login nie jest w bazie
        //TODO funkcja load to database

        return true;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
        //TODO hash
        //TODO ilosc znaków znak specilany jedn duzy jedn mały
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
    }

    private String login;
    private String password;
    private String firstName;

}
