package at.simstoe.cloud.launcher;

import at.simstoe.cloud.api.logger.LogType;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * @author Simon Stögerer
 * copyright - all rights reserved
 * created: 23.12.2024 - 13:22
 */

@RequiredArgsConstructor
public final class WorkerThread extends Thread {
    private final CloudLauncher cloudLauncher;
    private final StringBuffer stringBuffer = new StringBuffer();
    private final byte[] bytes = new byte[2048];

    @Override
    public void run() {
        while (this.cloudLauncher.isRunning()) {
            this.readStream(System.in, s -> {
                if (this.cloudLauncher.commandManager().cloudCommands().containsKey(s)) {
                    this.cloudLauncher.commandManager().execute(s);
                } else {
                    this.cloudLauncher.logger().log("Unknown command: " + s, LogType.ERROR);
                }

                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private void readStream(final InputStream inputStream, final Consumer<String> consumer) {
        var length = 0;
        try {
            while (inputStream.available() > 0
                    && (length = inputStream.read(this.bytes, 0, this.bytes.length)) != -1) {
                this.stringBuffer.append(new String(this.bytes, 0, length, StandardCharsets.UTF_8));
            }

            final var string = this.stringBuffer.toString();
            if (string.contains("\n")) {
                for (final var s : string.split("\n")) consumer.accept(s);
            }
            this.stringBuffer.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
