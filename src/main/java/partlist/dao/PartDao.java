package partlist.dao;

import partlist.model.Part;

import java.util.List;

public interface PartDao {
    void addPart(Part part);
    void updatePart(Part part);
    void deletePart(Part part);
    Part getPartById(int id);
    List<Part> getPartByTitle(String title);
    List<Part> getAllPartList();
    List<Part> getFilterPartList(boolean necessity);

}
