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
public class FilterTodosByLimitFromDB {

  @Test
  public void listTodosWithLimitFilters() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("limit", Arrays.asList(new String[] { "0" }));
    Todo[] limitTodos = db.listTodos(queryParams);
    assertEquals(0, limitTodos.length, "Incorrect number of todos");
    
    queryParams.clear();
    queryParams.put("limit", Arrays.asList(new String[] { "5" }));
    limitTodos = db.listTodos(queryParams);
    assertEquals(5, limitTodos.length, "Incorrect number of todos");


    queryParams.clear();
    queryParams.put("limit", Arrays.asList(new String[] { "20" }));
    limitTodos = db.listTodos(queryParams);
    assertEquals(20, limitTodos.length, "Incorrect number of todos");
  }
}