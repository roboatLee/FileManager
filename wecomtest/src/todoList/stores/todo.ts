import { defineStore } from "pinia";
import { ref } from "vue";
import { TodoDto, createTodo } from "../moudle/todoDto";

export const useTodoStore = defineStore("todo", () => {
  const todos = ref<TodoDto[]>([]);

  function addTodo( todo:TodoDto ) {
    const newTodo = createTodo(todo);
    todos.value.push(newTodo);
  }

  function removeTodo(id: number) {
    const index = todos.value.findIndex((t) => t.id === id);
    if (index !== -1) {
      todos.value.splice(index, 1);
    }
  }

  function toggleDone(id: number) {
    const todo = todos.value.find((t) => t.id === id);
    if (todo) {
      todo.done = !todo.done;
    }
  }

  return {
    todos,
    addTodo,
    removeTodo,
    toggleDone,
  };
});
