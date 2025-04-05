package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Labels and un-labels an existing person in the address book as favourite.
 */
public class FavCommand extends Command {
    public static final String COMMAND_WORD = "fav";
    public static final String MESSAGE_DUPLICATE_NAME = "A contact with this name already exists in AcademySource.";
    public static final String MESSAGE_DUPLICATE_TELEGRAM = "A contact with this telegram handle "
            + "already exists in AcademySource.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set/unset the favourite status "
            + "of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "If the contact is not labelled as favourite, labels it as favourite. "
            + "Otherwise, un-label it.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Added %1$s as favourites!";
    public static final String MESSAGE_UNMARK_PERSON_SUCCESS = "Removed %1$s from favourites!";
    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public FavCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        String message = MESSAGE_MARK_PERSON_SUCCESS;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person toggledPerson = personToEdit.toggleFav();

        if (!personToEdit.isSameName(toggledPerson) && model.hasName(toggledPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_NAME);
        }

        if (!personToEdit.isSameTelegram(toggledPerson) && model.hasTelegram(toggledPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_TELEGRAM);
        }

        model.setPerson(personToEdit, toggledPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (!toggledPerson.getIsFavourite()) {
            message = MESSAGE_UNMARK_PERSON_SUCCESS;
        }
        return new CommandResult(String.format(message, toggledPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FavCommand)) {
            return false;
        }

        FavCommand otherFavCommand = (FavCommand) other;
        return index.equals(otherFavCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }

}
