package blokplugins.kitroom.extra;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class FilesStartup {

    private final Logger logger;
    private final File dataFolder;

    public FilesStartup(Logger logger, File dataFolder) {
        this.logger = logger;
        this.dataFolder = dataFolder;
    }

    public void createFiles(List<String> fileNames) {
        for (String fileName : fileNames) {
            File file = new File(dataFolder, fileName);
            try {
                if (!file.exists()) {
                    boolean isFileCreated = file.createNewFile();
                    if (isFileCreated) {
                        logger.info("File created successfully: " + fileName);
                    } else {
                        logger.warning("Failed to create the file: " + fileName);
                    }
                } else {
                    logger.info("File already exists: " + fileName);
                }
            } catch (IOException e) {
                logger.warning("An error occurred while creating the file: " + fileName);
                e.printStackTrace();
            }
        }
    }
}
