package org.xitikit.examples.java.mysql.logwatch.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryRepository;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.util.StringUtils.*;

@Service
@Transactional
public class AccessLogServiceImpl implements AccessLogService{

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public AccessLogServiceImpl(final LogEntryRepository logEntryRepository){

        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void parseAndSaveAccessLogEntries(final String accessLogPath){
        // I considered using a future here, but this method
        // really needs to block further execution in the
        // current thread because other processes depend on this.
        // If there is a need for non-blocking later, then it can be
        // accomplished with a wrapper.
        if(!hasText(accessLogPath)){
            throw new AccessLogException("'Path accessLogPath' is required.");
        }
        try(BufferedReader reader = accessLogReader(accessLogPath)){
            // Because the file will most likely be very large, it is more
            // beneficial use a parallel stream. The order of insertion does
            // not matter, since results can/should be ordered by the date column.
            reader
                .lines()
                .parallel()
                .map(AccessLogHelper::toLogEntry)
                .forEach(logEntryRepository::save);
        }
        catch(IOException e){
            e.printStackTrace();
            throw new AccessLogException("Error reading the access log at " + accessLogPath, e);
        }
    }

    /**
     * Determines whether to get the log from the filesystem or the classpath.
     *
     * @param path The path to process
     *
     * @return BufferedReader
     */
    private BufferedReader accessLogReader(String path) throws IOException{

        assert path != null;

        BufferedReader bufferedReader;
        if(path.startsWith("classpath:")){

            bufferedReader = new BufferedReader(
                loadFromClasspath(
                    path.substring(10).trim()
                ));
        }
        else{
            bufferedReader = Files.newBufferedReader(Paths.get(path));
        }
        return bufferedReader;
    }

    private InputStreamReader loadFromClasspath(String classPathResource){

        assert classPathResource != null;

        return new InputStreamReader(
            this.getClass()
                .getClassLoader()
                .getResourceAsStream(classPathResource));
    }
}
