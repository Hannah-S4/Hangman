package hangman.List;

public class StringListNode {

    public String value;
    public StringListNode next;

    public boolean hasNext() {
        return this.next != null;
    }

    public StringListNode(final String value) {
        this(value, null);
    }

    public StringListNode(final String value, final StringListNode next) {
        this.value = value;
        this.next = next;
    }

}