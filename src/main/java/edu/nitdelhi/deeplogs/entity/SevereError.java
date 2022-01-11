package edu.nitdelhi.deeplogs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SevereError {
	@Id
	@Column(name="severe_error_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long severeErrorId;
	
}
