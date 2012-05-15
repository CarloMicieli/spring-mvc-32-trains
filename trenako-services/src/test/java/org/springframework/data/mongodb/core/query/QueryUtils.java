package org.springframework.data.mongodb.core.query;

import java.util.List;

public class QueryUtils {
	public static List<Criteria> getCriteria(final Query query) {
		return query.getCriteria();
	}
}
