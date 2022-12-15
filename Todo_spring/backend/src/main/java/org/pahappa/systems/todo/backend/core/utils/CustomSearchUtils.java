package org.pahappa.systems.todo.backend.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.sers.webutils.model.RecordStatus;
import org.sers.webutils.model.utils.SortField;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;

public class CustomSearchUtils {

    private static final int MINIMUM_CHARACTERS_FOR_SEARCH_TERM = 1;

    public static boolean searchTermSatisfiesQueryCriteria(String query) {
        if (StringUtils.isBlank(query)) {
            return false;
        }
        return query.length() >= MINIMUM_CHARACTERS_FOR_SEARCH_TERM;
    }

    private static Search generateSearchTerms(String query, List<String> searchFields) {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

        if (StringUtils.isNotBlank(query) && CustomSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
            ArrayList<Filter> filters = new ArrayList<Filter>();
            CustomSearchUtils.generateSearchTerms(searchFields, query, filters);
            search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
        }
        return search;
    }
    
    private static boolean generateSearchTerms(List<String> searchFields, String query, List<Filter> filters) {
        if (searchFields != null && !searchFields.isEmpty()) {
            for (String token : query.replaceAll("  ", " ").split(" ")) {
                String searchTerm = "%" + StringEscapeUtils.escapeSql(token) + "%";
                Filter[] orFilters = new Filter[searchFields.size()];
                int counter = 0;
                for (String searchField : searchFields) {
                    orFilters[counter] = Filter.like(searchField, searchTerm);
                    counter++;
                }
                filters.add(Filter.or(orFilters));
            }
            return true;
        }
        return false;
    }
    
    public static Search addSortField(SortField sortField, Search search) {
    	if (sortField != null) {
			search.addSort(sortField.getSort());
		} else {
			search.addSort(new Sort("dateCreated", true));
		}

		return search;
    }

    public static Search generateSearchObjectForUsers(String query, SortField sortField) {
        Search search = generateSearchTerms(query, Arrays.asList("username", "firstName", "lastName", "emailAddress"));
		return addSortField(sortField, search);
    }
    
    public static Search generateSearchObjectForRoles(String query, SortField sortField) {
        Search search = generateSearchTerms(query, Arrays.asList("name", "description"));
		return addSortField(sortField, search);
    }
    
    public static Search generateSearchObjectForPermissions(String query, SortField sortField) {
        Search search = generateSearchTerms(query, Arrays.asList("name", "description"));
		return addSortField(sortField, search);
    }

    public static Search generateSearchForIntegers(Search search, List<String> selectedIntColumns, int min, int max) {
    	if(selectedIntColumns != null) {
    		if(selectedIntColumns.size() > 0) {
    			for(String name : selectedIntColumns) {
    				search.addFilterAnd(Filter.lessOrEqual(name, min), Filter.greaterOrEqual(name, max));
    			}
    		} else {
    			selectedIntColumns = Arrays.asList("eloe", "estimatedCostAssignee", "estimatedCostOrganisation",
    					"actualLoe", "actualCostOrganisation", "actualCostAssignee");
    			for(String name : selectedIntColumns) {
    				search.addFilterAnd(Filter.lessOrEqual(name, min), Filter.greaterOrEqual(name, max));
    			}
    		}
    	}
    	return search;
    }
    
    public static Search generateSearchForDates(Search search, List<String> selectedDates, Date startDate, Date endDate) {
    	if(selectedDates != null) {
    		if(selectedDates.size() > 0) {
    			for(String name : selectedDates) {
//    				search.addFilterOr(Filter.lessOrEqual(name, endDate), Filter.greaterOrEqual(name, startDate));
    				search.addFilterGreaterOrEqual(name, startDate);
    				search.addFilterLessOrEqual(name, endDate);
    			}
    		} else {
    			selectedDates = Arrays.asList("expectedDateOfDelivery", "actualDateOfDelivery", "dateCreated");
    			for(String name : selectedDates) {
//    				search.addFilterOr(Filter.lessOrEqual(name, endDate), Filter.greaterOrEqual(name, startDate));
    				search.addFilterGreaterOrEqual(name, startDate);
    				search.addFilterLessOrEqual(name, endDate);
    			}
    		}
    	}
    	return search;
    }

}

