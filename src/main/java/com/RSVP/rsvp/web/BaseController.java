package com.RSVP.rsvp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import java.util.*;

public abstract class BaseController {

    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    protected void paginate(Map<String, Object> params, Model model, Long page) {
        if (page != null) {
            params.put("page", page);
            model.addAttribute("page", page);
        } else {
            page = 1L;
            model.addAttribute("page", page);
        }
        // pagination start
        Long totalItems = (Long) params.get("totalItems");
        Integer max = (Integer) params.get("max");
        List<Long> pageList = pagination(page, totalItems, max);
        List<?> list = (List<?>) params.get("list");
        Long first = ((page - 1) * max) + 1;
        Long last = first + (list.size() - 1);
        String[] pagination = new String[]{first.toString(),
                last.toString(), totalItems.toString()};
        model.addAttribute("pagination", pagination);
        model.addAttribute("pages", pageList);
        // pagination end
    }

    private List<Long> pagination(Long page, Long totalItems, Integer max) {
        Long numberOfPages = totalItems / max;
        if (totalItems % max > 0) {
            numberOfPages++;
        }
        log.debug("Pagination: {} {} {} {}", new Object[]{page, totalItems,
                max, numberOfPages});
        Set<Long> pageList = new LinkedHashSet<>();
        long h = page - 1;
        long i = page;
        long j = page + 1;
        boolean isThousands = false;
        boolean isFiveHundreds = false;
        boolean isHundreds = false;
        boolean isFifties = false;
        boolean isTens = false;
        boolean isStarted = false;
        if (h > 0 && h > 1000) {
            for (long y = 0; y < h; y += 1000) {
                if (y == 0) {
                    isStarted = true;
                    pageList.add(1l);
                } else {
                    pageList.add(y);
                }
            }
        } else if (h > 0 && h > 500) {
            for (long y = 0; y < h; y += 500) {
                if (y == 0) {
                    isStarted = true;
                    pageList.add(1l);
                } else {
                    pageList.add(y);
                }
            }
        } else if (h > 0 && h > 100) {
            for (long y = 0; y < h; y += 100) {
                if (y == 0) {
                    isStarted = true;
                    pageList.add(1l);
                } else {
                    pageList.add(y);
                }
            }
        } else if (h > 0 && h > 50) {
            for (long y = 0; y < h; y += 50) {
                if (y == 0) {
                    isStarted = true;
                    pageList.add(1l);
                } else {
                    pageList.add(y);
                }
            }
        } else if (h > 0 && h > 10) {
            for (long y = 0; y < h; y += 10) {
                if (y == 0) {
                    isStarted = true;
                    pageList.add(1l);
                } else {
                    pageList.add(y);
                }
            }
        }
        if (i > 1 && i < 4) {
            for (long x = 1; x < i; x++) {
                pageList.add(x);
            }
        } else if (h > 0) {
            if (!isStarted) {
                pageList.add(1L);
            }
            for (long x = h; x < i; x++) {
                pageList.add(x);
            }
        }
        do {
            pageList.add(i);
            if (i > j) {
                if (isThousands || (i + 1000) < numberOfPages) {
                    isThousands = true;
                    i -= i % 1000;
                    i += 999;
                } else if (isFiveHundreds || (i + 500) < numberOfPages) {
                    isFiveHundreds = true;
                    i -= i % 500;
                    i += 499;
                } else if (isHundreds || (i + 100) < numberOfPages) {
                    isHundreds = true;
                    i -= i % 100;
                    i += 99;
                } else if (isFifties || (i + 50) < numberOfPages) {
                    isFifties = true;
                    i -= i % 50;
                    i += 49;
                } else if (isTens || (i + 10) < numberOfPages) {
                    isTens = true;
                    i -= i % 10;
                    i += 9;
                }
            }
        } while (i++ < numberOfPages);
        if (numberOfPages > 0) {
            pageList.add(numberOfPages);
        }

        log.debug("Pages {}: {}", page, pageList);
        return new ArrayList<>(pageList);
    }
}
