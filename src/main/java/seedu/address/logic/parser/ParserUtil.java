package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MODULE_CODE;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleRegistry;
import seedu.address.model.person.ModuleRegistry.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Telegram;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code onBasedIndex} into a list of {@code Index} and returns it.Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if any of the specified index given in the string is invalid
     *     (not non-zero unsigned integer).
     */
    public static List<Index> parseMassIndex(String oneBasedIndexes) throws ParseException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] splittedIndexes = trimmedIndexes.split(" ");
        Set<String> addedIndex = new HashSet<>();
        List<Index> indexes = new LinkedList<>();

        for (String index : splittedIndexes) {
            if (addedIndex.contains(index)) {
                throw new ParseException(DeleteCommand.MESSAGE_DUPLICATE_INDEX);
            }
            addedIndex.add(index);
            indexes.add(parseIndex(index));
        }
        return indexes;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim().replaceAll(" +", " ");
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String role} into a {@code role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedTag = role.trim();
        if (!Role.isValidRole(trimmedTag)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return Role.getRole(trimmedTag);
    }

    /**
    * Parses a {@code String moduleCode} into a {@code Module}.
    * Leading and trailing whitespaces will be trimmed.
    *
    * @param moduleCode The module code to be parsed.
    * @return The corresponding {@code Module} from {@code ModuleRegistry}.
    * @throws ParseException If the given {@code moduleCode} does not match any registered module.
    */
    public static Module parseModule(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModule = moduleCode.trim();
        return Optional.ofNullable(ModuleRegistry.getModuleByCode(trimmedModule))
                .orElseThrow(() -> new ParseException(MESSAGE_INVALID_MODULE_CODE));
    }

    /**
     * Parses a {@code Collection<String> moduleCodes} into a {@code Set<Module>}.
     * Each module code in the collection is validated and converted into a {@code Module} object.
     *
     * @param moduleCodes A collection of module codes to be parsed.
     * @return A {@code Set<Module>} containing the parsed modules.
     * @throws ParseException If any of the module codes are invalid.
     */
    public static Set<Module> parseModules(Collection<String> moduleCodes) throws ParseException {
        requireNonNull(moduleCodes);
        if (moduleCodes.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_MODULE_CODE);
        }
        final Set<Module> moduleSet = new HashSet<>();
        for (String moduleCode : moduleCodes) {
            moduleSet.add(parseModule(moduleCode));
        }
        return moduleSet;
    }

    /**
     * Parse a {@code Optional<String>} into an {@code Optional<Telegram>} object.
     * If the {@code ArgumentMultimap} does not contain the prefix for telegram, return an empty Optional object.
     * Otherwise, return an Optional object containing the Telegram object.
     * @param telegramString Optional object from {@code ArgumentMultimap}
     * @return a {@code Optional} object containing {@code Telegram} if it is present.
     * @throws ParseException
     */
    public static Optional<Telegram> parseTelegram(Optional<String> telegramString) throws ParseException {
        requireNonNull(telegramString);
        if (telegramString.isEmpty()) {
            return Optional.empty();
        }
        String telegramHandle = telegramString.get();
        if (!Telegram.isValidHandle(telegramHandle)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Telegram(telegramHandle));
    }

    /**
     * Validates the {@code keywords} are valid keywords for Name.
     */
    public static void validateNameKeywords(List<String> keywords) throws ParseException {
        for (String keyword : keywords) {
            if (!Name.isValidName(keyword)) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
        }
    }

    /**
     * Validates the {@code keywords} are valid keywords for Phone.
     */
    public static void validatePhoneKeywords(List<String> keywords) throws ParseException {
        for (String keyword : keywords) {
            if (!keyword.matches("\\d+")) {
                throw new ParseException("Phone number must only contain digits.");
            }
        }
    }

    /**
     * Validates the {@code keywords} are valid keywords for Modules.
     */
    public static void validateModuleKeywords(List<String> keywords) throws ParseException {
        for (String keyword : keywords) {
            if (keyword.trim().isEmpty()) {
                throw new ParseException("Module keyword cannot be empty.");
            }
            if (!keyword.matches("^[A-Za-z0-9]+$")) {
                throw new ParseException("Module keywords must contain only alphanumeric characters.");
            }
        }
    }

    /**
     * Validates the {@code keywords} are valid keywords for Favourite.
     */
    public static void validateFavouriteKeywords(List<String> keywords) throws ParseException {
        if (keywords.size() != 1) {
            throw new ParseException("f/ field must contain exactly one keyword: 'y' or 'n' (case-insensitive).");
        }
        String lower = keywords.get(0).toLowerCase();
        if (!lower.equals("y") && !lower.equals("n")) {
            throw new ParseException("f/ field only accepts 'y' or 'n' (case-insensitive).");
        }
    }

    /**
     * Validates the {@code keywords} is a valid keyword for Role.
     */
    public static void validateRoleKeywords(List<String> keywords) throws ParseException {
        if (keywords.size() != 1) {
            throw new ParseException("r/ field must contain exactly one keyword: 'prof' "
                    + "or 'TA' (case-insensitive).");
        }
        String lower = keywords.get(0).toLowerCase();
        if (!Role.isValidRole(lower)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Validates the {@code keywords} are valid keywords for Telegram.
     */
    public static void validateTelegramKeywords(List<String> keywords) throws ParseException {
        for (String keyword : keywords) {
            if (keyword.trim().isEmpty()) {
                throw new ParseException("Telegram keyword cannot be empty.");
            }
            if (!keyword.matches("^[A-Za-z0-9_@]+$")) {
                throw new ParseException("Telegram handle should only contain alphabets, digits, underscores or '@'.");
            }
        }
    }

    /**
     * Validates the {@code keywords} are valid keywords for Email.
     */
    public static void validateEmailKeywords(List<String> keywords) throws ParseException {
        for (String keyword : keywords) {
            if (keyword.trim().isEmpty()) {
                throw new ParseException("Email keyword cannot be empty.");
            }
        }
    }
}
