package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Phone} matches any of the keywords given.
 * The search is case-insensitive and allows partial matches for phone numbers.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final SearchField field;

    /**
     * Represents the search field type for finding persons.
     */
    public enum SearchField {
        /** Search by name field */
        NAME,
        /** Search by phone number field */
        PHONE
    }

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate} to find persons by the specified field and keywords.
     *
     * @param field    The field to search (either {@code NAME} or {@code PHONE}).
     * @param keywords The list of keywords to match the corresponding specified field.
     */
    public PersonContainsKeywordsPredicate(SearchField field, List<String> keywords) {
        this.field = field;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        switch (field) {
        case NAME:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        case PHONE:
            return keywords.stream()
                    .anyMatch(keyword -> person.getPhone().value.contains(keyword));
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonContainsKeywordsPredicate otherPredicate)) {
            return false;
        }
        return keywords.equals(otherPredicate.keywords) && field == otherPredicate.field;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
