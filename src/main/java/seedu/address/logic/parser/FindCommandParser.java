package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throwParseException();
        }

        String[] splitArgs = trimmedArgs.split("\\s+", 2);
        if (splitArgs.length != 2) {
            throwParseException();
        }

        String prefix = splitArgs[0].trim();
        String keyword = splitArgs[1].trim();

        PersonContainsKeywordsPredicate.SearchField searchField = null;
        switch (prefix) {
        case "/name":
            searchField = PersonContainsKeywordsPredicate.SearchField.NAME;
            break;
        case "/phone":
            searchField = PersonContainsKeywordsPredicate.SearchField.PHONE;
            break;
        default:
            throwParseException();
        }

        if (searchField == null) {
            throwParseException();
        }

        List<String> keywords = Arrays.asList(keyword.split("\\s+"));
        return new FindCommand(new PersonContainsKeywordsPredicate(searchField, keywords));
    }

    private void throwParseException() throws ParseException {
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
