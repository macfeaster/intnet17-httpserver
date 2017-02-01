package se.mauritzz.kth.inet.application;

import se.mauritzz.kth.inet.http.body.HtmlResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class TemplateEngine {

	private Map<String, String> data;

	private TemplateEngine(Map<String, String> data) {
		this.data = data;
	}

	static HtmlResponseBody render(String viewPath, Map<String, String> data) throws IOException {
		return new HtmlResponseBody(new TemplateEngine(data).render(viewPath));
	}

	private String render(String viewPath) throws IOException {
		return Files.readAllLines(Paths.get("res/" + viewPath + ".html"))
				.stream()
				.map(l -> l.contains("{{") ? processLine(l) : l)
				.collect(Collectors.joining("\n"));
	}

	private String processLine(String line) {
		// Find variables to be replaced
		Pattern pattern = Pattern.compile("\\{\\{[ ]*(\\w+)[ ]*}}");
		Matcher matcher = pattern.matcher(line);

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
