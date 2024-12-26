package br.com.felipems.desafio_todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipems.desafio_todolist.entity.Todo;

// É a classe que acessa o Banco de dados
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
