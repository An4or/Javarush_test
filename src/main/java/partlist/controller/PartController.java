package partlist.controller;

import partlist.model.Part;
import partlist.services.PartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class PartController {

    private final int RECORDS_OF_PAGE = 10;

    private PartService partService;

    private int displayFilterFlag = 1; // default = all parts

    @Autowired()
    @Qualifier(value = "partService")
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @RequestMapping(value = "/parts")
    public ModelAndView partList(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer selectorList) {

        ModelAndView modelAndView = new ModelAndView("/parts");
        modelAndView.addObject("part", new Part());

        if (selectorList != null) displayFilterFlag = selectorList;

        List<Part> parts = switchList(displayFilterFlag);

        if (!parts.isEmpty()) {
            PagedListHolder<Part> pagedListHolder = new PagedListHolder<>(parts);

            pagedListHolder.setPageSize(RECORDS_OF_PAGE);

            int maxPage = pagedListHolder.getPageCount();
            modelAndView.addObject("maxPage", maxPage);

            if (page == null || page < 1 || page > maxPage) page = 1;
            modelAndView.addObject("page", page);

            if (page > maxPage)
                pagedListHolder.setPage(page);
            else if (page <= maxPage)
                pagedListHolder.setPage(page - 1);

            modelAndView.addObject("listParts", pagedListHolder.getPageList());

            modelAndView.addObject("forAssembly", computersForAssembly());
        } else {
            modelAndView.addObject("listParts", null);
        }

        return modelAndView;

    }


    @RequestMapping("/remove/{id}")
    public ModelAndView removePart(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/parts");
        this.partService.deletePart(partService.getPartById(id));
        return modelAndView;
    }


    @RequestMapping(value = "/parts/add", method = RequestMethod.POST)
    public ModelAndView addOrUpdatePart(@ModelAttribute("part") Part part) {
        ModelAndView modelAndView = new ModelAndView("redirect:/parts");

        if (part.getId() == 0) this.partService.addPart(part);
        else this.partService.updatePart(part);

        return modelAndView;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView editPart(@RequestParam() int id,
                                 @RequestParam(required = false) Integer page) {
        ModelAndView modelAndView = new ModelAndView("/parts");
        modelAndView.addObject("part", partService.getPartById(id));

        List<Part> parts = switchList(displayFilterFlag);

        PagedListHolder<Part> pagedListHolder = new PagedListHolder<>(parts);

        pagedListHolder.setPageSize(RECORDS_OF_PAGE);

        int maxPage = pagedListHolder.getPageCount();
        modelAndView.addObject("maxPage", maxPage);

        if (page == null || page < 1 || page > maxPage) page = 1;
        modelAndView.addObject("page", page);

        if (page > maxPage) pagedListHolder.setPage(page);
        else if (page <= maxPage) pagedListHolder.setPage(page - 1);

        modelAndView.addObject("listParts", pagedListHolder.getPageList());

        modelAndView.addObject("forAssembly", computersForAssembly());

        return modelAndView;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView searchUser(@RequestParam() String title) {
        ModelAndView modelAndView = new ModelAndView("/parts");
        modelAndView.addObject("part", new Part());

        modelAndView.addObject("listParts", partService.getPartByTitle(title));

        return modelAndView;
    }


    private int computersForAssembly() {
        int quantity = 0;

        List<Part> onlyNecessaryParts = partService.getFilterPartList(true);

        if (!onlyNecessaryParts.isEmpty()) {
            Collections.sort(onlyNecessaryParts, (p1, p2) -> {
                int x = p1.getQuantity();
                int y = p2.getQuantity();
                return x > y ? 1 : x < y ? -1 : 0;
            });
            quantity = onlyNecessaryParts.get(0).getQuantity();
        }

        return quantity;
    }

    private List<Part> switchList(int i) {
        List<Part> result = null;

        switch (i) {
            case 1:
                result = partService.getPartList();
                break;
            case 0:
                result = partService.getFilterPartList(true);
                break;
            case -1:
                result = partService.getFilterPartList(false);
                break;
        }

        return result;
    }
}
