package seedu.club.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_MEMBER_FORMAT = "Invalid member format! \n%1$s";
    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX = "The member index provided is invalid.";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid.";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d members listed.";
    public static final String MESSAGE_NON_EXISTENT_GROUP = "The group \'%1$s\' does not exist in the Club Book.";
    public static final String MESSAGE_MANDATORY_GROUP = "The group \'%1$s\' cannot be deleted as "
            + "it is a mandatory group.";
    public static final String MESSAGE_INVALID_POLL_DISPLAYED_INDEX = "The poll index provided is invalid.";
    public static final String MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX = "The answer index provided is invalid.";
    public static final String MESSAGE_REQUIRE_LOG_IN = "Please log in to Club Connect to use this feature.";
    public static final String MESSAGE_REQUIRE_SIGN_UP = "Please sign up to use Club Connect.";
    public static final String MESSAGE_REQUIRE_EXCO_LOG_IN = "You need to be an Exco member to use this feature.";
    public static final String MESSAGE_REQUIRE_LOG_OUT = "Please logout before logging in to another account.";
    public static final String MESSAGE_UNABLE_TO_DELETE_CURRENT_USER = "Unable to delete yourself.";
    public static final String MESSAGE_DATE_ALREADY_PASSED = "The date and time you have entered has already passed! "
            + "Please enter a future date and time.";
}
