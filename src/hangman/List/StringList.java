package hangman.List;

// Code from sheet 08. Just changes the int values into String values

public class StringList {

    public StringListNode front;

    public StringList() {
        this.front = null;
    }

    /**
     * Returns true if the list is empty.
     */
    public boolean isEmpty() {
        return this.front == null;
    }

    /**
     * Inserts a value at the front of list.
     *
     * @param value The value that is to be inserted.
     */
    public void addFront(final String value) {
        this.front = new StringListNode(value, this.front);
    }

    /**
     * Recursive helper method that traverses through a list inserts a new
     * node at the very end.
     *
     * @param ptr   Current list node.
     * @param value The value that is to be inserted.
     */
    private static void addBackHelper(final StringListNode ptr, final String value) {
        if (ptr.hasNext()) {
            addBackHelper(ptr.next, value);
        } else {
            ptr.next = new StringListNode(value);
        }
    }

    /**
     * Inserts a value at the end of the list.
     *
     * @param value The value that is to be inserted.
     */
    public void addBack(final String value) {
        if (this.isEmpty()) {
            this.addFront(value);
        } else {
            addBackHelper(this.front, value);
        }
    }

    /**
     * Calculates (iteratively) the number of element within
     * the list.
     *
     * @return Number of list elements/nodes.
     */
    public int size() {
        int ctr = 0;

        StringListNode ptr = this.front;

        while (ptr != null) {
            ctr++;
            ptr = ptr.next;
        }

        return ctr;
    }

    // Prints all elements of a list
    public void printList() {
        StringListNode ptr = this.front;

        while (ptr != null) {
            System.out.println(ptr.value);
            ptr = ptr.next;
        }
    }

    // Deletes given element out of list and returns the adapted list
    public StringList deleteElement(String elem) {
        StringListNode current = this.front;
        StringListNode previous = null;

        while (current != null) {
            if (current.value.equals(elem)) {
                // Element gefunden, löschen
                if (previous == null) {
                    // Das zu löschende Element ist das erste Element in der Liste
                    this.front = current.next;
                } else {
                    // Das zu löschende Element ist irgendwo in der Mitte oder am Ende der Liste
                    previous.next = current.next;
                }
                return this;
            }
            // Aktualisiere die Zeiger
            previous = current;
            current = current.next;
        }
        return this;
    }


}


