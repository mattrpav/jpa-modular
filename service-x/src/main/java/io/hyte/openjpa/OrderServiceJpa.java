package io.hyte.openjpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.hyte.openjpa.model.a.Order;

public class OrderServiceJpa {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceJpa.class);
	private EntityManagerFactory entityManagerFactory = null;
	private boolean localTransactionEnabled = true;

	protected static void closeEntityManager(EntityManager em) {
		if (em != null && em.isOpen()) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} finally {
				em.close();
			}
		}
	}

	public List<Order> listOrders() throws Exception {
		
		EntityManager em = null;
		
		try {
			em = getEntityManagerFactory().createEntityManager();
			
			if(isLocalTransactionEnabled()) {
				em.getTransaction().begin();
			}

			TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
			List<Order> orders = query.getResultList();
			
			if(isLocalTransactionEnabled()) {
				em.getTransaction().commit();
			}
			
			if(logger.isDebugEnabled()) {
				logger.debug("List returned orders.size:{}", orders.size());
			}
			return orders;
		} finally {
			closeEntityManager(em);
		}
	}
	
	public Order getOrder(long orderId) throws Exception {
		
		EntityManager em = null;
		
		try {
			em = getEntityManagerFactory().createEntityManager();
			
			if(isLocalTransactionEnabled()) {
				em.getTransaction().begin();
			}

			TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o where o.orderId = :orderId", Order.class);
			query.setParameter("orderId", orderId);
			
			Order order = query.getSingleResult();
			
			if(isLocalTransactionEnabled()) {
				em.getTransaction().commit();
			}
			
			if(logger.isDebugEnabled()) {
				logger.debug("Found orderId:{}", order.getOrderId());
			}
			return order;
		} finally {
			closeEntityManager(em);
		}
	}

	public void saveOrder(Order order) throws Exception {
		EntityManager em = null;
			
		try {
			em = getEntityManagerFactory().createEntityManager();
				
			if(isLocalTransactionEnabled()) {
				em.getTransaction().begin();
			}

    		em.persist(order);
			//em.flush();
							
			if(isLocalTransactionEnabled()) {
				em.getTransaction().commit();
			}

			if(logger.isDebugEnabled()) {
				logger.info("Saved orderId:{}", order.getOrderId());
			}
		} finally {
			closeEntityManager(em);
		}		
	}

	public void deleteOrder(long orderId) throws Exception {

		EntityManager em = null;
		try { 
			em = entityManagerFactory.createEntityManager();

			if(isLocalTransactionEnabled()) {
				em.getTransaction().begin();
			}
			
			TypedQuery<Order> query = em.createQuery("DELETE FROM Order o where o.orderId = :orderId", Order.class);
			query.setParameter("orderId", orderId);
			
			int rows = query.executeUpdate();
			em.flush();
			
			if(isLocalTransactionEnabled()) {
				em.getTransaction().commit();
			}
						
			if(logger.isDebugEnabled()) {
				logger.debug("Deleted orderId:{} total rows:{}", orderId, rows);
			}
		} finally {
			closeEntityManager(em);
		}		
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public boolean isLocalTransactionEnabled() {
		return localTransactionEnabled;
	}

	public void setLocalTransactionEnabled(boolean localTransactionEnabled) {
		this.localTransactionEnabled = localTransactionEnabled;
	}
}
