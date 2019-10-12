package fudan.se.lab4.util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import fudan.se.lab4.constant.FileConstant;
import fudan.se.lab4.constant.InfoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return true;
        } else {
            if (destFileName.endsWith(File.separator)) {
                throw new RuntimeException(MessageFormat.format(InfoConstant.getInfo("FILE_CANNOT_BE_DIR"), destFileName));
            }
            // file exists?
            if (!file.getParentFile().exists()) {
                //if the parent dir is not exist, then create it.
                logger.info(InfoConstant.getInfo("CREATING_PARENT_DIR"));
                if (!file.getParentFile().mkdirs()) {
                    throw new RuntimeException(InfoConstant.getInfo("FAILED_CREAT_DIR"));
                }
            }
            // create target file.
            try {
                if (file.createNewFile()) {
                    logger.info(MessageFormat.format(InfoConstant.getInfo("SUCCESS_TO_CREATE_FILE"), destFileName));
                    return true;
                } else {
                    throw new RuntimeException(MessageFormat.format(InfoConstant.getInfo("FAILED_TO_CREATE_FILE"), destFileName));
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
                throw new RuntimeException(MessageFormat.format(InfoConstant.getInfo("FAILED_TO_CREATE_FILE_REASON"), destFileName,
                        e.getMessage()));
            }
        }
    }

    public static void write(String[] array, String dataFilePath) {
        if (createFile(dataFilePath)) {
            BufferedWriter bw;
            try {
                bw = new BufferedWriter(new FileWriter(dataFilePath, true));
                CsvWriter out = new CsvWriter(bw, FileConstant.CSV_SEPARATOR);
                out.writeRecord(array);
                out.flush();
                bw.flush();
                out.close();
                bw.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * delete the user with such name from the file
     * if the name exists, then return true
     * else return false
     *
     * @param name         user name
     * @param dataFilePath file which stores the data of users
     */
    public static boolean delete(String name, String dataFilePath) {
        CsvReader reader;
        ArrayList<String[]> contents = new ArrayList<>();
        boolean hasName = false;
        // first read the contents from file
        // delete the row with such name
        try {
            reader = new CsvReader(dataFilePath, FileConstant.CSV_SEPARATOR,
                    Charset.forName(FileConstant.CHARSET));
            while (reader.readRecord()) {
                String[] item = reader.getValues();
                if (!item[0].equals(name)) {
                    contents.add(item);
                } else {
                    hasName = true;
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        // if no user with such a name, return false
        if (!hasName) {
            return false;
        }
        //else, write all contents into the file
        else if (createFile(dataFilePath)) {
            BufferedWriter bw;
            try {
                bw = new BufferedWriter(new FileWriter(dataFilePath));
                CsvWriter out = new CsvWriter(bw, FileConstant.CSV_SEPARATOR);
                for (String[] content : contents) {
                    out.writeRecord(content);
                }
                out.flush();
                bw.flush();
                out.close();
                bw.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }

        }
        return true;
    }

    public static String[] readByField(String Name, String dataFilePath, int field) {
        CsvReader reader;
        try {
            reader = new CsvReader(dataFilePath, FileConstant.CSV_SEPARATOR,
                    Charset.forName(FileConstant.CHARSET));
            while (reader.readRecord()) {
                String[] item = reader.getValues();
                if (item[field].equals(Name)) {
                    return item;
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        throw new RuntimeException(MessageFormat.format(InfoConstant.getInfo("ENTITY_NOT_FOUND"), Name));
    }

    public static List<String> readByColumn(int num, String nameOfSubject, String dataFilePath) {
        CsvReader reader;
        List<String> item = new ArrayList<>();
        try {
            reader = new CsvReader(dataFilePath, FileConstant.CSV_SEPARATOR,
                    Charset.forName(FileConstant.CHARSET));
            while (reader.readRecord()) {
                String[] information = reader.getValues();
                if (num >= information.length) {
                    throw new RuntimeException(MessageFormat.format(InfoConstant.getInfo("SUBJECT_NOT_FOUND"), nameOfSubject));
                }
                item.add(information[num]);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return item;

    }

    public static boolean exist(String name, String dataFilePath) {
        createFile(dataFilePath);
        CsvReader reader;
        try {
            reader = new CsvReader(dataFilePath, FileConstant.CSV_SEPARATOR,
                    Charset.forName(FileConstant.CHARSET));
            while (reader.readRecord()) {
                String[] item = reader.getValues();
                if (item[0].equals(name)) {
                    return true;
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return false;
    }

}
