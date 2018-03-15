package seedu.club.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.club.model.group.Group;
import seedu.club.model.Member.Email;
import seedu.club.model.Member.MatricNumber;
import seedu.club.model.Member.Name;
import seedu.club.model.Member.Password;
import seedu.club.model.Member.Member;
import seedu.club.model.Member.Phone;
import seedu.club.model.Member.Username;
import seedu.club.model.tag.Tag;
import seedu.club.model.util.SampleDataUtil;

/**
 * A utility class to help with building Member objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_MATRIC_NUMBER = "A1234567H";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_GROUP = "exco";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_USERNAME = "alice@gmail.com";
    public static final String DEFAULT_PASSWORD = "Alice8535";

    private Name name;
    private Phone phone;
    private Email email;
    private MatricNumber matricNumber;
    private Group group;
    private Set<Tag> tags;
    private Username username;
    private Password password;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        matricNumber = new MatricNumber(DEFAULT_MATRIC_NUMBER);
        group = new Group(DEFAULT_GROUP);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code memberToCopy}.
     */
    public PersonBuilder(Member memberToCopy) {
        name = memberToCopy.getName();
        phone = memberToCopy.getPhone();
        email = memberToCopy.getEmail();
        matricNumber = memberToCopy.getMatricNumber();
        group = memberToCopy.getGroup();
        tags = new HashSet<>(memberToCopy.getTags());
        username = memberToCopy.getUsername();
        password = memberToCopy.getPassword();
    }

    /**
     * Sets the {@code Name} of the {@code Member} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Member} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code MatricNumber} of the {@code Member} that we are building.
     */
    public PersonBuilder withMatricNumber(String matricNumber) {
        this.matricNumber = new MatricNumber(matricNumber);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Member} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Member} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code Member} that we are building
     */
    public PersonBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }
    /**
     * Sets the {@code Group} of the {@code Member} that we are building.
     */
    public PersonBuilder withGroup(String group) {
        this.group = new Group(group);
        return this;
    }
    /**
     * Sets the {@code Group} of the {@code Member} that we are building to the default group - "member".
     */
    public PersonBuilder withGroup() {
        this.group = new Group(Group.DEFAULT_GROUP);
        return this;
    }
    /**
     * Sets the {@Password} of the {@code Member} that we are building
     * @return
     */
    public PersonBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }
    public Member build() {
        return new Member(name, phone, email, matricNumber, group, tags, username, password);
    }
}
