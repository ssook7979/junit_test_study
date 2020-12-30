package com.hspark.iloveyouboss.domain;

import java.time.Instant;

public interface Persistable {
	
	Integer getId();
	
	Instant getCreateTimestamp();
	
	void setCreateTimestamp(Instant instant);
	
}
