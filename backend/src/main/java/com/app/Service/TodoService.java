//Made by GRPTeam05
package com.app.Service;

import com.app.Entity.Todo;
import com.app.Repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@AllArgsConstructor
@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    //Add a to-do item in the repository.
    public Todo saveTodo(Todo todo){
        return todoRepository.save(todo);
    }

    //Check if the given item has been completed.
    public String checkCompletion(String content){
        Todo todo = todoRepository.findByContent(content);
        return todo.getCompleted();
    }

    //Get all to-do items.
    public List<Todo> findAllToDoItems(){
        return todoRepository.findAll();
    }

    //Mark the given item as completed.
    public void markCompleted(String content){
        Todo todo = todoRepository.findByContent(content);
        todo.setCompleted("true");
        todoRepository.save(todo);
    }

    //Mark the given item as uncompleted.
    public void markUncompleted(String content){
        Todo todo = todoRepository.findByContent(content);
        todo.setCompleted("false");
        todoRepository.save(todo);
    }

    //Delete the to-do item by its content.
    public void deleteItemByContent(String content){
        todoRepository.deleteByContent(content);
    }
}
