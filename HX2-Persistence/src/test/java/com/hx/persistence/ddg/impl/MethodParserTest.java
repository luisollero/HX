package com.hx.persistence.ddg.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import net.sf.cglib.proxy.MethodInterceptor;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.junit.Test;
import org.mockito.Mockito;

import com.hx.persistence.ddg.impl.DeleteAllMethodInterceptor;
import com.hx.persistence.ddg.impl.DeleteMethodInterceptor;
import com.hx.persistence.ddg.impl.FindByExampleMethodInterceptor;
import com.hx.persistence.ddg.impl.FindByPropertiesMethodInterceptor;
import com.hx.persistence.ddg.impl.FindMethodInterceptor;
import com.hx.persistence.ddg.impl.FindType;
import com.hx.persistence.ddg.impl.MethodParser;
import com.hx.persistence.ddg.impl.SaveAllMethodInterceptor;
import com.hx.persistence.ddg.impl.SaveMethodInterceptor;
import com.hx.persistence.ddg.impl.SaveOrUpdateAllMethodInterceptor;
import com.hx.persistence.ddg.impl.SaveOrUpdateMethodInterceptor;
import com.hx.persistence.ddg.impl.UnknownMethodInterceptor;
import com.hx.persistence.ddg.impl.UpdateAllMethodInterceptor;
import com.hx.persistence.ddg.impl.UpdateMethodInterceptor;
import com.hx.persistence.tx.TransactionManager;

public class MethodParserTest {

	@Test
	public void testSave() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "save", transactionManager);
		assertSame(SaveMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((SaveMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testSaveAll() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "saveAll", transactionManager);
		assertSame(SaveAllMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((SaveAllMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testUpdate() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "update", transactionManager);
		assertSame(UpdateMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((UpdateMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testUpdateAll() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "updateAll", transactionManager);
		assertSame(UpdateAllMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((UpdateAllMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "saveOrUpdate", transactionManager);
		assertSame(SaveOrUpdateMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((SaveOrUpdateMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testSaveOrUpdateAll() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "saveOrUpdateAll", transactionManager);
		assertSame(SaveOrUpdateAllMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((SaveOrUpdateAllMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testDelete() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "delete", transactionManager);
		assertSame(DeleteMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((DeleteMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testDeleteAll() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "deleteAll", transactionManager);
		assertSame(DeleteAllMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((DeleteAllMethodInterceptor) methodInterceptor).transactionManager);
	}

	@Test
	public void testGet() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "get", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGet() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGet", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFind() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "find", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCount() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "count", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.count, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testGetB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testNotNullGetB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testNotNullGetBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testCountBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.count, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testGetByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.count, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testGetByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testNotNullGetByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testCountByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.count, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testGetByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.count, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testGetWithLazies() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLazies", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetWithLazies() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLazies", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindWithLazies() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLazies", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(0, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountWithLazies() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLazies", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testNotNullGetWithLaziesB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindWithLaziesB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testCountWithLaziesB() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesB", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testNotNullGetWithLaziesBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLaziesBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindWithLaziesBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testCountWithLaziesBy() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesBy", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetWithLaziesByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLaziesByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindWithLaziesByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesByX", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("x", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountWithLaziesByX() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesByX", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetWithLaziesByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLaziesByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindWithLaziesByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesByXY", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountWithLaziesByXY() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesByXY", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testNotNullGetWithLaziesByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLaziesByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindWithLaziesByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testCountWithLaziesByXYAnd() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesByXYAnd", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetWithLaziesByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLaziesByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindWithLaziesByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesByXYAndABAndCcc", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(3, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("xY", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("aB", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals("ccc", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(2));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountWithLaziesByXYAndABAndCcc() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesByXYAndABAndCcc", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testGetWithLaziesByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "getWithLaziesByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.get, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testNotNullGetWithLaziesByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "notNullGetWithLaziesByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.notNullGet, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testFindWithLaziesByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findWithLaziesByExample", transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
	}

	@Test
	public void testCountWithLaziesByExample() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "countWithLaziesByExample", transactionManager);
		assertSame(UnknownMethodInterceptor.class, methodInterceptor.getClass());
	}

	@Test
	public void testFindByParentTaxonomyAndParentTaxonAndOrderAscByTaxonId() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByParentTaxonomyAndParentTaxonAndOrderAscByTaxonId",
				transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(2, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("parentTaxonomy", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("parentTaxon", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).ascOrderProperties.size());
		assertEquals(Boolean.TRUE, ((FindByPropertiesMethodInterceptor) methodInterceptor).ascOrderProperties.get(0));
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).orderProperties.size());
		assertEquals("taxonId", ((FindByPropertiesMethodInterceptor) methodInterceptor).orderProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
		assertFalse(((FindMethodInterceptor) methodInterceptor).limited);
	}

	@Test
	public void testFindWithLaziesByParentTaxonomyAndParentTaxonAndOrderAscByTaxonIdLimited() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null,
				"findWithLaziesByParentTaxonomyAndParentTaxonAndOrderAscByTaxonIdLimited", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(2, ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.size());
		assertEquals("parentTaxonomy", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(0));
		assertEquals("parentTaxon", ((FindByPropertiesMethodInterceptor) methodInterceptor).queryProperties.get(1));
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).ascOrderProperties.size());
		assertEquals(Boolean.TRUE, ((FindByPropertiesMethodInterceptor) methodInterceptor).ascOrderProperties.get(0));
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).orderProperties.size());
		assertEquals("taxonId", ((FindByPropertiesMethodInterceptor) methodInterceptor).orderProperties.get(0));
		assertTrue(((FindMethodInterceptor) methodInterceptor).withLazies);
		assertTrue(((FindMethodInterceptor) methodInterceptor).limited);
	}

	@Test
	public void testFindOrderAscByTaxonIdLimited() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findOrderAscByTaxonIdLimited", transactionManager);
		assertSame(FindByPropertiesMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByPropertiesMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByPropertiesMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).ascOrderProperties.size());
		assertEquals(Boolean.TRUE, ((FindByPropertiesMethodInterceptor) methodInterceptor).ascOrderProperties.get(0));
		assertEquals(1, ((FindByPropertiesMethodInterceptor) methodInterceptor).orderProperties.size());
		assertEquals("taxonId", ((FindByPropertiesMethodInterceptor) methodInterceptor).orderProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
		assertTrue(((FindMethodInterceptor) methodInterceptor).limited);
	}

	@Test
	public void testFindByExampleAndOrderDescByPropertyName1() throws Exception {

		TransactionManager transactionManager = mockTransationManager();

		MethodInterceptor methodInterceptor = MethodParser.parse(null, "findByExampleAndOrderDescByPropertyName1",
				transactionManager);
		assertSame(FindByExampleMethodInterceptor.class, methodInterceptor.getClass());
		assertSame(transactionManager, ((FindByExampleMethodInterceptor) methodInterceptor).transactionManager);
		assertSame(FindType.find, ((FindByExampleMethodInterceptor) methodInterceptor).findType);
		assertEquals(1, ((FindByExampleMethodInterceptor) methodInterceptor).ascOrderProperties.size());
		assertEquals(Boolean.FALSE, ((FindByExampleMethodInterceptor) methodInterceptor).ascOrderProperties.get(0));
		assertEquals(1, ((FindByExampleMethodInterceptor) methodInterceptor).orderProperties.size());
		assertEquals("propertyName1", ((FindByExampleMethodInterceptor) methodInterceptor).orderProperties.get(0));
		assertFalse(((FindMethodInterceptor) methodInterceptor).withLazies);
		assertFalse(((FindMethodInterceptor) methodInterceptor).limited);
	}

	private TransactionManager mockTransationManager() {

		TransactionManager transactionManager = mock(TransactionManager.class);
		SessionFactory sessionFactory = mock(SessionFactory.class);
		ClassMetadata classMetadata = mock(ClassMetadata.class);

		stub(transactionManager.getSessionFactory()).toReturn(sessionFactory);
		stub(sessionFactory.getClassMetadata(Mockito.<Class<?>> anyObject())).toReturn(classMetadata);
		stub(classMetadata.getPropertyTypes()).toReturn(new Type[0]);

		return transactionManager;
	}
}
