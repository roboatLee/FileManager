import { Question } from "../moudle/question"

export function getAccuracy(q: Question) {
  if (!q.submitCount) return '0%'
  return ((q.correctCount / q.submitCount) * 100).toFixed(1) + '%'
}