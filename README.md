# parser
A Java + MySQL log file parser and analyzer 

what it specifically does is: import a log file from a specified directory then it finds all the IPs that exceed the specified threshold in the interval between the `--startDate` initial datetime and the end date that is calculated under the hood based on the `--duration` provided (can only be hourly or daily) .

You can test run this application by running the following command:<br>
`java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --path=/path/to/log`

# Documentation
documentation is now being prepared, Stay tuned!
