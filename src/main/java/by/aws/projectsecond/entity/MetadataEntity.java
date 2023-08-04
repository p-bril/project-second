package by.aws.projectsecond.entity;

import by.aws.projectsecond.model.MetadataModel;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * @author Polina Bril
 */
@Entity
@Data
@Table(name = "metadata")
public class MetadataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Long size;

	@Temporal(TemporalType.DATE)
	private Date lastModified;

	private String extension;

	public MetadataEntity() {
	}

	public MetadataEntity(String name, long size, Date lastModified, String extension) {
		this.name = name;
		this.size = size;
		this.lastModified = lastModified;
		this.extension = extension;
	}

	public MetadataModel toModel() {
		return new MetadataModel(this.name, this.size, this.lastModified, this.extension);
	}
}
