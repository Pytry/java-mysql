package org.xitikit.examples.java.mysql.logwatch.simple;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/**
 * Contains all functionality required for user interaction
 * through the terminal.
 * <p>
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
public class LogWatchCli{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    private final LogEntryRepository logEntryRepository;

    private final AccessLogLoader accessLogLoader;

    private final BlockedIpv4Repository blockedIpv4Repository;

    private final Collector<String, Ipv4SearchQueryBuilder, Ipv4SearchQuery> argumentsCollector;

    public LogWatchCli(
        final LogEntryRepository logEntryRepository,
        final AccessLogLoader accessLogLoader,
        final BlockedIpv4Repository blockedIpv4Repository){

        this.logEntryRepository = logEntryRepository;
        this.accessLogLoader = accessLogLoader;
        this.blockedIpv4Repository = blockedIpv4Repository;
        this.argumentsCollector = Collector.of(
            Ipv4SearchQueryBuilder::new,
            (builder, arg) -> {
                if(arg.startsWith("--accessLog=")){
                    builder.accessLog = Paths.get(arg.substring(12));
                }
                else if(arg.startsWith("--startDate=")){
                    builder.startDate = startDate(arg.substring(12));
                }
                else if(arg.startsWith("--duration=")){
                    builder.watchDuration = duration(arg.substring(11));
                }
                else if(arg.startsWith("--threshold=")){
                    builder.threshold = threshold(arg.substring(12));
                }
            },
            (currentIp, additionIp) -> {
                if(additionIp.startDate != null){
                    currentIp.startDate = additionIp.startDate;
                }
                if(additionIp.watchDuration != null){
                    currentIp.watchDuration = additionIp.watchDuration;
                }
                if(additionIp.threshold != null && additionIp.threshold > 0){
                    currentIp.threshold = additionIp.threshold;
                }
                return currentIp;
            },
            Ipv4SearchQueryBuilder::build,
            Collector.Characteristics.CONCURRENT,
            Collector.Characteristics.UNORDERED
        );
    }

    /**
     * Converts the given string into a LocalDateTime.
     */
    private LocalDateTime startDate(final String arg){

        return arg == null ? null : LocalDateTime.from(formatter.parse(arg));
    }

    /**
     * null-safe conversion to a {@link WatchDuration}
     */
    private WatchDuration duration(final String arg){

        return arg == null ? null : WatchDuration.valueOf(arg);
    }

    /**
     * Null safe integer conversion.
     */
    private Integer threshold(final String arg){

        return arg == null || "".equals(arg.trim())
            ? null
            : Integer.valueOf(arg);
    }

    private static LocalDateTime endDate(final LocalDateTime localDateTime, final WatchDuration watchDuration){

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

    public void run(final String... args){

        System.out.println("************************");
        System.out.println("Starting LogWatchCli::run(" + Arrays.toString(args) + ")");
        System.out.println("************************");

        loadAndPrint(argsToQuery(args));
    }

    private void loadAndPrint(final Ipv4SearchQuery query){

        assert query != null;
        loadAccessLog(query.getAccessLog());

        System.out.println("*************");
        search(query)
            .forEach(
                result -> System.out.println(
                    blockedIpv4Repository.save(
                        new BlockedIpv4(
                            result.getIpv4(),
                            blockedMessage(query, result)))
                        .getReason())
            );
        System.out.println("*************");
    }

    private void loadAccessLog(final Path accessLog){

        if(accessLog != null){
            accessLogLoader.parseAndSaveAccessLogEntries(accessLog);
        }
    }

    private String blockedMessage(Ipv4SearchQuery query, AccessLogSearchResult result){
        // A more robust solution would store these values as columns in the blocked_ipv4 table.
        return "BLOCKED_IPV4:" + result.getIpv4() +
            "|REASON:EXCEEDED_REQUEST_THRESHOLD" +
            "|MAX_ALLOWED_REQUESTS:" + query.getThreshold() +
            "|ACTUAL_REQUESTS:" + result.getTotal() +
            "|START_DATETIME_LOCAL:" + query.getStartDate() +
            "|END_DATETIME_LOCAL:" + query.getEndDate();
    }

    private List<AccessLogSearchResult> search(final Ipv4SearchQuery ipv4SearchQuery){

        return logEntryRepository
            .findAllByDateRangeAndThreshold(
                ipv4SearchQuery.getStartDate(),
                ipv4SearchQuery.getEndDate(),
                ipv4SearchQuery.getThreshold());
    }

    private Ipv4SearchQuery argsToQuery(final String... args){

        return Arrays.stream(args)
            .map(arg -> arg == null ? "" : arg.trim())
            .filter(arg -> arg.length() > 0)
            .collect(argumentsCollector);
    }

    /**
     * An intermediate object is needed when parsing the arguments since
     * the "endDate" can only be calculated once the "--startDate" and
     * "--duration" arguments have been processed.
     */
    private final static class Ipv4SearchQueryBuilder{

        private LocalDateTime startDate;

        private WatchDuration watchDuration;

        private Integer threshold;

        private Path accessLog;

        Ipv4SearchQuery build(){

            return new Ipv4SearchQuery(
                this.startDate,
                endDate(this.startDate, watchDuration),
                threshold,
                accessLog
            );
        }
    }
}