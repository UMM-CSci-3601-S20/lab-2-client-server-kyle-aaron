package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todo.Database getTodo functionality
 */
public class GetTodoByIDFromDB {

  @Test
  public void getID_5889598555fbbad472586a56() throws IOException {
    Database db = new Database("/todos.json");
    Todo todo = db.getTodo("5889598555fbbad472586a56");
    assertEquals("5889598555fbbad472586a56", todo._id, "Incorrect id");
  }

  @Test
  public void getID_58895985ee4964bdc668bd9e() throws IOException {
    Database db = new Database("/todos.json");
    Todo todo = db.getTodo("58895985ee4964bdc668bd9e");
    assertEquals("58895985ee4964bdc668bd9e", todo._id, "Incorrect id");
  }
}