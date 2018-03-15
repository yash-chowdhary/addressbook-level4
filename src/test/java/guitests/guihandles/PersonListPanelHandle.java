package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.club.model.member.Member;
import seedu.club.ui.MemberCard;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code MemberCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<MemberCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private Optional<MemberCard> lastRememberedSelectedPersonCard;

    public PersonListPanelHandle(ListView<MemberCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<MemberCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("member list size expected 1.");
        }

        return new PersonCardHandle(personList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<MemberCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the member.
     */
    public void navigateToCard(Member member) {
        List<MemberCard> cards = getRootNode().getItems();
        Optional<MemberCard> matchingCard = cards.stream().filter(card -> card.member.equals(member)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("member does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the member card handle of a member associated with the {@code index} in the list.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(getRootNode().getItems().get(index).member);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code member} in the list.
     */
    public PersonCardHandle getPersonCardHandle(Member member) {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.member.equals(member))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("member does not exist."));
    }

    /**
     * Selects the {@code MemberCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code MemberCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<MemberCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MemberCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<MemberCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
