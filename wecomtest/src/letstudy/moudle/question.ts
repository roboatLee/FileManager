export interface Question {
  id: number
  title: string
  type: 'single_choice' | 'multiple_choice' | 'judge' | 'fill' | 'math' | 'subjective'
  difficulty: number
  tags: string[]
  submitCount: number
  correctCount: number
  isPublic: boolean
}