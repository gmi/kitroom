package blokplugins.kitroom.extra;

import blokplugins.kitroom.database.PointsDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class FilesStartup {

    private final Logger logger;
    private final File dataFolder;
    private final PointsDatabase pointsDatabase;

    public FilesStartup(Logger logger, File dataFolder, PointsDatabase pointsDatabase) {
        this.logger = logger;
        this.dataFolder = dataFolder;
        this.pointsDatabase = pointsDatabase;
    }

    public void createFiles(List<String> fileNames) throws SQLException {
        for (String fileName : fileNames) {
            File file = new File(dataFolder, fileName);
            pointsDatabase.uploadKit("kitroom", fileName, null);
        }
    }
}
