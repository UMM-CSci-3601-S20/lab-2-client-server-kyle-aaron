package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listTodos with owner parameters
 */
public class FilterTodosByOwnerFromDB {

  @Test
  public void listTodosWithOwnerFilters() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] { "GOD" }));
    Todo[] ownerTodos = db.listTodos(queryParams);
    assertEquals(0, ownerTodos.length, "Incorrect number of todos");
    
    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] { "Fry" }));
    ownerTodos = db.listTodos(queryParams);
    assertEquals(61, ownerTodos.length, "Incorrect number of todos");

    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] { "Barry" }));
    ownerTodos = db.listTodos(queryParams);
    assertEquals(51, ownerTodos.length, "Incorrect number of todos");

    queryParams.clear();
    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    ownerTodos = db.listTodos(queryParams);
    assertEquals(43, ownerTodos.length, "Incorrect number of todos");
  }
}