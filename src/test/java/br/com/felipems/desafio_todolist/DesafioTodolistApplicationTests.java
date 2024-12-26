package br.com.felipems.desafio_todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.felipems.desafio_todolist.entity.Todo;

// Para utilizar o H2 nos testes é necessário definir uma porta randomica
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DesafioTodolistApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testeCreateTodoSuccess() {
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
		var todo = new Todo("", "", false, 1);

		webTestClient
		//Requisição
		.post()
		.uri("/todos")
		.bodyValue(todo)
		.exchange()
		.expectStatus().isBadRequest();

	}

}
