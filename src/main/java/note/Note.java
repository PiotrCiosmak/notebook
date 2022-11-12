package note;

import entity.NoteEntity;
import jakarta.persistence.*;
import org.hibernate.Session;

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

    public void createNewNote(Long AccountID)
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

        jakarta.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

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
    }

    public void readNote(Long noteID)
    {
        jakarta.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT n.content FROM Note n WHERE n.id_note = ?").setParameter(1, noteID);
        List note = q.getResultList();
        System.out.println("ZAWARTOŚĆ:\n" + note.get(0));
    }

    public void deleteNote(Long noteID)
    {
        EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.createNativeQuery("delete from Note where id_note = 2");
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
}