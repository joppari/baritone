/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.utils.command.defaults;

import baritone.api.Settings;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalInverted;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.utils.command.Command;
import baritone.api.utils.command.exception.CommandInvalidStateException;
import baritone.api.utils.command.helpers.arguments.ArgConsumer;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

public class InvertCommand extends Command {
    public InvertCommand() {
        super("invert");
    }

    @Override
    protected void executed(String label, ArgConsumer args, Settings settings) {
        args.requireMax(0);

        ICustomGoalProcess customGoalProcess = baritone.getCustomGoalProcess();
        Goal goal;

        if (isNull(goal = customGoalProcess.getGoal())) {
            throw new CommandInvalidStateException("No goal");
        }

        if (goal instanceof GoalInverted) {
            goal = ((GoalInverted) goal).origin;
        } else {
            goal = new GoalInverted(goal);
        }

        customGoalProcess.setGoal(goal);
        logDirect(String.format("Goal: %s", goal.toString()));
    }

    @Override
    protected Stream<String> tabCompleted(String label, ArgConsumer args, Settings settings) {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Run away from the current goal";
    }

    @Override
    public List<String> getLongDesc() {
        return asList(
                "The invert command tells Baritone to head away from the current goal rather than towards it.",
                "",
                "Usage:",
                "> invert - Invert the current goal."
        );
    }
}
