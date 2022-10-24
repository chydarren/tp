package seedu.duke.common;

/**
 * Provides enum variables for storing custom program information messages.
 */
public enum InfoMessages {
    LINE_SEPARATOR(System.lineSeparator()),
    SPACE(" "),
    FULL_STOP("."),
    FULL_STOP_SPACE(". "),
    COLON_SPACE(": "),
    DOLLAR_SIGN("$"),
    INFO_DIVIDER("____________________________________________________________"),
    INFO_ADD_EXPENSE("I have added the following Expense transaction:"),
    INFO_ADD_INCOME("I have added the following Income transaction:"),
    INFO_EDIT_EXPENSE("I have edited the following Expense transaction:"),
    INFO_EDIT_INCOME("I have edited the following Income transaction:"),
    INFO_DELETE("I have deleted the following transaction:"),
    INFO_EXIT("Goodbye and see you soon."),
    INFO_GREET("Hello! I'm Moo and I will help you to manage your finances."),
    INFO_HELP_GREET("Gotcha! Here are the commands that you may use:"),
    INFO_HELP_PROMPT("Enter <help> if you need the list of commands."),
    INFO_LIST("Here are your transaction records:"),
    INFO_LIST_EMPTY("There are no transaction records found."),
    INFO_LIST_FILTERED("Here are the transaction records that match your search expression:"),
    INFO_LIST_UNFILTERED("There are no transaction records that match your search expression."),
    INFO_STATS_EMPTY("There are no statistics available yet for the given statistics type."),
    INFO_STATS_CATEGORIES("Here are the total savings for each category:"),
    INFO_STATS_MONTHS("Here is the expenditure summary for each month:"),
    INFO_STATS_MONTHS_COMMENT_ONE("Excellent! You saved quite a lot this month."),
    INFO_STATS_MONTHS_COMMENT_TWO("Wow, keep up the good work. You saved at least 80% of your income."),
    INFO_STATS_MONTHS_COMMENT_THREE("Good effort, you saved at least half of your income."),
    INFO_STATS_MONTHS_COMMENT_FOUR("Hmm, it spent quite a sum. Try saving more in future."),
    INFO_STATS_MONTHS_COMMENT_FIVE("You spent too much. Try to manage your expense within your budget."),
    INFO_STATS_TIME("Here are the total savings and expenses for"),
    INFO_STATS_INCOME("Total Income = "),
    INFO_STATS_EXPENSES("Total Expenses = "),
    INFO_STATS_SAVINGS("Total Savings = "),
    INFO_STATS_CATEGORIES_HEADER("-----CATGORIES-----"),
    INFO_STATS_SUMMARY_HEADER("-----SUMMARY-----"),
    INFO_PURGE("All your transactions have been purged."),
    INFO_PURGE_ABORT("Purging has been aborted. All transactions records are retained."),
    INFO_PURGE_EMPTY("The command is aborted as the transactions list is empty."),
    INFO_PURGE_WARNING("Are you sure you want to proceed with this command? Please enter 'Y' to confirm."),
    INFO_BUDGET_SET_SUCCESSFUL("You have successfully updated the budget."),
    INFO_CURRENT_BUDGET("Monthly budget set as: $"),
    INFO_REMAINING_BUDGET("Budget remained for "),
    INFO_EXCEEDING_BUDGET("Budget exceeded for "),
    INFO_BUDGET_EXCEEDED_TIPS("Consider spending less!"),
    INFO_BUDGET_NOT_EXCEEDED_TIPS("Keep it up!");

    //@@author chydarren
    public final String message;

    /**
     * Instantiates a new information message when user initialises a new instance of this enum.
     *
     * @param message A string containing the message.
     */
    InfoMessages(String message) {
        this.message = message;
    }

    /**
     * Gets the information message as a string.
     *
     * @return A string containing the message.
     */
    public String toString() {
        return message;
    }
}