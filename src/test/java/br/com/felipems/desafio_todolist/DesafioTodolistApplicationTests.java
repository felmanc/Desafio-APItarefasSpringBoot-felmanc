package br.com.felipems.desafio_todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.felipems.desafio_todolist.entity.Todo;

// Para utilizar o H2 nos testes é necessário definir uma porta randomica
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DesafioTodolistApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	void createTodoSuccess() {
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		webTestClient
		//Requisição
		.post()
		.uri("/todos")
		.bodyValue(todo)
		//Resposta
		.exchange()
		.expectStatus().isOk();
	}

	@Test
	@DirtiesContext
	void testCreateTodoSuccess() {
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		webTestClient
		//Requisição
		.post()
		.uri("/todos")
		.bodyValue(todo)
		//Resposta
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		//$: indica o path
		.jsonPath("$").isArray()
		//Verifica se a quantidade retornada é igual 1 registro
		.jsonPath("$.length()").isEqualTo(1)
		// Verifica se as propriedades retornadas são iguais as definidas em todo
		.jsonPath("$[0].nome").isEqualTo(todo.getNome())
		.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
		.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
		.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());
	}

	@Test
	void testeCreateTodoFailure() {
		var todoWrong = new Todo("", "", false, 1);

		webTestClient
		//Requisição
		.post()
		.uri("/todos")
		.bodyValue(todoWrong)
		.exchange()
		.expectStatus().isBadRequest();

	}

	@Test
	@DirtiesContext
	void testeListTodoSuccess() {
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		createTodoSuccess();

		webTestClient
		//Requisição
		.get()
		.uri("/todos")
		//Resposta
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		//$: indica o path
		.jsonPath("$").isArray()
		//Verifica se a quantidade retornada é igual 1 registro
		.jsonPath("$.length()").isEqualTo(1)
		// Verifica se as propriedades retornadas são iguais as definidas em todo
		.jsonPath("$[0].nome").isEqualTo(todo.getNome())
		.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
		.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
		.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());
	}

	@Test
	@DirtiesContext
	void testeListTodoFailure() {
		createTodoSuccess();

		webTestClient
		//Requisição
		.get()
		.uri("/todos/1")
		//Resposta
		.exchange()
		.expectStatus().is4xxClientError();
	}	

	@Test
	@DirtiesContext
	void testeUpdateTodoSuccess() {
		var todo = new Todo(1L, "todo 2", "desc todo 2", false, 1);

		createTodoSuccess();

		webTestClient
		//Requisição
		.put()
		.uri("/todos")
		.bodyValue(todo)
		//Resposta
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		//$: indica o path
		.jsonPath("$").isArray()
		//Verifica se a quantidade retornada é igual 1 registro
		.jsonPath("$.length()").isEqualTo(1)
		// Verifica se as propriedades retornadas são iguais as definidas em todo
		.jsonPath("$[0].nome").isEqualTo(todo.getNome())
		.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
		.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
		.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());
	}

	@Test
	void testeUpdateTodoFailure() {
		var todo = new Todo(1L, "todo 2", "desc todo 2", false, 1);

		webTestClient
		//Requisição
		.put()
		.uri("/todos")
		.bodyValue(todo)
		//Resposta
		.exchange()
		.expectStatus().is5xxServerError();
	}

	@Test
	@DirtiesContext
	void testeDeleteTodoSuccess() {
		createTodoSuccess();

		webTestClient
		//Requisição
		.delete()
		.uri("/todos/1")
		//Resposta
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		//$: indica o path
		.jsonPath("$").isArray()
		//Verifica se a quantidade retornada é igual 1 registro
		.jsonPath("$.length()").isEqualTo(0);
	}	

	@Test
	void testeDeleteTodoFailure() {
		webTestClient
		//Requisição
		.delete()
		.uri("/todos/1")
		//Resposta
		.exchange()
		.expectStatus().is5xxServerError();
	}	
}
