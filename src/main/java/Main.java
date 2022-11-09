import account.Account;
import menu.Menu;

import java.util.Objects;

public class Main
{
    public static void main(String[] args)
    {
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
        while (true)
        {
            mainMenuOption = menu.mainMenu();
            noteOption = mainMenuOption.substring(0, mainMenuOption.indexOf('_'));
            noteID = mainMenuOption.substring(mainMenuOption.indexOf('_') + 1);

            switch (noteOption)
            {
                case "new" ->
                {
                    //TODO funkcja tworząco nową notatkę w clasie Note i insert
                }
                case "read" ->
                {
                    //TODO funkcja wyświetlająca treśc nowej notatki z klasy Note
                }
                case "edit" ->
                {
                    //TODO funkcja umożliwiające edycję zawartości notatki w klasie Note i update
                }
                case "delete"->
                {
                    //TODO funkcja usuwająca rekord w bazie delete
                }
            }
        }
    }
}