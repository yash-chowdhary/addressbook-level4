package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.Member.Member;
import seedu.club.model.group.Group;
import seedu.club.model.Member.Email;
import seedu.club.model.Member.MatricNumber;
import seedu.club.model.Member.Name;
import seedu.club.model.Member.Password;
import seedu.club.model.Member.Phone;
import seedu.club.model.Member.Username;
import seedu.club.model.Member.exceptions.DuplicatePersonException;
import seedu.club.model.Member.exceptions.PersonNotFoundException;
import seedu.club.model.tag.Tag;

/**
 * Edits the details of an existing Member in the club book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Member identified "
            + "by the index number used in the last Member listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_MATRIC_NUMBER + "MATRIC NUMBER] "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Member: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This Member already exists in the club book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Member memberToEdit;
    private Member editedMember;

    /**
     * @param index of the Member in the filtered Member list to edit
     * @param editPersonDescriptor details to edit the Member with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(memberToEdit, editedMember);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target Member cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedMember));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Member> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        memberToEdit = lastShownList.get(index.getZeroBased());
        editedMember = createEditedPerson(memberToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Member} with the details of {@code memberToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Member createEditedPerson(Member memberToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert memberToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(memberToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(memberToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(memberToEdit.getEmail());
        MatricNumber updatedMatricNumber = editPersonDescriptor.getMatricNumber()
                .orElse(memberToEdit.getMatricNumber());
        Group updatedGroup = editPersonDescriptor.getGroup().orElse(memberToEdit.getGroup());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(memberToEdit.getTags());
        Username updatedUsername = editPersonDescriptor.getUsername().orElse(memberToEdit.getUsername());
        Password updatedPassword = editPersonDescriptor.getPassword().orElse(memberToEdit.getPassword());

        return new Member(updatedName, updatedPhone, updatedEmail, updatedMatricNumber, updatedGroup,
                updatedTags, updatedUsername, updatedPassword);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(memberToEdit, e.memberToEdit);
    }

    /**
     * Stores the details to edit the Member with. Each non-empty field value will replace the
     * corresponding field value of the Member.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private MatricNumber matricNumber;
        private Group group;
        private Set<Tag> tags;
        private Username username;
        private Password password;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setMatricNumber(toCopy.matricNumber);
            setGroup(toCopy.group);
            setTags(toCopy.tags);
            setUsername(toCopy.username);
            setPassword(toCopy.password);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email,
                    this.matricNumber, this.group, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setMatricNumber(MatricNumber matricNumber) {
            this.matricNumber = matricNumber;
        }

        public Optional<MatricNumber> getMatricNumber() {
            return Optional.ofNullable(matricNumber);
        }

        public Optional<Group> getGroup() {
            return Optional.ofNullable(group);
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getMatricNumber().equals(e.getMatricNumber())
                    && getTags().equals(e.getTags())
                    && getUsername().equals(e.getUsername())
                    && getPassword().equals(e.getPassword());
        }
    }
}
