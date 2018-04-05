package seedu.club.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_MEMBER_FORMAT = "Invalid member format! \n%1$s";
    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX = "The member index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d members listed!";
    public static final String MESSAGE_INVALID_POLL_DISPLAYED_INDEX = "The poll index provided is invalid";
    public static final String MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX = "The answer index provided is invalid";
    public static final String MESSAGE_INVALID_PHOTO_PATH = "Unable to read profile photo from the path: %1$s";
    public static final String MESSAGE_SAME_PHOTO_PATH = "Profile photo path entered is same as the current photo";
    public static final String MESSAGE_INVALID_PERMISSIONS = "This command cannot be executed as you don't have EXCO "
            + "privileges";
    public static final String MESSAGE_REQUIRE_LOG_IN = "Please log in to Club Connect.";
    public static final String MESSAGE_REQUIRE_SIGN_UP = "Please sign up to use Club Connect";
    public static final String MESSAGE_REQUIRE_EXCO_LOG_IN = "Only excos are authorised to execute current command";
}
