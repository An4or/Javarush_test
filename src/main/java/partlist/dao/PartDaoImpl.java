package partlist.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import partlist.model.Part;
import org.hibernate.Session;

import java.util.List;

@Repository
public class PartDaoImpl implements PartDao {

    private static final Logger logger = LoggerFactory.getLogger(PartDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Part> getAllPartList() {
        List<Part> result = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createCriteria(Part.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("The allPartList was successfully received ");
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Part> getFilterPartList(boolean necessity) {
        List<Part> result = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Query query = session.createQuery("from Part where necessity = :param ");
            query.setParameter("param", necessity);
            result = (List<Part>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info(String.format("The partList is necessarily [%s] received successfully", necessity));

        return result;
    }


    public void addPart(Part part) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Part successfully added. Details: " + part);
    }

    public void updatePart(Part part) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Part successfully updated. Details: " + part);
    }

    public void deletePart(Part part) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(part);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Part successfully deleted. Details: " + part);
    }

    public Part getPartById(int id) {
        Part result = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result =  session.get(Part.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info(String.format("The Part with the ID = [%d] was successfully received", id));

        return result;

    }

    @SuppressWarnings("unchecked")
    public List<Part> getPartByTitle(String title) {
        List<Part> result = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Query query = session.createQuery("from Part where title = :param ");
            query.setParameter("param", title);
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info(String.format("The Part with the TITLE = [%s] was successfully received", title));

        return result;
    }
}
