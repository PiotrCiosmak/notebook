package account;

import entity.AccountEntity;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
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
        while (true)
        {
            System.out.print("Podaj login: ");
            boolean loginLengthCorrect = setLogin(scanner.nextLine());
            if (loginLengthCorrect)
                break;
            else
                System.err.println("LOGIN NIE MOŻE BYĆ DŁUŻSZY NIŻ 100 ZNAKÓW\nSPRÓBUJ PONOWNIE\n");
        }

        System.out.print("Podaj hasło: ");
        setPassword(scanner.nextLine());
        if (!checkIfPasswordIsCorrect())
            return -1L;
        hashAndSetPassword();
        return checkIfDataCorrect();
    }

    public Long register()
    {
        while (true)
        {
            System.out.print("Podaj login: ");
            boolean loginLengthCorrect = setLogin(scanner.nextLine());
            if (loginLengthCorrect)
                break;
            else
                System.err.println("LOGIN NIE MOŻE BYĆ DŁUŻSZY NIŻ 100 ZNAKÓW\nSPRÓBUJ PONOWNIE\n");
        }
        if (!checkIfLoginIsNotInDatabase())
            return -1L;

        while (true)
        {
            System.out.print("Podaj hasło: ");
            boolean passwordIsStringEnough = hashAndSetNewPassword(scanner.nextLine());
            if (passwordIsStringEnough)
                break;
            else
                System.err.println("PODAJE HASŁO JEST ZBYT SŁABE LUB JEST DŁUŻSZE NIZ 100 ZNAKÓW\nSPRÓBUJ PONOWNIE\n");
        }
        while (true)
        {
            System.out.print("Podaj imie: ");
            boolean firstNameLengthCorrect = setFirstName(scanner.nextLine());
            if (firstNameLengthCorrect)
                break;
            else
                System.err.println("IMIE NIE MOŻE BYĆ DŁUŻSZY NIŻ 100 ZNAKÓW\nSPRÓBUJ PONOWNIE\n");
        }

        EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

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

    public boolean setLogin(String login)
    {
        if (login.length() > 100)
            return false;
        this.login = login.toLowerCase();
        return true;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    private String getHashedPassword()
    {
        EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT a.password FROM Account a WHERE a.login = ?").setParameter(1, getLogin());
        List password = q.getResultList();
        return password.get(0).toString();
    }

    private void hashAndSetPassword()
    {
        this.password = hash(getPassword());
    }

    private boolean hashAndSetNewPassword(String password)
    {
        if(password.length()>100)
            return false;
        if (checkIfPasswordIsStringEnough(password))
        {
            this.password = hash(password);
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

    public boolean setFirstName(String firstName)
    {
        if (firstName.length() > 100)
            return false;
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        return true;
    }

    private boolean checkIfLoginIsNotInDatabase()
    {
        EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT a.login FROM Account a WHERE a.login = ?").setParameter(1, getLogin());
        List account = q.getResultList();
        return account.isEmpty();
    }

    private boolean checkIfPasswordIsCorrect()
    {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return bcrypt.matches(password, getHashedPassword());
    }

    private Long checkIfDataCorrect()
    {
        EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT a.id_account FROM Account a WHERE a.login = ? AND a.password = ?").setParameter(1, getLogin()).setParameter(2, getHashedPassword());
        List account = q.getResultList();
        if (!account.isEmpty())
            return Long.parseLong(account.get(0).toString());
        return -1L;
    }

    private String hash(String password)
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
        return bCryptPasswordEncoder.encode(password);
    }

    private boolean checkIfPasswordIsStringEnough(String password)
    {
        if (password.length() >= 8)
        {
            Pattern capitalLetter = Pattern.compile("[A-Z]");
            Pattern lowerLetter = Pattern.compile("[a-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasCapitalLetter = capitalLetter.matcher(password);
            Matcher hasLowerLetter = lowerLetter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasCapitalLetter.find() && hasLowerLetter.find() && hasDigit.find() && hasSpecial.find();
        }
        else
            return false;
    }

    private String login;
    private String password;
    private String firstName;
}