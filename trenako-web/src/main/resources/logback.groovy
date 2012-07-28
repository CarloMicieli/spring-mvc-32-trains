import ch.qos.logback.classic.encoder.PatternLayoutEncoder 
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

appender("CONSOLE", ConsoleAppender) {
	target = System.out
	encoder(PatternLayoutEncoder) {
		pattern = "%level %logger - %msg%n"
	}
}

logger("com.trenako", INFO, ["CONSOLE"])
logger("org.mongodb", INFO, ["CONSOLE"])
logger("org.springframework", INFO, ["CONSOLE"])
