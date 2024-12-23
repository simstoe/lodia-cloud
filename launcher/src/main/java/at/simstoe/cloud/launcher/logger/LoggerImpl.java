package at.simstoe.cloud.launcher.logger;

import at.simstoe.cloud.api.logger.LogType;
import at.simstoe.cloud.api.logger.Logger;
import at.simstoe.cloud.api.logger.console.AnsiColor;

/**
 * @author Simon Stögerer
 * copyright - all rights reserved
 * created: 23.12.2024 - 13:16
 */

public final class LoggerImpl implements Logger {
    @Override
    public void log(String text) {
        System.out.println(text);
    }

    @Override
    public void log(String text, LogType logType) {
        var logTemplate = "§8[§r" + mapLogTypeToColor(logType).ansiCode() + logType + "§8] §r" + text;

        System.out.println(AnsiColor.replaceColorCodes(logTemplate));
    }

    private AnsiColor mapLogTypeToColor(LogType logType) {
        return switch (logType) {
            case INFO -> AnsiColor.CYAN;
            case SUCCESS -> AnsiColor.GREEN;
            case WARNING -> AnsiColor.YELLOW;
            case ERROR -> AnsiColor.RED;
            default -> AnsiColor.RESET;
        };
    }
}
