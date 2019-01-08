package io.hyte.openjpa.itest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.hyte.openjpa.OrderServiceJpa;
import io.hyte.openjpa.model.a.Order;
import io.hyte.openjpa.model.a.SimpleOrderItem;
import io.hyte.openjpa.model.b.SpecialOrderItem;
import io.hyte.openjpa.model.c.ExtraSpecialOrderItem;

public class OpenJPAModularTest {
	
	protected static EntityManagerFactory entityManagerFactory = null;
	protected OrderServiceJpa orderServiceJpa = null;

	@BeforeClass
	public static void setupClass() throws SQLException {
		entityManagerFactory = Persistence.createEntityManagerFactory("hyte-orders-h2-tcp");
		
		EntityManager tmpEm = entityManagerFactory.createEntityManager();
		tmpEm.getTransaction().begin();
		
		Connection connection = tmpEm.unwrap(java.sql.Connection.class);
		RunScript.execute(connection, new InputStreamReader(ExtraSpecialOrderItem.class.getResourceAsStream("/db/h2/h2-orders-extraspecial-drop.sql")));
		RunScript.execute(connection, new InputStreamReader(SpecialOrderItem.class.getResourceAsStream("/db/h2/h2-orders-special-drop.sql")));
	  	RunScript.execute(connection, new InputStreamReader(Order.class.getResourceAsStream("/db/h2/h2-orders-drop.sql")));
	  	
	  	RunScript.execute(connection, new InputStreamReader(Order.class.getResourceAsStream("/db/h2/h2-orders-create.sql")));
		RunScript.execute(connection, new InputStreamReader(SpecialOrderItem.class.getResourceAsStream("/db/h2/h2-orders-special-create.sql")));
		RunScript.execute(connection, new InputStreamReader(ExtraSpecialOrderItem.class.getResourceAsStream("/db/h2/h2-orders-extraspecial-create.sql")));
	  	
	  	tmpEm.getTransaction().commit();
	}
	
	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory = null;
	}
	
	@Before
	public void setUp() {
		orderServiceJpa = new OrderServiceJpa();
		orderServiceJpa.setEntityManagerFactory(entityManagerFactory);
	}
	
	@After
	public void tearDown() { 
		orderServiceJpa.setEntityManagerFactory(null);
		orderServiceJpa = null;
		
	}
	
	@Test
	public void testOrderNoItemsLifecycle() throws Exception {
		Order order = generateOrder();
		order.getOrderItems().clear();

		orderServiceJpa.saveOrder(order);
		
		Order getOrder = orderServiceJpa.getOrder(order.getOrderId());
		
		orderServiceJpa.deleteOrder(getOrder.getOrderId());
		
		List<Order> orders = orderServiceJpa.listOrders();
		assertNotNull(orders);
		assertTrue(orders.isEmpty());
	}

	
	@Test
	public void testOrderItemsLifecycle() throws Exception {
		Order order = generateOrder();
		orderServiceJpa.saveOrder(order);
		
		Order getOrder = orderServiceJpa.getOrder(order.getOrderId());
		
		orderServiceJpa.deleteOrder(getOrder.getOrderId());
		
		List<Order> orders = orderServiceJpa.listOrders();
		assertNotNull(orders);
		assertTrue(orders.isEmpty());
	}
	
	protected static Order generateOrder() {
		Order order = new Order();
		order.setCreatedBy("junit");
		order.setCreatedDateTime(Calendar.getInstance());
		
		SimpleOrderItem simpleOrderItem = new SimpleOrderItem();
		simpleOrderItem.setExtra("extrastuff");
		simpleOrderItem.setQuantity(1);
		simpleOrderItem.setSku("sku-simple");

		
		SpecialOrderItem specialOrderItem = new SpecialOrderItem();
		specialOrderItem.setSpecial("special stuff");
		specialOrderItem.setQuantity(2);
		specialOrderItem.setSku("sku-special");

		ExtraSpecialOrderItem extraSpecialOrderItem = new ExtraSpecialOrderItem();
		extraSpecialOrderItem.setExtraSpecial("extra special stuff");
		extraSpecialOrderItem.setQuantity(3);
		extraSpecialOrderItem.setSku("sku-extraspecial");
		
		order.getOrderItems().add(simpleOrderItem);
		order.getOrderItems().add(specialOrderItem);
		order.getOrderItems().add(extraSpecialOrderItem);
		return order;
	}
}
