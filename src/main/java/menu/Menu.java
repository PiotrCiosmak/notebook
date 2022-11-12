package menu;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
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
                default -> System.err.println("NIE MA TAKIEJ OPCJI\nSPRÓBUJ PONOWNIE\n");
            }
        }
    }

    public String mainMenu(Long accountID)
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
                    String selectedNoteID = showAndSelectNote("WYBIERZ NOTATKĘ DO ODCZYTANIA", accountID);
                    if (selectedNoteID.equals("lack"))
                        return "lack";
                    return "read_" + selectedNoteID;
                }
                case '3' ->
                {
                    String selectedNoteID = showAndSelectNote("WYBIERZ NOTATKĘ DO EDYCJI", accountID);
                    if (selectedNoteID.equals("lack"))
                        return "lack";
                    return "edit_" + selectedNoteID;
                }
                case '4' ->
                {
                    String selectedNoteID = showAndSelectNote("WYBIERZ NOTATKĘ DO USUNIĘCIA", accountID);
                    if (selectedNoteID.equals("lack"))
                        return "lack";
                    return "delete_" + selectedNoteID;
                }
                case '0' -> System.exit(0);
                default -> System.err.println("Nie ma takiej opcji\n");
            }
        }
    }

    private String showAndSelectNote(String label, Long accountID)
    {
        Long selectedNoteNumber;
        label = label.toUpperCase();
        while (true)
        {
            System.out.println(label);
            System.out.println("---LISTA NOTATEK---");
            Long amountOfNotes = showNotes(accountID);
            if (amountOfNotes == 0L)
            {
                return "lack";
            }
            System.out.print("Wybieram: ");
            selectedNoteNumber = scanner.nextLong();
            if (selectedNoteNumber < 1 || selectedNoteNumber > amountOfNotes)
            {
                System.err.println("NOTATKA O TAKIM NUMERZE NIE ISTNIEJE.\nSPRÓBUJ PONOWNIE");
            }
            else
            {
                break;
            }
        }
        Long selectedNoteID = selectNoteID(selectedNoteNumber, accountID);
        return selectedNoteID.toString();
    }

    private Long showNotes(Long accountID)
    {
        jakarta.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT n.title FROM Note n WHERE n.id_account = ?").setParameter(1, accountID);
        List note = q.getResultList();
        if (note.isEmpty())
        {
            return 0L;
        }
        for (int i = 0; i < note.size(); ++i)
            System.out.println(i + 1 + "." + note.get(i).toString());
        return Long.parseLong(String.valueOf(note.size()));
    }

    private Long selectNoteID(Long selectedNoteNumber, Long accountID)
    {
        jakarta.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT n.id_note FROM Note n WHERE n.id_account = ?").setParameter(1, accountID);
        List note = q.getResultList();
        return Long.parseLong(note.get(Integer.parseInt(String.valueOf(selectedNoteNumber)) - 1).toString());
    }
}