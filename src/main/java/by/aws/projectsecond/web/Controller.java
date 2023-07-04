package by.aws.projectsecond.web;

import com.amazonaws.util.EC2MetadataUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Polina Bril
 */
@RestController
public class Controller {

	@GetMapping(value = "/data")
	public String getData() {
		String region = EC2MetadataUtils.getEC2InstanceRegion();
		String availabilityZone = EC2MetadataUtils.getAvailabilityZone();
		return String.format("Region is %s. AvailabilityZone is %s.", region, availabilityZone);
	}
}
