package blokplugins.kitroom.extra;

import blokplugins.kitroom.database.PointsDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class DBstartup {

    private final Logger logger;
    private final File dataFolder;
    private final PointsDatabase pointsDatabase;

    public DBstartup(Logger logger, File dataFolder, PointsDatabase pointsDatabase) {
        this.logger = logger;
        this.dataFolder = dataFolder;
        this.pointsDatabase = pointsDatabase;
    }

    public void defaultDB(List<String> fileNames) throws SQLException {
        for (String fileName : fileNames) {
            if (!pointsDatabase.kitExists("kitroom", fileName)) {
                pointsDatabase.uploadKit("kitroom", fileName, null);
            } else {
                continue;
            }

        }
    }
}
