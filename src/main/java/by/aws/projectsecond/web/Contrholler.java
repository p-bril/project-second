package by.aws.projectsecond.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Polina Bril
 */
@RestController
public class Contrholler {

	@GetMapping(value = "/data")
	public String getData() throws IOException {
		String commandToken = "curl -X PUT \"http://169.254.169.254/latest/api/token\" -H \"X-aws-ec2-metadata-token-ttl-seconds: 21600\"";
		String token = proceedCommand(commandToken);
		String commandAvailabitilyZone = String.format(
				"curl -H \"X-aws-ec2-metadata-token: %s\" -v -s http://169.254.169.254/latest/meta-data/placement/availability-zone",
				token);
		String availabilityZone = proceedCommand(commandAvailabitilyZone);
		String commandRegion = String.format(
				"curl -H \"X-aws-ec2-metadata-token: %s\" -v -s http://169.254.169.254/latest/meta-data/placement/region",
				token);
		String region = proceedCommand(commandRegion);
		return String.format("Region is %s. \nAvailabilityZone is %s.", region, availabilityZone);
	}

	private String proceedCommand(String command) throws IOException {
		Process process = Runtime.getRuntime().exec(command);
		InputStream stream = process.getInputStream();
		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
		}
		return textBuilder.toString();
	}
}
