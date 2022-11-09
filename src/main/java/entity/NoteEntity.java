package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "note", schema = "public", catalog = "notebook")
public class NoteEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_note")
    private long idNote;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "id_account")
    private long idAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account", referencedColumnName = "id_account", insertable = false, updatable = false, nullable = false)
    private AccountEntity accountByIdAccount;

    public long getIdNote()
    {
        return idNote;
    }

    public void setIdNote(long idNote)
    {
        this.idNote = idNote;
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

    public long getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(long idAccount)
    {
        this.idAccount = idAccount;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteEntity that = (NoteEntity) o;

        if (idNote != that.idNote) return false;
        if (idAccount != that.idAccount) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (idNote ^ (idNote >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (idAccount ^ (idAccount >>> 32));
        return result;
    }

    public AccountEntity getAccountByIdAccount()
    {
        return accountByIdAccount;
    }

    public void setAccountByIdAccount(AccountEntity accountByIdAccount)
    {
        this.accountByIdAccount = accountByIdAccount;
    }
}
