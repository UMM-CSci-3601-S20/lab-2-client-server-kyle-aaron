package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listTodos with limit parameters
 */
public class FilterTodosByOrderByFromDB {

  @Test
  public void listTodosWithOrderByFilters() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("orderBy", Arrays.asList(new String[] { "owner" }));
    Todo[] orderByTodos = db.listTodos(queryParams);
    // There are 51 "Barry" values, so we expect these to be first in the list after sorting 
    int precedingBarryCount = 0;
    for (int i = 0; i < 51; i++) {
        if (orderByTodos[i].owner.equals("Barry")) {
            precedingBarryCount++;    
        }
    }
    assertEquals(51, precedingBarryCount, "Incorrect sorting with owner filter");

    queryParams.clear();
    queryParams.put("orderBy", Arrays.asList(new String[] { "status" }));
    orderByTodos = db.listTodos(queryParams);
    // There are 157 "false" values, so we expect these to be first in the list after sorting 
    int precedingFalseCount = 0;
    for (int i = 0; i < 157; i++) {
        if (orderByTodos[i].status == false) {
            precedingFalseCount++;    
        }
    }
    assertEquals(157, precedingFalseCount, "Incorrect sorting with status filter");

    queryParams.clear();
    queryParams.put("orderBy", Arrays.asList(new String[] { "body" }));
    orderByTodos = db.listTodos(queryParams);
    // Check the first value (lexicographically)
    int firstBody = 0;
    for (int i = 0; i < 1; i++) {
        if (orderByTodos[i].body.equals("Ad sint incididunt officia veniam incididunt. Voluptate exercitation eu aliqua laboris occaecat deserunt cupidatat velit nisi sunt mollit sint amet.")) {
            firstBody++;    
        }
    }
    assertEquals(1, firstBody, "Incorrect sorting with body filter");

    queryParams.clear();
    queryParams.put("orderBy", Arrays.asList(new String[] { "category" }));
    orderByTodos = db.listTodos(queryParams);
    // There are 76 "groceries" values, so we expect these to be first in the list after sorting 
    int precedingGroceryCount = 0;
    for (int i = 0; i < 76; i++) {
        if (orderByTodos[i].category.equals("groceries")) {
            precedingGroceryCount++;    
        }
    }
    assertEquals(76, precedingGroceryCount, "Incorrect sorting with category filter");
    
  }
}