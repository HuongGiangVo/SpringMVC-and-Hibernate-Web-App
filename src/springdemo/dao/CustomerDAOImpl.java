package springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import springdemo.entity.Customer;
@Repository  // spring component, handle exception transaction
public class CustomerDAOImpl implements CustomerDAO {
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;// this name also is in bean id="sessionFactory" in the spring-mvc-crud-demo-servlet.xml
	
	@Override
	public List<Customer> getCustomers() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();//import from org.hibernate
		//create a query ---sort by lastName
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class); //import from org.hibernate.query
		//execute query and get result list
		List<Customer> customers=theQuery.getResultList();		
		
		//return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		//save or update the customer 
		currentSession.saveOrUpdate(theCustomer);//this from hibernate . it checks if id has already, ->update and save
		
	}

	@Override
	public Customer getCustomer(int theId) {
		//get current session hibernate
		Session currentSession = sessionFactory.getCurrentSession();
		//retrieve / read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		//get the current session hibernate
		Session currentSession = sessionFactory.getCurrentSession();
		//delete object with primary key
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId",theId);
		theQuery.executeUpdate();
	}

}
