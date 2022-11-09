package account;

import entity.AccountEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account implements IAccount
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

        if (checkIfDataCorrect())//TODO funkcja sprawdza czy istnieje taki login jesli tak to porownuje zahaszowane hasła jeśli sie zgadza to zwraca true jestli cos nie to false
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean register()
    {
        System.out.print("Podaj login: ");
        setLogin(scanner.nextLine());

        while (true)
        {
            System.out.print("Podaj hasło: ");
            boolean passwordIsStringEnough = setNewPassword(scanner.nextLine());
            if (passwordIsStringEnough)
            {
                break;
            }
            else
            {
                System.err.println("PODAJE HASŁO JEST ZBYT SŁABE\nSPRÓBUJ PONOWNIE");
            }
        }

        System.out.print("Podaj imie: ");
        setFirstName(scanner.nextLine());
        if (checkIfLoginIsNotInDatabase())//TODO funkcja sprawdza czy taki login został już uzyty w bazie jesli nie to zwraca true jeśli tak to zwraca false
        {
            try
            {
                transaction.begin();
                AccountEntity account = new AccountEntity();
                account.setLogin(login);
                account.setPassword(password);
                account.setName(firstName);
                manager.persist(account);
                transaction.commit();
            } finally
            {
                if (transaction.isActive())
                {
                    transaction.rollback();
                }
                manager.close();
                EntityManagerFactory.close();
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login.toLowerCase();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        hash();//TODO hash
        this.password = password;
    }

    public boolean setNewPassword(String password)
    {
        if (checkIfPasswordIsStringEnough())
        {
            hash();//TODO hash
            this.password = password;
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
    }

    private boolean checkIfPasswordIsStringEnough()
    {
        if (password.length() >= 8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();
        }
        else
            return false;
    }

    private String login;
    private String password;
    private String firstName;

    private static final EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static final EntityManager manager = EntityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = manager.getTransaction();

}