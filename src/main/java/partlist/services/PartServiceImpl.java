package partlist.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import partlist.dao.PartDao;
import partlist.model.Part;

import java.util.List;

@Service
@Transactional
public class PartServiceImpl implements partlist.services.PartService {

    private PartDao partDao;

    public void setPartDao(PartDao partDao) {
        this.partDao = partDao;
    }

    public List<Part> getFilterPartList(boolean necessity) {
        return partDao.getFilterPartList(necessity);
    }

    @Transactional
    public void addPart(Part part) {
        partDao.addPart(part);
    }

    @Transactional
    public void updatePart(Part part) {
        partDao.updatePart(part);
    }

    @Transactional
    public void deletePart(Part part) {
        partDao.deletePart(part);
    }

    @Transactional
    public Part getPartById(int id) {
        return partDao.getPartById(id);
    }

    @Transactional
    public List<Part> getPartByTitle(String title) {
        return partDao.getPartByTitle(title);
    }

    @Transactional
    public List<Part> getPartList() {
        return partDao.getAllPartList();
    }
}
