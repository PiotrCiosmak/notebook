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
            if (lines.get(lines.size() - 1).equals("-END-"))
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
            session.close();
        } finally
        {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();
        }
    }

    public void readNote(Long noteID)
    {
        Session session = manager.unwrap(org.hibernate.Session.class);
        try
        {
            session.beginTransaction();
            NoteEntity selectedNote = session.get(NoteEntity.class,noteID);
            session.getTransaction().commit();
            System.out.println("ZAWARTOŚĆ:\n" + selectedNote.getContent());
            session.close();
        } finally
        {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();
        }
    }

    public void deleteNote(Long noteID)
    {
        jakarta.persistence.EntityManagerFactory EntityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = EntityManagerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        Session s = manager.unwrap(org.hibernate.Session.class);

        transaction.begin();
        Query q = manager.createNativeQuery("SELECT * FROM Note n WHERE n.id_note = ?").setParameter(1, noteID);
        List<NoteEntity> lista = q.getResultList();
        manager.remove(lista.get(0));
        manager.flush();
        manager.clear();
        transaction.commit();
    }

    public void updateNote(Long noteID)
    {
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

    private String title;
    private String content;
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");//TODO dodać do schematu
    private static final EntityManager manager = factory.createEntityManager();//TODO dodać do schematu
}