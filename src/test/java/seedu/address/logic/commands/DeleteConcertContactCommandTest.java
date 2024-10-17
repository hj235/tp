package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
//=======================================================WORK IN PROGRESS==========================================//
/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePersonFromConcertCommand}.
 */
public class DeleteConcertContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeletePersonFromConcertCommand deletePersonFromConcertCommand = new DeletePersonFromConcertCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeletePersonFromConcertCommand.MESSAGE_DELETE_PERSON_FROM_CONCERT_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deletePersonFromConcertCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePersonFromConcertCommand deletePersonFromConcertCommand = new DeletePersonFromConcertCommand(outOfBoundIndex);

        assertCommandFailure(deletePersonFromConcertCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_OBJECT);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeletePersonFromConcertCommand deletePersonFromConcertCommand = new DeletePersonFromConcertCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeletePersonFromConcertCommand.MESSAGE_DELETE_PERSON_FROM_CONCERT_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deletePersonFromConcertCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_OBJECT);

        Index outOfBoundIndex = INDEX_SECOND_OBJECT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePersonFromConcertCommand deletePersonFromConcertCommand = new DeletePersonFromConcertCommand(outOfBoundIndex);

        assertCommandFailure(deletePersonFromConcertCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePersonFromConcertCommand deleteFirstCommand = new DeletePersonFromConcertCommand(INDEX_FIRST_OBJECT);
        DeletePersonFromConcertCommand deleteSecondCommand = new DeletePersonFromConcertCommand(INDEX_SECOND_OBJECT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePersonFromConcertCommand deleteFirstCommandCopy = new DeletePersonFromConcertCommand(INDEX_FIRST_OBJECT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeletePersonFromConcertCommand deletePersonFromConcertCommand = new DeletePersonFromConcertCommand(targetIndex);
        String expected = DeletePersonFromConcertCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deletePersonFromConcertCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
