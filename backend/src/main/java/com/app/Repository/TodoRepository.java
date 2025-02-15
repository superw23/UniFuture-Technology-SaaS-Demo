//Made by GRPTeam05
package com.app.Repository;

import com.app.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Todo findByContent(String content);
    @Transactional
    public void deleteByContent(String content);
}
