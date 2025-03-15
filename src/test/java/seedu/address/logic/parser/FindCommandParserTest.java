package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.NAME, Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "/name Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n /name Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.PHONE, Arrays.asList("91234567")));
        assertParseSuccess(parser, "/phone 91234567", expectedFindCommand);
    }

}
