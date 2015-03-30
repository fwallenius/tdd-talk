package se.wallenius.fileops;

public interface MyFileReader {

    /**
     * Tries to open a file with given path + name. Returns true or false depending on result.
     *
     * @param fileName
     * @return result
     */
    boolean open(String fileName);

    /**
     *
     * @return The next line from the open file. Null if end of file is reached.
     */
    String nextLine();

    /**
     * Close the open file.
     */
    void close();
}
