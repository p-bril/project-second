package by.aws.projectsecond.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Polina Bril
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataModel {
	private String name;
	private Long size;
	private Date lastModified;
	private String extension;
}
