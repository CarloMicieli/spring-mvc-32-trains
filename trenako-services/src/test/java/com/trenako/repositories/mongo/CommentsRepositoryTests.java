/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.repositories.mongo;

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;
import com.trenako.repositories.CommentsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentsRepositoryTests extends AbstractMongoRepositoryTests {

	private RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.build();
	}
	
	public CommentsRepository repo;
	
	@Override
	void initRepository(MongoTemplate mongo) {
		repo = new CommentsRepositoryImpl(mongo);
		
		// mocking the current timestamp value
		((CommentsRepositoryImpl)repo).setCurrentTimestamp(
				new GregorianCalendar(2012, 6, 1, 10, 0).getTime());
	}
	
	@Test
	public void shouldFindCommentsByRollingStock() {
		
		when(mongo().findOne(isA(Query.class), eq(RollingStockComments.class))).thenReturn(rsComments());
		
		RollingStockComments results = repo.findByRollingStock(rollingStock());
		
		assertNotNull(results);
		assertEquals(rsComments(), results);
		assertEquals(3, results.getItems().size());
		
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo(), times(1)).findOne(arg.capture(), eq(RollingStockComments.class));
		assertEquals("{ \"slug\" : \"acme-123456\"}", arg.getValue().getQueryObject().toString());
	}
	
	@Test
	public void shouldPostNewComments() {
		repo.createNew(rollingStock(), newComment("Comment"));
		
		ArgumentCaptor<Query> argWhere = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).upsert(argWhere.capture(), argUpdate.capture(), eq(RollingStockComments.class));
		
		assertEquals("{ \"slug\" : \"acme-123456\"}", queryObject(argWhere).toString());
		
		String expected = "{ \"$set\" : { \"rollingStock\" : { \"slug\" : \"acme-123456\" , \"label\" : \"ACME 123456\"}} ,"+
			" \"$inc\" : { \"numberOfComments\" : 1} ,"+
			" \"$push\" : { \"items\" : { \"commentId\" : \"12-07-01-10-00-00_bob\" , \"author\" : \"bob\" ,"+
			" \"content\" : \"Comment\" , \"postedAt\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}

	@Test
	public void shouldPostCommentAnswers() {
		repo.createAnswer(rollingStock(), newComment("Comment"), newComment("Answer"));
		
		ArgumentCaptor<Query> argWhere = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argWhere.capture(), argUpdate.capture(), eq(RollingStockComments.class));
		
		assertEquals("{ \"slug\" : \"acme-123456\" , \"items.commentId\" : \"12-07-01-12-30-00_bob\"}", queryObject(argWhere).toString());
		
		String expected = "{ \"$inc\" : { \"numberOfComments\" : 1} ,"+
				" \"$push\" : { \"items.$.answers\" : "+
				"{ \"commentId\" : \"12-07-01-10-00-00_bob\" , \"author\" : \"bob\" , "+
				"\"content\" : \"Answer\" , \"postedAt\" : { \"$date\" : \"2012-07-01T08:00:00.000Z\"}}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldDeleteComments() {
		repo.remove(rollingStock(), newComment("Comment"));
		
		ArgumentCaptor<Query> argWhere = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argWhere.capture(), argUpdate.capture(), eq(RollingStockComments.class));
		
		assertEquals("{ \"slug\" : \"acme-123456\"}", queryObject(argWhere).toString());
		
		String expected = "{ \"$inc\" : { \"numberOfComments\" : -1} , \"$pull\" : { \"items\" : { \"commentId\" : \"12-07-01-12-30-00_bob\"}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}
	
	@Test
	public void shouldDeleteCommentAnswers() {
		repo.removeAnswer(rollingStock(), newComment("12-07-01-12-30-00_bob", "Comment"), newComment("12-07-01-10-00-00_bob", "Answer"));
		
		ArgumentCaptor<Query> argWhere = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> argUpdate = ArgumentCaptor.forClass(Update.class);
		verify(mongo(), times(1)).updateFirst(argWhere.capture(), argUpdate.capture(), eq(RollingStockComments.class));
		
		assertEquals("{ \"slug\" : \"acme-123456\" , \"items.commentId\" : \"12-07-01-12-30-00_bob\"}", queryObject(argWhere).toString());
		
		String expected = "{ \"$inc\" : { \"numberOfComments\" : -1} , \"$pull\" : { \"items.$.answers\" : { \"commentId\" : \"12-07-01-10-00-00_bob\"}}}";
		assertEquals(expected, updateObject(argUpdate).toString());
	}
	
	private RollingStockComments rsComments() {
		RollingStockComments rsc = new RollingStockComments(rollingStock());
		rsc.setItems(Arrays.asList(newComment("Comment"), newComment("Comment"), newComment("Comment")));
		return rsc;
	}
	
	private Comment newComment(String content) {
		return newComment("12-07-01-12-30-00_bob", content);
	}
	
	private Comment newComment(String commentId, String content) {
		Comment c = new Comment();
		c.setCommentId(commentId);
		c.setAuthor("bob");
		c.setContent(content);
		return c;
	}
}