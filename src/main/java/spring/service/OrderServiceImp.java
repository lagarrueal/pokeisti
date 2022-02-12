package spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.dao.OrderDao;
import spring.model.Order;

@Service
public class OrderServiceImp implements OrderService{

	@Autowired
	private OrderDao orderDao;

	@Transactional
	public void save(Order order) {
		orderDao.save(order);
	}

	@Transactional
	public void delete(int order_id) {
		orderDao.delete(order_id);
	}

	@Transactional(readOnly = true)
	public List<Order> list() {
		return orderDao.list();
	}
	
	@Transactional(readOnly = true)
	public Order getOrder(int order_id) {
		return orderDao.getOrder(order_id);
	}
}
