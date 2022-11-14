package note;

import entity.NoteEntity;
import jakarta.persistence.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Note implements INote
{
    Scanner scanner = new Scanner(System.in);

    public Note()
    {
        title = "empty";
        content = "empty";
    }

    public void createNewNote(Long accountID)
    {
        System.out.println("---NOWA NOTATKA---");
        System.out.println("Podaj treśc notatki (aby zakończyć wpisz -END-:)");
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            lines.add(scanner.nextLine());
            if (lines.get(lines.size() - 1).equalsIgnoreCase("-END-"))
            {
                lines.remove(lines.size() - 1);
                break;
            }
        }
        setContent(String.join("\n", lines));

        while (true)
        {
            System.out.print("Podaj tytuł notatki:");
            boolean titleLengthCorrect = setTitle(scanner.nextLine());
            if (titleLengthCorrect)
                break;
            else
                System.err.println("TYTUŁ NIE MOŻE BYĆ DŁUŻSZY NIŻ 1000 ZNAKÓW\nSPRÓBUJ PONOWNIE\n");
        }

        EntityManager manager = factory.createEntityManager();
        Session session = manager.unwrap(org.hibernate.Session.class);
        NoteEntity note = new NoteEntity();
        try
        {
            note.setTitle(title);
            note.setContent(content);
            note.setIdAccount(accountID);
            manager.persist(note);
            session.beginTransaction();
            session.save(note);
            session.getTransaction().commit();
        } finally
        {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();
        }
    }

    public void readNote(Long noteID)
    {
        EntityManager manager = factory.createEntityManager();
        Query q = manager.createNativeQuery("SELECT n.content FROM Note n WHERE n.id_note = ?").setParameter(1, noteID);
        List note = q.getResultList();
        System.out.println("ZAWARTOŚĆ:\n" + note.get(0));
    }

    public void updateNote(Long noteID)
    {
        EntityManager manager = factory.createEntityManager();
        Session session = manager.unwrap(org.hibernate.Session.class);
        try
        {
            session.beginTransaction();
            NoteEntity selectedNote = session.get(NoteEntity.class, noteID);
            selectedNote.setContent(inputNewContent());
            session.getTransaction().commit();
        } finally
        {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();
        }
        System.out.println("NOTATKA ZOSTAŁA ZAKTUALIZOWANA");
    }

    public void deleteNote(Long noteID)
    {
        EntityManager manager = factory.createEntityManager();
        Session session = manager.unwrap(org.hibernate.Session.class);
        try
        {
            session.beginTransaction();
            Query q = manager.createNativeQuery("DELETE FROM Note n WHERE n.id_note = ?");
            q.setParameter(1,noteID);
            q.executeUpdate();
            session.getTransaction().commit();
        } finally
        {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();
        }
        //NIE DZIAŁA
        /*EntityManager manager = factory.createEntityManager();
        Session session = manager.unwrap(org.hibernate.Session.class);
        try
        {
            session.beginTransaction();
            NoteEntity selectedNote = session.get(NoteEntity.class, noteID);
            session.delete(selectedNote);
            session.getTransaction().commit();
        } finally
        {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();
        }*/
        System.out.println("NOTATKA ZOSTAŁA USUNIĘTA");
    }

    public String getTitle()
    {
        return title;
    }

    public boolean setTitle(String title)
    {
        if (title.length() > 1000)
            return false;
        this.title = title;
        return true;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    private String inputNewContent()
    {
        System.out.println("Podaj treśc notatki (aby zakończyć wpisz -END-:)");
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            lines.add(scanner.nextLine());
            if (lines.get(lines.size() - 1).equalsIgnoreCase("-END-"))
            {
                lines.remove(lines.size() - 1);
                break;
            }
        }
        return String.join("\n", lines);
    }

    private String title;
    private String content;
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
}