package account;

import entity.AccountEntity;
import jakarta.persistence.*;

import java.util.List;
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

    public Long signIn()
    {
        System.out.print("Podaj login: ");
        setLogin(scanner.nextLine());

        System.out.print("Podaj hasło: ");
        setPassword(scanner.nextLine());

        return checkIfDataCorrect();
    }

    //zwroc ACcountID
    public Long register()
    {
        System.out.print("Podaj login: ");
        setLogin(scanner.nextLine());
        if (!checkIfLoginIsNotInDatabase())
            return -1L;

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
        try
        {
            transaction.begin();
            AccountEntity account = new AccountEntity();
            account.setLogin(login);
            account.setPassword(password);
            account.setName(firstName);
            manager.persist(account);
            manager.flush();
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
        return checkIfDataCorrect();
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
        //hash();//TODO hash
        this.password = password;
    }

    public boolean setNewPassword(String password)
    {
        if (checkIfPasswordIsStringEnough(password))
        {
            //hash();//TODO hash
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

    private boolean checkIfLoginIsNotInDatabase()
    {
        Query q = manager.createNativeQuery("SELECT a.login FROM Account a WHERE a.login = ?").setParameter(1, getLogin());
        List account = q.getResultList();
        return account.isEmpty();
    }

    private Long checkIfDataCorrect()
    {
        Query q = manager.createNativeQuery("SELECT a.id_account FROM Account a WHERE a.login = ? AND a.password = ?").setParameter(1, getLogin()).setParameter(2,getPassword());
        List account = q.getResultList();
        if(!account.isEmpty())
        {
            return Long.parseLong(account.get(0).toString());
        }
        return -1L;
    }

    private boolean checkIfPasswordIsStringEnough(String password)
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