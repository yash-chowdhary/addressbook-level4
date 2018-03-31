package seedu.club.model.task;

//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.model.task.exceptions.DuplicateTaskException;
import seedu.club.model.task.exceptions.TaskNotFoundException;

/**
 *
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs an empty UniqueTaskList.
     */
    public UniqueTaskList() {}

    /**
     * Constructs a UniqueTaskList using given tasks.
     * Enforces no nulls
     */
    public UniqueTaskList(Set<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.addAll(tasks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all tasks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Task> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tasks in this list with those in the argument order list.
     */
    public void setTasks(Set<Task> tasks) {
        requireAllNonNull(tasks);
        internalList.setAll(tasks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every task in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTaskList from) {
        final Set<Task> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(task -> !alreadyInside.contains(task))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        for (Task task : internalList) {
            if (task.equals(toCheck)) {

            }
        }
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Task to the list.
     *
     * @throws DuplicateTaskException if the Task to add is a duplicate of an existing Task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes {@code Task toRemove} from the list if it exists.
     */
    public void remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new TaskNotFoundException();
        }
        internalList.remove(toRemove);
    }

    @Override
    public Iterator<Task> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        sortList();
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Sorts the list of tasks according to alphabetical order of description.
     */
    private void sortList() {
        internalList.sort(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getDate().getDate().compareTo(task2.getDate().getDate()) == 0) {
                    return task1.getTime().getTime().compareTo(task2.getTime().getTime());
                }
                return task1.getDate().getDate().compareTo(task2.getDate().getDate());
            }
        });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTaskList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
