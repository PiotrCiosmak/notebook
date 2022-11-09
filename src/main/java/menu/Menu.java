package menu;

import java.util.Scanner;

public class Menu implements IMenu
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
        while (true)
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
                    String selectedNoteID = showAndSelectNote("WYBIERZ NOTATKĘ DO ODCZYTANIA");
                    return "read_" + selectedNoteID;
                }
                case '3' ->
                {
                    String selectedNoteID = showAndSelectNote("WYBIERZ NOTATKĘ DO EDYCJI");
                    return "edit_" + selectedNoteID;
                }
                case '4' ->
                {
                    String selectedNoteID = showAndSelectNote("WYBIERZ NOTATKĘ DO USUNIĘCIA");
                    return "delete_" + selectedNoteID;
                }
                case '0' -> System.exit(0);
                default -> System.err.println("Nie ma takiej opcji\n");
            }
        }
    }

    private String showAndSelectNote(String label)
    {
        Long selectedNoteNumber;
        label = label.toUpperCase();
        while (true)
        {
            System.out.println(label);
            System.out.println("---LISTA NOTATEK---");
            Long ammountOfNotes = showNotes();//TODO wyświetl listę notatek tego użytkownika z baz danych, funkcja odrazu zwraca liczbę wszystkich notatek (SELECT)
            System.out.print("Wybieram: ");
            selectedNoteNumber = scanner.nextLong();
            if (selectedNoteNumber < 1 || selectedNoteNumber > ammountOfNotes)
            {
                System.err.println("NOTATKA O TAKIM NUMERZE NIE ISTNIEJE.\nSPRÓBUJ PONOWNIE");
            }
            else
            {
                break;
            }
        }
        return ID_NOTATKI.toString();//TODO bo numer notatki z listy bedzie sie nie zgadzać z id notatki!!!
    }
}