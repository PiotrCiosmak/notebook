package note;

import entity.NoteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Note
{
    Scanner scanner = new Scanner(System.in);

    public Note()
    {
        title = "empty";
        content = "empty";
    }

    public boolean createNewNote(Long AccountID)
    {
        System.out.println("---NOWA NOTATKA---");

        System.out.println("Podaj treśc notatki (aby zakończyć wpisz -END-:");
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            lines.add(scanner.nextLine());
            if (lines.get(lines.size() - 1).equals("-END-"))
            {
                lines.remove(lines.size() - 1);
                break;
            }
        }
        content = String.join("\n", lines);

        System.out.print("Podaj tytuł notatki:");
        title = scanner.nextLine();

        try
        {
            transaction.begin();
            NoteEntity note = new NoteEntity();
            note.setTitle(title);
            note.setContent(content);
            note.setIdAccount(AccountID);
            manager.persist(note);
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
        return true;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    private String title;
    private String content;

    private static final jakarta.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static final EntityManager manager = EntityManagerFactory.createEntityManager();
    private static final EntityTransaction transaction = manager.getTransaction();
}