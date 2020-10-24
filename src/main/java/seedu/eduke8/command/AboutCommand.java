package seedu.eduke8.command;

import seedu.eduke8.Stats;
import seedu.eduke8.common.DisplayableList;
import seedu.eduke8.ui.Ui;

public class AboutCommand extends Command {

    @Override
    public void execute(DisplayableList displayableList, Ui ui) {
        ui.printAbout();

        new Stats().showStatsToUser(ui);
    }
}
