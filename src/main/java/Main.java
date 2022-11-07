import entity.AccountEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Objects;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory fac = Persistence.createEntityManagerFactory("default");
        EntityManager man = fac.createEntityManager();
        EntityTransaction tr = man.getTransaction();



        Account account = new Account();
        Menu menu = new Menu();
        String loginOption;
        while (true)
        {
            loginOption = menu.loginMenu();
            if (Objects.equals(loginOption, "sign-in"))
            {
                boolean signInCorrect = account.signIn();
                if (signInCorrect)
                {
                    System.out.println("ZALOGOWANO");
                    break;
                }
                else
                {
                    System.err.println("BŁĘDNE DANE\nSPRÓBUJ PONOWNIE");
                }
            }
            else if (loginOption.equals("register"))
            {
                boolean registerCorrect = account.register();
                if (registerCorrect)
                {
                    System.out.println("ZAREJESTROWANO");
                    break;
                }
                else
                {
                    System.err.println("PODANY LOGIN JEST ZAJĘTY\nSPRÓBUJ PONOWNIE");
                }
            }
        }

        String mainMenuOption;
        String noteOption;
        String noteID;
/*        while (true)
        {
            mainMenuOption = menu.mainMenu();
            noteOption = mainMenuOption.substring(0, mainMenuOption.indexOf('_'));
            noteID = mainMenuOption.substring(mainMenuOption.indexOf('_') + 1);

            switch (noteOption)
            {
                case "new" ->
                {

                }
                case "read" ->
                {

                }
                case "edit" ->
                {

                }
                case "delete"->
                {

                }

            }
        }*/
    }
}