package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listTodos with multiple parameters
 */
public class FilterTodosByCategoryFromDB {

  @Test
  public void listTodosWithCategoryFilters() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("category", Arrays.asList(new String[] { "hardcore metal" }));
    Todo[] categoryTodos = db.listTodos(queryParams);
    assertEquals(0, categoryTodos.length, "Incorrect number of todos with category hardcore metal");

    queryParams.clear();
    queryParams.put("category", Arrays.asList(new String[] { "video games" }));
    categoryTodos = db.listTodos(queryParams);
    assertEquals(71, categoryTodos.length, "Incorrect number of todos with category video games");

  }
}