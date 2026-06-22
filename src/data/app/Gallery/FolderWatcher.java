package data.app.Gallery;

import java.io.File;
import java.nio.file.*;

public class FolderWatcher extends Thread {
    private final String folderPath;
    private final Runnable callback;

    public FolderWatcher(String folderPath, Runnable callback) {
        this.folderPath = folderPath;
        this.callback = callback;
        this.setDaemon(true); // Shuts down thread automatically when the app closes
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(folderPath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    callback.run(); // Calls syncFolder() in main app
                }
                if (!key.reset()) break;
            }
        } catch (Exception e) {
            System.out.println("Watcher Error: " + e.getMessage());
        }
    }
}