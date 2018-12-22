package partlist.services;

import partlist.model.Part;

import java.util.List;

public interface PartService {
    void addPart(Part part);
    void updatePart(Part part);
    void deletePart(Part part);
    Part getPartById(int id);
    List<Part> getPartByTitle(String title);
    List<Part> getPartList();
    List<Part> getFilterPartList(boolean necessity);
}
