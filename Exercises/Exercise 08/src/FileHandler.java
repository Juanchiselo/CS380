import java.io.*;
import java.util.ArrayList;

public class FileHandler
{
    private static FileHandler instance = null;
    private String line;
    private String path;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public static FileHandler getInstance()
    {
        if(instance == null)
            instance = new FileHandler();
        return instance;
    }

    private FileHandler()
    {
    }

    private void openFile()
    {
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
        } catch(FileNotFoundException e) {
            System.err.println("ERROR: Unable to open file '" + path + "'.");
        }
    }

    private void closeFile()
    {
        try {
            bufferedReader.close();
            fileReader.close();
        } catch(IOException e) {
            System.err.println("ERROR: Unable to close file '" + path + "'.");
        }
    }

    /**
     * Reads the specified file.
     * @param path - The path to the file to read the data from.
     */
    public ArrayList<String> readFile(String path)
    {
        this.path = path;
        ArrayList<String> file = new ArrayList<>();

        try {
            openFile();
            while((line = bufferedReader.readLine()) != null)
                    file.add(line);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to read from file '" + this.path + "'.");
        } finally {
            closeFile();
        }
        return file;
    }
}
