package seedu.duke.command;

//@@author paullowse
import seedu.duke.Storage;
import seedu.duke.Ui;
import seedu.duke.data.TransactionList;
import seedu.duke.data.transaction.Transaction;
import seedu.duke.exception.StatsInvalidTypeException;
import seedu.duke.exception.MoolahException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.command.CommandTag.COMMAND_TAG_STATS_TYPE;
import static seedu.duke.command.CommandTag.COMMAND_TAG_GLOBAL_MONTH;
import static seedu.duke.command.CommandTag.COMMAND_TAG_GLOBAL_NUMBER;
import static seedu.duke.command.CommandTag.COMMAND_TAG_GLOBAL_PERIOD;
import static seedu.duke.command.CommandTag.COMMAND_TAG_GLOBAL_YEAR;
import static seedu.duke.common.InfoMessages.INFO_STATS_CATEGORIES;
import static seedu.duke.common.InfoMessages.INFO_STATS_EMPTY;
import static seedu.duke.common.InfoMessages.INFO_STATS_EXPENSES;
import static seedu.duke.common.InfoMessages.INFO_STATS_INCOME;
import static seedu.duke.common.InfoMessages.INFO_STATS_SAVINGS;
import static seedu.duke.common.InfoMessages.INFO_STATS_SUMMARY_HEADER;
import static seedu.duke.common.InfoMessages.INFO_STATS_TIME;

/**
 * Represents a get command object that will execute the operations for Get command.
 */
public class StatsCommand extends Command {
    //@@author paullowse
    private static final String LINE_SEPARATOR = System.lineSeparator();
    // The command word used to trigger the execution of Moolah Manager's operations
    public static final String COMMAND_WORD = "STATS";
    // The description for the usage of command
    public static final String COMMAND_DESCRIPTION = "To get statistics of the transactions such "
            + "as the total savings for each category, summary of expenditure, etc.";
    // The guiding information for the usage of command
    public static final String COMMAND_USAGE = "Usage: stats s/STATISTICS_TYPE";
    // The formatting information for the parameters used by the command
    public static final String COMMAND_PARAMETERS_INFO = "Parameters information:"
            + LINE_SEPARATOR
            + "STATISTICS_TYPE: The type of statistics to be generated. Only \"categories\" is accepted.";

    // Basic help description
    public static final String COMMAND_HELP = "Command Word: " + COMMAND_WORD + LINE_SEPARATOR
            + COMMAND_DESCRIPTION + LINE_SEPARATOR + COMMAND_USAGE + LINE_SEPARATOR;
    // Detailed help description
    public static final String COMMAND_DETAILED_HELP = COMMAND_HELP + COMMAND_PARAMETERS_INFO
            + LINE_SEPARATOR;

    //@@author chydarren
    private static final int UNDEFINED_PARAMETER = -1;
    private static final int TRUE_AND = 1;
    private static final int TRUE_OR = 2;
    private static final int TRUE_INVALID_OR = 3;
    private static final int FALSE = 0;
    private static final String CATEGORIES = "categories";
    private static final String TIME = "time";
    private static final String WEEKS = "weeks";
    private static final String MONTHS = "months";
    private static Logger statsLogger = Logger.getLogger(StatsCommand.class.getName());
    private String statsType;
    private int month;
    private int year;
    private String period;
    private int number;

    //@@author paullowse

    public StatsCommand() {
        statsType = "";
        period = null;
        month = UNDEFINED_PARAMETER;
        year = UNDEFINED_PARAMETER;
        number = UNDEFINED_PARAMETER;
    }

    /**
     * Gets the mandatory tags of the command.
     *
     * @return A string array containing all mandatory tags.
     */
    @Override
    public String[] getMandatoryTags() {
        String[] mandatoryTags = new String[]{COMMAND_TAG_STATS_TYPE};
        return mandatoryTags;
    }

    @Override
    public String[] getOptionalTags() {
        String[] optionalTags = new String[]{
            COMMAND_TAG_GLOBAL_MONTH,
            COMMAND_TAG_GLOBAL_YEAR,
            COMMAND_TAG_GLOBAL_NUMBER,
            COMMAND_TAG_GLOBAL_PERIOD,
        };
        return optionalTags;
    }

    @Override
    public void setStatsType(String statsType) {
        this.statsType = statsType;
    }

    @Override
    public void setGlobalMonth(int month) {
        this.month = month;
    }

    @Override
    public void setGlobalYear(int year) {
        this.year = year;
    }

    @Override
    public void setGlobalNumber(int number) {
        this.number = number;
    }

    @Override
    public void setGlobalPeriod(String period) {
        this.period = period;
    }

    //@@author chydarren

    /**
     * Checks if the input contains month or/and year tags.
     *
     * @return 1 if both tags are given, 2 if only month is given, 0 if both are not given.
     */
    public int containMonthYear() {
        if (month != UNDEFINED_PARAMETER && year != UNDEFINED_PARAMETER) {
            return TRUE_AND;
        } else if (year != UNDEFINED_PARAMETER) {
            return TRUE_OR;
        } else if (month != UNDEFINED_PARAMETER) {
            return TRUE_INVALID_OR;
        }
        return FALSE;
    }

    /**
     * Checks if the input contains period and/or number tags.
     *
     * @return 1 if both tags are given, 2 if either period/number is given, 0 if both are not given.
     */
    public int containPeriodNumber() {
        if (period != null && number != UNDEFINED_PARAMETER) {
            return TRUE_AND;
        } else if (period != null || number != UNDEFINED_PARAMETER) {
            return TRUE_OR;
        }
        return FALSE;
    }

    /**
     * Executes the operations related to the command.
     *
     * @param ui           An instance of the Ui class.
     * @param transactions An instance of the TransactionList class.
     * @param storage      An instance of the Storage class.
     */
    @Override
    public void execute(TransactionList transactions, Ui ui, Storage storage) throws MoolahException {
        statsLogger.setLevel(Level.SEVERE);
        statsLogger.log(Level.INFO, "Entering execution of the Stats command.");

        listStatsByStatsType(statsType, transactions, month, year, period, number);
    }

    /**
     * Lists the statistics depending on the type of statistics requested.
     *
     * @param statsType     The type of statistics that is needed.
     * @param transactions  An instance of the TransactionList class.
     * @throws MoolahException If the type of statistics is not recognised.
     */
    private void listStatsByStatsType(String statsType, TransactionList transactions, int month,
                                             int year, String period, int number)
            throws MoolahException {
        switch (statsType) {
        case CATEGORIES:
            statsLogger.log(Level.INFO, "Stats type has been detected for categorical savings.");
            statsTypeCategoricalSavings(transactions);
            statsLogger.log(Level.INFO, "End of Stats command.");
            break;
        //@@author paullowse
        case TIME:
            statsLogger.log(Level.INFO, "Stats type has been detected for monthly savings.");

            // Stats command uses last N months or years
            if (containPeriodNumber() == TRUE_AND && containMonthYear() == FALSE) {
                statsLogger.log(Level.INFO, "Stats command uses last N months or years.");
                statsTypeTimeSavings(transactions, year, month, period, number);
            // Stats command uses either monthly or yearly
            } else if (containPeriodNumber() == FALSE && (containMonthYear() == TRUE_OR
                    || containMonthYear() == TRUE_AND)) {
                statsLogger.log(Level.INFO, "Stats command uses either monthly or yearly.");
                statsTypeTimeSavings(transactions, year, month, period, number);
            // Throws a missing tag if s/time was called without any relevant tags
            } else {
                statsLogger.log(Level.WARNING, "An exception has been caught due to a missing tag");
                throw new StatsInvalidTypeException();
            }
            break;
        //@@author chydarren
        default:
            statsLogger.log(Level.WARNING, "An exception has been caught due to an invalid stats type.");
            throw new StatsInvalidTypeException();
        }
    }

    //@@author chydarren

    /**
     * Display the statistics requested for current amount of savings in each category.
     *
     * @param transactions An instance of the TransactionList class.
     */
    public void statsTypeCategoricalSavings(TransactionList transactions) {
        String categoricalSavingsList = transactions.listCategoricalSavings();

        if (categoricalSavingsList.isEmpty()) {
            statsLogger.log(Level.INFO, "Categorical savings list is empty as there are no transactions available.");
            Ui.showInfoMessage(INFO_STATS_EMPTY.toString());
            return;
        }

        assert !categoricalSavingsList.isEmpty();
        statsLogger.log(Level.INFO, "Categorical savings list is found to contain categories-amount pairs.");
        Ui.showList(categoricalSavingsList, INFO_STATS_CATEGORIES.toString());
    }

    //@@author paullowse

    /**
     * Calls transactions to get the necessary transaction list, convert the parameters into a String for output.
     * Produces info strings, list of categories and summary statistics.
     *
     * @param transactions  An instance of the TransactionList class.
     * @param year          A specified year.
     * @param month         A specified month.
     * @param period        A specified period of time.
     * @param number        A specified number of periods.
     * @throws MoolahException If the type of statistics is not recognised.
     */
    public void statsTypeTimeSavings(TransactionList transactions, int year, int month,
                                            String period, int number) throws MoolahException {

        ArrayList<Transaction> timeTransactions;
        // only year
        if (containPeriodNumber() == TRUE_AND && period == WEEKS) {
            timeTransactions = transactions.getTransactionsByWeekRange(LocalDate.now(), number);
        } else if (containPeriodNumber() == TRUE_AND && period == WEEKS) {
            timeTransactions = transactions.getTransactionsByMonthRange(LocalDate.now(), number);
        } else if (containMonthYear() == TRUE_AND) {
            timeTransactions = transactions.getTransactionsByMonth(year, month);
        } else if (containMonthYear() == TRUE_OR) {
            timeTransactions = transactions.getTransactionsByYear(year);
        } else {
            statsLogger.log(Level.WARNING, "An exception has been caught due to a missing tag");
            throw new StatsInvalidTypeException();
        }
        String timeSavingsList = transactions.listTimeStats(timeTransactions, year, month, period, number);

        if (timeSavingsList.isEmpty()) {
            statsLogger.log(Level.INFO, "Categorical savings list is empty as there are no transactions available.");
            Ui.showInfoMessage(INFO_STATS_EMPTY.toString());
            return;
        }

        // summary values
        ArrayList<String> amounts;
        amounts = transactions.processTimeSummaryStats(timeTransactions);

        String incomeMessage = INFO_STATS_SUMMARY_HEADER + LINE_SEPARATOR
                + INFO_STATS_INCOME + amounts.get(0);
        String expensesMessage = INFO_STATS_EXPENSES + amounts.get(1);
        String savingsMessage = INFO_STATS_SAVINGS + amounts.get(2);

        assert !timeSavingsList.isEmpty();
        statsLogger.log(Level.INFO, "Monthly savings list is found to contain categories-amount pairs.");
        Ui.showStatsList(timeSavingsList, INFO_STATS_TIME.toString(), incomeMessage, expensesMessage, savingsMessage);
    }

    //@@author paullowse

    /**
     * Enables the program to exit when the Bye command is issued.
     *
     * @return A boolean value that indicates whether the program shall exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
