package br.com.felipems.desafio_todolist.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.felipems.desafio_todolist.entity.Todo;
import br.com.felipems.desafio_todolist.repository.TodoRepository;


@Service
public class TodoService {
    Logger logger = Logger.getLogger(TodoService.class.getName());
    private TodoRepository todoRepository;

    // O construtor implicitamente efetua a injeção de dependência*
    // não é necessário o @Autowired
    // * mais recomendado injeção de dependência pelo construtor
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Todas as operações retornarão a lista da Todos atualizada (Uma todo list sempre exibe todos os itens)
    public List<Todo> create(Todo todo) {
        logger.info("Create Todo!");
        todoRepository.save(todo);
        return list();
    }

    public List<Todo> list() {
        logger.info("List Todos!");

        Sort sort = Sort.by("prioridade").descending().and(
            Sort.by("nome").ascending());
        return todoRepository.findAll(sort);
    }

    public List<Todo> update(Todo todo) {
        logger.info("Update Todos!");        
        todoRepository.save(todo);
        return list();
    }

    public List<Todo> delete(Long id) {
        todoRepository.deleteById(id);
        return list();
    }
}
