import java.util.Scanner;

public class Menu
{
    Scanner scanner = new Scanner(System.in);

    public String loginMenu()
    {
        char selectedOption;

        while (true)
        {
            System.out.println("---MENU LOGOWANIA---");
            System.out.println("1. Zaloguj się");
            System.out.println("2. Zarejestruj się");
            System.out.println("0. Wyjdź");
            System.out.print("Wybieram: ");
            selectedOption = scanner.next().charAt(0);
            switch (selectedOption)
            {
                case '1' ->
                {
                    return "sign-in";
                }
                case '2' ->
                {
                    return "register";
                }
                case '0' -> System.exit(0);
                default -> System.err.println("Nie ma takiej opcji\n");
            }
        }
    }

    public String mainMenu()
    {
        char selectedOption;
        while(true)
        {
            System.out.println("---MENU GŁÓWNE---");
            System.out.println("1. Utwórz nową notatkę");
            System.out.println("2. Odczytaj notatkę");
            System.out.println("3. Edytuj notatkę");
            System.out.println("4. Usuń notatkę");
            System.out.println("0. Wyjdź");
            System.out.print("Wybieram: ");
            selectedOption = scanner.next().charAt(0);
            switch (selectedOption)
            {
                case '1' ->
                {
                    return "new";
                }
                case '2' ->
                {
                    //TODO wybierz notatkę z bazy
                    //TODO zwróc ID tej notatki
                    return "read_" + "ID_NOTATKI";
                }
                case '3' ->
                {
                    //TODO wybierz notatkę z bazy
                    //TODO zwróc ID tej notatki
                    return "edit_" + "ID_NOTATKI";
                }
                case 'r' ->
                {
                    //TODO wybierz notatkę z bazy
                    //TODO zwróc ID tej notatki
                    return "delete_" + "ID_NOTATKI";
                }
                case '0' -> System.exit(0);
                default -> System.err.println("Nie ma takiej opcji\n");
            }
        }
    }
}
