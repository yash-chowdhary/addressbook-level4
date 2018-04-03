package seedu.club.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.commons.util.StringUtil;

import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Name;
import seedu.club.model.member.Password;
import seedu.club.model.member.Phone;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.Username;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Question;
import seedu.club.model.tag.Tag;
import seedu.club.model.task.Date;
import seedu.club.model.task.Description;
import seedu.club.model.task.Time;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";
    public static final String MESSAGE_INVALID_PATH = "Path should be a valid absolute path to a file.";

    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String matricNumber} into an {@code MatricNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code matricNumber} is invalid.
     */
    public static MatricNumber parseMatricNumber(String matricNumber) throws IllegalValueException {
        requireNonNull(matricNumber);
        String trimmedMatricNumber = matricNumber.trim();
        if (!MatricNumber.isValidMatricNumber(trimmedMatricNumber)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        return new MatricNumber(trimmedMatricNumber);
    }

    /**
     * Parses a {@code Optional<String> matricNumber} into an {@code Optional<MatricNumber>}
     * if {@code matricNumber} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MatricNumber> parseMatricNumber(Optional<String> matricNumber) throws IllegalValueException {
        requireNonNull(matricNumber);
        return matricNumber.isPresent() ? Optional.of(parseMatricNumber(matricNumber.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String username} into an {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Username parseUsername(String username) {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return  username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into an {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Password parsePassword(String password) {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<Passowrd>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Password> parsePassword(Optional<String> password) {
        return password.isPresent() ? Optional.of(parsePassword(password.get())) : Optional.empty();
    }

    //@@author amrut-prabhu
    /**
     * Parses a {@code String photo} into an {@code ProfilePhoto}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code photo} is invalid.
     */
    public static ProfilePhoto parseProfilePhoto(String photo) throws IllegalValueException {
        requireNonNull(photo);
        String trimmedProfilePhoto = photo.trim();
        if (!ProfilePhoto.isValidProfilePhoto(trimmedProfilePhoto)) {
            throw new IllegalValueException(ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS);
        }
        return new ProfilePhoto(trimmedProfilePhoto);
    }

    /**
     * Parses a {@code Optional<String> photo} into an {@code Optional<ProfilePhoto>} if {@code photo} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfilePhoto> parseProfilePhoto(Optional<String> photo) throws IllegalValueException {
        requireNonNull(photo);
        return photo.isPresent() ? Optional.of(parseProfilePhoto(photo.get())) : Optional.empty();
    }

    /**
     * Parses a {@code path} into a {@code File}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code path} is invalid.
     */
    public static File parsePath(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();

        if (trimmedPath.isEmpty()) {
            throw new IllegalValueException(MESSAGE_INVALID_PATH);
        }

        return new File(trimmedPath);
    }

    /**
     * Parses a {@code path} into a {@code File}.
     *
     * @throws IllegalValueException if the given {@code path} is not absolute or is a directory.
     */
    public static File parseExportPath(String path) throws IllegalValueException, IOException {
        File file = parsePath(path);

        if (!file.isAbsolute() || file.isDirectory() || !validFileName(path)) {
            throw new IllegalValueException(MESSAGE_INVALID_PATH);
        }

        file.createNewFile();
        return file;
    }

    /**
     * Returns true if {@code path} represents the path of a CSV (.csv) file.
     */
    private static boolean validFileName(String path) {
        String csvFileExtension = ".csv";

        int length = path.length();
        String fileExtension = path.substring(length - 4);
        return fileExtension.compareToIgnoreCase(csvFileExtension) == 0;
    }

    /**
     * Parses a {@code path} into a {@code File}.
     *
     * @throws IllegalValueException if the given {@code path} is is not an absolute file path or does not exist.
     */
    public static File parseImportPath(String path) throws IllegalValueException {
        File file = parsePath(path);

        if (!file.isAbsolute() || file.isDirectory() || !file.exists() || !file.canRead()) {
            throw new IllegalValueException(MESSAGE_INVALID_PATH);
        }

        return file;
    }
    //@@author

    //@@author yash-chowdhary
    /**
     * Parses a {@code String group} into a {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(Group.MESSAGE_GROUP_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }

    /**
     * Parses a {@code Optional<String> group} into a {@code Optional<Group>} if {@code group} is present.
     */
    public static Optional<Group> parseGroup(Optional<String> group) throws  IllegalValueException {
        requireNonNull(group);
        return group.isPresent() ? Optional.of(parseGroup(group.get())) : Optional.empty();
    }
    //@@author
    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code Optional<String> tag} into a {@code Optional<Tag>} if {@code group} is present
     */
    public static Optional<Tag> parseOptionalTag(Optional<String> tag) throws IllegalValueException {
        requireNonNull(tag);
        return tag.isPresent() ? Optional.of(parseTag(tag.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String question} into a {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Question parseQuestion(String question) throws IllegalValueException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new IllegalValueException(Question.MESSAGE_QUESTION_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code Optional<String> question} into an {@code Optional<Question>} if {@code question} is present.
     */
    public static Optional<Question> parseQuestion(Optional<String> question) throws IllegalValueException {
        requireNonNull(question);
        return question.isPresent() ? Optional.of(parseQuestion(question.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String answer} into a {@code answer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code answer} is invalid.
     */
    public static Answer parseAnswer(String answer) throws IllegalValueException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (!Answer.isValidAnswer(trimmedAnswer)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        return new Answer(trimmedAnswer);
    }

    /**
     * Parses a {@code Optional<String> answer} into a {@code Optional<Answer>} if {@code group} is present
     */
    public static Optional<Answer> parseOptionalAnswer(Optional<String> answer) throws IllegalValueException {
        requireNonNull(answer);
        return answer.isPresent() ? Optional.of(parseAnswer(answer.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code List<Answer>}.
     * Removes any duplicates if any
     */
    public static List<Answer> parseAnswers(Collection<String> answers) throws IllegalValueException {
        requireNonNull(answers);
        final Set<String> answerStringSet = new HashSet<>();
        final List<Answer> answerList = new ArrayList<>();
        for (String answer : answers) {
            if (!answerStringSet.contains(answer)) {
                Answer parsedAnswer = parseAnswer(answer);
                answerStringSet.add(answer);
                answerList.add(parsedAnswer);
            }
        }
        return answerList;
    }

    //@@author yash-chowdhary
    /**
     * Parses a {@code String client} into a {@code Client}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code client} is invalid.
     */
    public static Client parseClient(String client) throws IllegalValueException {
        requireNonNull(client);
        String trimmedClient = client.trim();
        if (!Client.isValidClient(trimmedClient)) {
            throw new IllegalValueException(Client.MESSAGE_CLIENT_CONSTRAINTS);
        }
        return new Client(trimmedClient);
    }

    /**
     * Parses a {@code Optional<String> client} into a {@code Optional<Client>} if {@code client} is present.
     */
    public static Optional<Client> parseClient(Optional<String> client) throws IllegalValueException {
        requireNonNull(client);
        return client.isPresent() ? Optional.of(parseClient(client.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        return new Subject(trimmedSubject);
    }

    /**
     * Parses a {@code Optional<String> subject} into a {@code Optional<Subject>} if {@code subject} is present.
     */
    public static Optional<Subject> parseSubject(Optional<String> subject) {
        requireNonNull(subject);
        return subject.isPresent() ? Optional.of(parseSubject(subject.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String body} into a {@code Body}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code body} is invalid.
     */
    public static Body parseBody(String body) {
        requireNonNull(body);
        String trimmedBody = body.trim();
        return new Body(trimmedBody);
    }

    /**
     * Parses a {@code Optional<String> body} into a {@code Optional<Body>} if {@code body} is present.
     */
    public static Optional<Body> parseBody(Optional<String> body) {
        requireNonNull(body);
        return body.isPresent() ? Optional.of(parseBody(body.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws IllegalValueException {
        logger.info(description);
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code Optional<String> description} into a {@code Optional<Description>} if {@code description}
     * is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(parseDescription(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code Optional<String> date} into a {@code Optional<Date>} if {@code date} is present.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String time} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code Optional<String> time} into a {@code Optional<Time>} if {@code time} is present.
     */
    public static Optional<Time> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(parseTime(time.get())) : Optional.empty();
    }

    //@@author
}
