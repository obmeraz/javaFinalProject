package by.zarembo.project.command;

import java.util.Optional;

public class CommandFactory {
    public static Optional<Command> defineCommand(String commandName) {
        Optional<Command> current = Optional.empty();
        if (commandName == null) {
            return current;
        }
        CommandType type = CommandType.valueOf(commandName.toUpperCase());
        current = Optional.of(type.getCommand());
        return current;
    }
}
