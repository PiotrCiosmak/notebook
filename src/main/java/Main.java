import account.Account;
import menu.Menu;
import note.Note;

import java.util.Objects;

public class Main
{
    public static void main(String[] args)
    {
        Account account = new Account();
        Menu menu = new Menu();
        String loginOption;
        Long accountID = -1L;
        while (true)
        {
            loginOption = menu.loginMenu();
            if (Objects.equals(loginOption, "sign-in"))
            {
                accountID = account.signIn();
                if (accountID != -1L)
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
                accountID = account.register();
                if (accountID != -1L)
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
        String noteID = null;
        while (true)
        {
            mainMenuOption = menu.mainMenu(accountID);
            if (mainMenuOption.equals("lack"))
            {
                noteOption = "lack";
            }
            else if (!mainMenuOption.equals("new"))
            {
                noteOption = mainMenuOption.substring(0, mainMenuOption.indexOf('_'));
                noteID = mainMenuOption.substring(mainMenuOption.indexOf('_') + 1);
            }
            else
            {
                noteOption = "new";
            }

            Note note = new Note();
            switch (noteOption)
            {
                case "new" ->
                {
                    note.createNewNote(accountID);
                }
                case "read" ->
                {
                    //TODO funkcja wyświetlająca treśc nowej notatki z klasy Note select
                }
                case "edit" ->
                {
                    //TODO funkcja umożliwiające edycję zawartości notatki w klasie Note i update
                }
                case "delete" ->
                {
                    //TODO funkcja usuwająca rekord w bazie delete
                }
                case "lack" ->
                {
                    System.err.println("NIE MASZ ŻADNEJ NOTATKI.\nUTWÓRZ NOWĄ NOTATKĘ");
                }
            }
        }
    }
}