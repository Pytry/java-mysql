package org.xitikit.examples.java.mysql.logwatch.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.xitikit.examples.java.mysql.logwatch.data.*;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

import static org.springframework.util.NumberUtils.*;
import static org.springframework.util.StringUtils.*;

/**
 * Contains all functionality required for user interaction
 * through the terminal.
 * <p>
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
@Service("logWatchCli")
public class LogWatchCli implements CommandLineRunner{

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    private static final Logger log = LoggerFactory.getLogger(LogWatchCli.class);

    private final LogEntrySearchService logEntrySearchService;

    private final AccessLogService accessLogService;

    private final BlockedIpv4Repository blockedIpv4Repository;

    private final Collector<String, TerminalCommands, TerminalCommands> argumentsCollector;

    @Autowired
    public LogWatchCli(
        final LogEntrySearchService logEntrySearchService,
        final AccessLogService accessLogService,
        final BlockedIpv4Repository blockedIpv4Repository){

        this.logEntrySearchService = logEntrySearchService;
        this.accessLogService = accessLogService;
        this.blockedIpv4Repository = blockedIpv4Repository;
        this.argumentsCollector = buildArgumentsCollector();
    }

    @Override
    public void run(final String... args){

        log.info("Starting LogWatchCliApplication::run(" + Arrays.toString(args) + ")");

        executeAndPrint(toTerminalCommands(args));
    }

    private Collector<String, TerminalCommands, TerminalCommands> buildArgumentsCollector(){

        return Collector.of(
            TerminalCommands::new,
            (builder, arg) -> {
                if(arg.startsWith("--accessLog=")){
                    builder.setAccessLog(arg.substring(12));
                }
                else if(arg.startsWith("--startDate=")){
                    builder.setStartDate(toStartDate(arg.substring(12)));
                }
                else if(arg.startsWith("--duration=")){
                    builder.setDuration(toDuration(arg.substring(11)));
                }
                else if(arg.startsWith("--threshold=")){
                    builder.setThreshold(toThreshold(arg.substring(12)));
                }
                else if(arg.startsWith("--ipv4=")){
                    builder.setIpv4(arg.substring(7));
                }
            },
            (current, addition) -> {
                if(addition.getStartDate() != null){
                    current.setStartDate(addition.getStartDate());
                }
                if(addition.getDuration() != null){
                    current.setDuration(addition.getDuration());
                }
                if(addition.getThreshold() != null && addition.getThreshold() > 0){
                    current.setThreshold(addition.getThreshold());
                }
                if(addition.getAccessLog() != null){
                    current.setAccessLog(addition.getAccessLog());
                }
                if(addition.getIpv4() != null){
                    current.setIpv4(addition.getIpv4());
                }
                return current;
            },
            Collector.Characteristics.CONCURRENT,
            Collector.Characteristics.UNORDERED
        );
    }

    /**
     * Converts the given string into a LocalDateTime.
     */
    private LocalDateTime toStartDate(final String arg){

        return arg == null ? null : LocalDateTime.from(formatter.parse(arg));
    }

    /**
     * null-safe conversion to a {@link WatchDuration}
     */
    private WatchDuration toDuration(final String arg){

        return arg == null ? null : WatchDuration.valueOf(arg);
    }

    /**
     * Null safe integer conversion.
     */
    private Integer toThreshold(final String arg){

        return hasText(arg)
            ? parseNumber(arg, Integer.class)
            : null;
    }

    private static LocalDateTime toEndDate(final LocalDateTime localDateTime, final WatchDuration watchDuration){

        final LocalDateTime answer;
        if(localDateTime == null || watchDuration == null){
            answer = null;
        }
        else if(watchDuration == WatchDuration.hourly){
            answer = localDateTime.plusHours(1);
        }
        else{
            answer = localDateTime.plusDays(1);
        }
        return answer;
    }

    private void println(Object in){

        System.out.println(in);
    }

    private void executeAndPrint(final TerminalCommands commands){

        if(commands != null){

            loadAccessLog(commands.getAccessLog());
            saveAndPrintQueryResults(toIpv4SearchQuery(commands));
            findAndPrintAllByIpv4(commands.getIpv4());
        }
        else{
            println("***************************************");
            println("No valid arguments were found. Exiting.");
            println("***************************************");
        }
    }

    /**
     * Searches for all database entries matching the given ipv4
     * and prints them out to the console.
     *
     * @param ipv4 The IPv4 to search for.
     */
    private void findAndPrintAllByIpv4(final String ipv4){

        if(ipv4 != null && !"".equals(ipv4.trim())){
            List<LogEntry> results = logEntrySearchService.findAllByIpv4OrderByDateAsc(ipv4);
            if(results == null || results.size() <= 0){
                println("NO RESULTS");
            }
            else{
                results.forEach(this::println);
            }
        }
    }

    private Ipv4SearchQuery toIpv4SearchQuery(final TerminalCommands commands){

        Ipv4SearchQuery query;
        if(commands.getStartDate() == null ||
            commands.getDuration() == null ||
            commands.getThreshold() == null ||
            commands.getThreshold() <= 0){
            query = null;
        }
        else{
            query = new Ipv4SearchQuery(
                commands.getStartDate(),
                toEndDate(commands.getStartDate(), commands.getDuration()),
                commands.getThreshold()
            );
        }
        return query;
    }

    private void loadAccessLog(final String accessLogPath){

        if(accessLogPath != null && !"".equals(accessLogPath)){
            accessLogService.parseAndSaveAccessLogEntries(accessLogPath);
        }
    }

    private String blockedMessage(Ipv4SearchQuery query, LogEntrySearchResult result){
        // A more robust solution would store these values as columns in the blocked_ipv4 table.
        return "BLOCKED_IPV4:" + result.getIpv4() +
            "|REASON:EXCEEDED_THRESHOLD" +
            "|MAX_ALLOWED:" + query.getThreshold() +
            "|ACTUAL:" + result.getTotal() +
            "|START_DATE:" + query.getStartDate() +
            "|END_DATE:" + query.getEndDate();
    }

    private void saveAndPrintQueryResults(final Ipv4SearchQuery query){

        if(query == null){
            return;
        }
        logEntrySearchService
            .findAddressesThatExceedThreshold(query)
            .forEach(
                result -> println(
                    blockedIpv4Repository.save(
                        new BlockedIpv4(
                            result.getIpv4(),
                            blockedMessage(query, result)
                        ))
                        .getReason())
            );
    }

    private TerminalCommands toTerminalCommands(final String... args){

        return Arrays.stream(args)
            .map(arg -> arg == null ? "" : arg.trim())
            .filter(arg -> arg.length() > 0)
            .collect(argumentsCollector);
    }
}