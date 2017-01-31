package se.mauritzz.kth.inet.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateEngine {

	private static final String OPENING_DELIMETER = "\\{\\{";
	private static final String CLOSING_DELIMETER = "}}";

	private Map<String, String> data;

	public TemplateEngine(Map<String, String> data) {
		this.data = data;
	}

	public String render(String viewPath) throws IOException {
		return Files.readAllLines(Paths.get(viewPath))
				.stream()
				.map(l -> l.contains(OPENING_DELIMETER) ? processLine(l) : l)
				.collect(Collectors.joining("\n"));
	}

	private String processLine(String line) {
		// Find variables to be replaced
		Pattern pattern = Pattern.compile(OPENING_DELIMETER + "[ ]*(\\w+)[ ]*" + CLOSING_DELIMETER);
		Matcher matcher = pattern.matcher(line);

		// Malformed data is just printed out as is
		if (!matcher.matches())
			return line;

		// Loop through every variable
		while (matcher.find()) {
			String replace = line.substring(matcher.start(), matcher.end());
			String variable = matcher.group(1);

			// Replace template literal with variable data, or simply remove it for non-existent data
			if (data.get(variable) != null)
				line = line.replace(replace, data.get(variable));
			else
				line = line.replace(replace, "");
		}

		return line;
	}

}
