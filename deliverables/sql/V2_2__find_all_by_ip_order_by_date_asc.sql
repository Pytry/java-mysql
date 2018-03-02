SELECT date, ipv4, request, status, user_agent
FROM log_entry
WHERE ipv4 = ${log_entry_ipv4_param}
ORDER BY date