import account.Account;
import menu.Menu;
import note.Note;


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
            if (loginOption.equals("sign-in"))
            {
                accountID = account.signIn();
                if (accountID != -1L)
                {
                    System.out.println("ZALOGOWANO");
                    break;
                }
                else
                {
                    System.err.println("BŁĘDNE DANE\nSPRÓBUJ PONOWNIE\n");
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
                    System.err.println("PODANY LOGIN JEST ZAJĘTY\nSPRÓBUJ PONOWNIE\n");
                }
            }
        }

        String mainMenuOption;
        String noteOption;
        Long noteID = -1L;
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
                noteID = Long.parseLong(mainMenuOption.substring(mainMenuOption.indexOf('_') + 1));
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
                    note.readNote(noteID);
                }
                case "edit" ->
                {
                    note.updateNote(noteID);
                }
                case "delete" ->
                {
                    note.deleteNote(noteID);
                }
                case "lack" ->
                {
                    System.err.println("NIE MASZ ŻADNEJ NOTATKI.\nUTWÓRZ NOWĄ NOTATKĘ");
                }
            }
        }
    }
}