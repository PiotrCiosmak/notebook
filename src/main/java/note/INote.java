package note;

public interface INote
{
    void createNewNote(Long AccountID);

    void readNote(Long noteID);

    void deleteNote(Long noteID);

    void updateNote(Long noteID);
}