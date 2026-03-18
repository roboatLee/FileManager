export interface TodoDto {
  id?: number;
  title: string;
  content: string;
  done: boolean;
  deadline: number;
}

export function createTodo(data: Partial<TodoDto>): TodoDto {
  return {
    id: data.id ?? Date.now(),
    title: data.title ?? "未命名任务",
    content: data.content ?? "",
    done: data.done ?? false,
    deadline: data.deadline ?? Date.now() + 86400000 // 默认一天后
  };
}