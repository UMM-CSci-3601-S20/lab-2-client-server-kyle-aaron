package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database listTodo functionality
 */
public class FullTodosListFromDB {

  @Test
  public void totalTodoCount() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    assertEquals(300, allTodos.length, "Incorrect total number of todos");
  }

  @Test
  public void firstTodoInFullList() throws IOException {
    Database db = new Database("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo firstTodo = allTodos[0];
    assertEquals("58895985a22c04e761776d54", firstTodo._id, "Incorrect id");
    assertEquals("Blanche", firstTodo.owner, "Incorrect owner");
    assertEquals(false, firstTodo.status, "Incorrect status");
    assertEquals("In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.", firstTodo.body, "Incorrect body");
    assertEquals("software design", firstTodo.category, "Incorrect category");
  }
}
