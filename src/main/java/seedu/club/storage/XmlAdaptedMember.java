package seedu.club.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.club.commons.exceptions.IllegalValueException;

import seedu.club.model.group.Group;
import seedu.club.model.member.Credentials;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.Username;
import seedu.club.model.tag.Tag;

/**
 * JAXB-friendly version of the member.
 */
public class XmlAdaptedMember {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "member's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String matricNumber;
    @XmlElement
    private String group;
    @XmlElement
    private String profilePhoto;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMember.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMember() {}

    /**
     * Constructs an {@code XmlAdaptedMember} with the given member details.
     */

    public XmlAdaptedMember(String name, String phone, String email, String matricNumber, String group,
                            List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matricNumber = matricNumber;
        this.group = group;
        this.username = this.matricNumber;
        this.password = "password";
        this.profilePhoto = "";
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given member into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMember
     */
    public XmlAdaptedMember(Member source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        matricNumber = source.getMatricNumber().value;
        group = source.getGroup().groupName;
        username = source.getCredentials().getUsername().value;
        password = source.getCredentials().getPassword().value;
        profilePhoto = source.getProfilePhoto().getProfilePhotoPath();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted member object into the model's member object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public Member toModelType() throws IllegalValueException {
        final List<Tag> memberTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            memberTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.matricNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MatricNumber.class.getSimpleName()));
        }
        if (!MatricNumber.isValidMatricNumber(this.matricNumber)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        final MatricNumber matricNumber = new MatricNumber(this.matricNumber);

        if (!Group.isValidGroup(this.group)) {
            throw new IllegalValueException(Group.MESSAGE_GROUP_CONSTRAINTS);
        }
        final Group group = new Group(this.group);

        final Set<Tag> tags = new HashSet<>(memberTags);

        final ProfilePhoto profilePhoto = new ProfilePhoto(this.profilePhoto);

        Member member = new Member(name, phone, email, matricNumber, group, tags,
                new Credentials(new Username(matricNumber.value)), profilePhoto);

        return member;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMember)) {
            return false;
        }

        XmlAdaptedMember otherMember = (XmlAdaptedMember) other;
        return Objects.equals(name, otherMember.name)
                && Objects.equals(phone, otherMember.phone)
                && Objects.equals(email, otherMember.email)
                && Objects.equals(matricNumber, otherMember.matricNumber)
                && Objects.equals(group, otherMember.group)
                && Objects.equals(profilePhoto, otherMember.profilePhoto)
                && tagged.equals(otherMember.tagged);
    }
}
