package com.managementprojects.projections;

import java.time.Instant;

public interface TaskDetailsProjection {
	
	String getName();
	String getDescription();
	Instant getStartDate();
	Instant getFinishDate();
	Long getProject();

}
