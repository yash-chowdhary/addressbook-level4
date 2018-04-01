package seedu.club;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.club.commons.core.Config;
import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.core.Version;
import seedu.club.commons.events.ui.ExitAppRequestEvent;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.util.ConfigUtil;
import seedu.club.commons.util.StringUtil;
import seedu.club.logic.Logic;
import seedu.club.logic.LogicManager;
import seedu.club.model.ClubBook;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.UserPrefs;
//import seedu.club.model.util.SampleDataUtil;
import seedu.club.storage.ClubBookStorage;
import seedu.club.storage.CsvClubBookStorage;
import seedu.club.storage.JsonUserPrefsStorage;
import seedu.club.storage.ProfilePhotoStorage;
import seedu.club.storage.Storage;
import seedu.club.storage.StorageManager;
import seedu.club.storage.UserPrefsStorage;
import seedu.club.storage.XmlClubBookStorage;
import seedu.club.ui.Ui;
import seedu.club.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ClubBook ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        ClubBookStorage clubBookStorage = new XmlClubBookStorage(userPrefs.getClubBookFilePath());
        ProfilePhotoStorage profilePhotoStorage = new ProfilePhotoStorage();
        CsvClubBookStorage csvClubBookStorage = new CsvClubBookStorage();
        storage = new StorageManager(clubBookStorage, userPrefsStorage, profilePhotoStorage, csvClubBookStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s club book and {@code userPrefs}. <br>
     * The data from the sample club book will be used instead if {@code storage}'s club book is not found,
     * or an empty club book will be used instead if errors occur when reading {@code storage}'s club book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyClubBook> clubBookOptional;
        ReadOnlyClubBook initialData;
        try {
            clubBookOptional = storage.readClubBook();
            if (!clubBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ClubBook");
            }
            initialData = clubBookOptional.orElse(new ClubBook());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ClubBook");
            initialData = new ClubBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ClubBook");
            initialData = new ClubBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        String prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ClubBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ClubBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Club Connect ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
