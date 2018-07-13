package userapi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import userapi.models.User;

@Repository
public class UserDao {
  
  @Autowired
  private SessionFactory _sessionFactory;
  
  private Session getSession() {
    return _sessionFactory.getCurrentSession();
  }

  public void save(final User user) {
    getSession().save(user);
    return;
  }
  
  public void saveOrUpdate(final User user) {
	    getSession().saveOrUpdate(user);
	    return;
	  }
  
  public void delete(User user) {
    getSession().delete(user);
    return;
  }
  
  @SuppressWarnings("unchecked")
  public List<User> getAll() {
    return getSession().createQuery("from User").list();
  }
  
  public User getByEmail(String email) {
    return (User) getSession().createQuery(
        "from User where email = :email")
        .setParameter("email", email)
        .uniqueResult();
  }
  
  public User getByRegToken(String token) {
	    return (User) getSession().createQuery(
	        "from User where reg_token = :token")
	        .setParameter("token", token)
	        .uniqueResult();
	  }
  
  public User getByForgotPassToken(String token) {
	    return (User) getSession().createQuery(
	        "from User where forgot_token = :token")
	        .setParameter("token", token)
	        .uniqueResult();
	  }

  public User getById(long id) {
	  return (User) getSession().createQuery(
		        "from User where id = :id")
		        .setParameter("id", id)
		        .uniqueResult();
  }

  public void update(User user) {
    getSession().update(user);
    return;
  }

} // class UserDao
