package by.aws.projectsecond.web;

import by.aws.projectsecond.model.MetadataModel;
import by.aws.projectsecond.service.S3ObjectService;
import java.io.File;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Polina Bril
 */
@RestController
public class S3ObjectController {

	@Autowired
	private S3ObjectService service;

	private static final String BUCKET_NAME = "bril-polina/pics";

	@PostMapping(value = "/upload/{filename}")
	public int uploadFile(@PathVariable String filename) {
		service.uploadObject(BUCKET_NAME, filename, new File("C://Users/228Lenovo/OneDrive/" + filename));
		return HttpStatus.SC_OK;
	}

	@GetMapping(value = "/download/{filename}")
	public int downloadFile(@PathVariable String filename) throws IOException {
		service.downloadFile(BUCKET_NAME, filename);
		return HttpStatus.SC_OK;
	}

	@DeleteMapping(value = "/delete/{filename}")
	public int deleteFile(@PathVariable String filename) {
		service.deleteObject(BUCKET_NAME, filename);
		return HttpStatus.SC_OK;
	}

	@GetMapping(value = "/metadata/{filename}")
	public MetadataModel getMetadata(@PathVariable String filename) {
		return service.getMetadata(BUCKET_NAME, filename).toModel();
	}

	@GetMapping(value = "/metadata/any")
	public MetadataModel randomFileMetadata() {
		return service.randomFileMetadata().toModel();
	}
}
