package au.edu.unsw.cse.data.api.domain.watcher;

import java.util.Date;

import org.mongodb.morphia.annotations.PrePersist;

import au.edu.unsw.cse.data.api.domain.entity.Entity;

public class EntityWatcher {
	@PrePersist
	void prePersist(Entity entity) {
		Date now = new Date();
		if (entity.getCreatedAt() == null) {
			entity.setCreatedAt(now);
		}
		entity.setUpdatedAt(now);
	}
}
