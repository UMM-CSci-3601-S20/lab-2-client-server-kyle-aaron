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
public class FilterTodosByBodyFromDB {

  @Test
  public void listTodosWithBody() throws IOException {
    tDatabase db = new tDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("contains", Arrays.asList(new String[] { "Hit or Miss, I guess they never miss, huh?" }));
    Todo[] bodyTodos = db.listTodos(queryParams);
    assertEquals(0, bodyTodos.length, "Incorrect number of todos with no body");

    queryParams.clear();
    queryParams.put("contains", Arrays.asList(new String[] { "Voluptate cillum non adipisicing velit nisi. Anim eu sunt occaecat dolor commodo exercitation." }));
    bodyTodos = db.listTodos(queryParams);
    assertEquals(1, bodyTodos.length, "Incorrect number of todos with that body");

    queryParams.clear();
    queryParams.put("contains", Arrays.asList(new String[] { "ipsum" }));
    bodyTodos = db.listTodos(queryParams);
    assertEquals(60, bodyTodos.length, "Incorrect number of todos with that word in their body");
  }
}