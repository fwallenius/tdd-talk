package se.wallenius.fileops;

import se.wallenius.exceptions.BadStateException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultFileReader implements MyFileReader {

    private BufferedReader reader = null;

    @Override
    public boolean open(String fileName) {
        Path path = Paths.get(fileName);

        try {
            this.reader = Files.newBufferedReader(path, Charset.defaultCharset());
            return true;
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public String nextLine() {
        if (this.reader != null) {
            try {
                return this.reader.readLine();
            } catch (IOException e) {
                throw new BadStateException("Could not read from file.", e);
            }
        } else {
            throw new BadStateException("Dude, I have no file to read from!");
        }
    }

    @Override
    public void close() {
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
