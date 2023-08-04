package by.aws.projectsecond.service;

import by.aws.projectsecond.entity.MetadataEntity;
import by.aws.projectsecond.repository.MetadataRepository;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Polina Bril
 */
@Service
public class S3ObjectService {

	private MetadataRepository repository;
	AmazonS3 s3client;

	@Autowired
	public S3ObjectService(MetadataRepository repository) {
		this.repository = repository;
		this.s3client = this.getClient();
	}

	public PutObjectResult uploadObject(String bucketName, String key, File file) {
		return s3client.putObject(bucketName, key, file);
	}

	public void downloadFile(String bucketName, String key) throws IOException {
		S3Object s3object = s3client.getObject(bucketName, key);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		FileUtils.copyInputStreamToFile(inputStream, new File("C://Users/228Lenovo/OneDrive/download/"+key));
	}

	@Transactional
	public void deleteObject(String bucketName, String objectKey) {
		s3client.deleteObject(bucketName, objectKey);
		repository.removeByName(objectKey);
	}

	public MetadataEntity getMetadata(String bucketName, String key) {
		Optional<MetadataEntity> existedEntity = repository.findByName(key);
		if (existedEntity.isPresent()) {
			return existedEntity.get();
		} else {
			ObjectMetadata metadata = s3client.getObjectMetadata(bucketName, key);
			String name = FilenameUtils.getName(key);
			Date date = metadata.getLastModified();
			Long size = metadata.getContentLength();
			String extension = FilenameUtils.getExtension(key);
			MetadataEntity entity = new MetadataEntity(name, size, date, extension);
			return repository.save(entity);
		}
	}

	public MetadataEntity randomFileMetadata() {
		List<MetadataEntity> allMetadata = repository.findAll();
		Random random = new Random();
		int randomValue = random.nextInt(allMetadata.size());
		return allMetadata.get(randomValue);
	}

	private AmazonS3 getClient() {
		return AmazonS3ClientBuilder.standard()
				.withRegion(Regions.EU_NORTH_1)
				.build();
	}
}
