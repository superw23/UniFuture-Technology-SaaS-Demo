//Made by GRPTeam05
package com.app.Controller;

import com.app.Entity.Todo;
import com.app.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//TodoController class passes requests from the client and then let the TodoService class deals with them.
public class TodoController {
    @Autowired
    private TodoService service;

    //Add a new to-do item.
    @PostMapping("/addTodoItem")
    public Todo addTodo(@RequestBody Todo todo){
        return service.saveTodo(todo);
    }

    //Get all to-do items.
    @GetMapping("/toDoItems")
    public List<Todo> findAllToDoItems(){
        return service.findAllToDoItems();
    }

    //Check if the given item has been completed.
    @GetMapping("/checkCompletion")
    public String checkCompletion(@RequestParam String content){
        return service.checkCompletion(content);
    }

    //Mark the given item as completed.
    @GetMapping("/markCompleted")
    public void markCompleted(@RequestParam String content){
        service.markCompleted(content);
    }

    //Mark the given item as uncompleted.
    @GetMapping("/markUncompleted")
    public void markUncompleted(@RequestParam String content){
        service.markUncompleted(content);
    }

    //Delete the to-do item by its content.
    @DeleteMapping("/deleteItemByContent/{content}")
    public void deleteItemByContent(@PathVariable String content){
         service.deleteItemByContent(content);
    }
}
