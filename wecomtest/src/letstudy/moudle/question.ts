export interface OptionDTO {
  key: string
  content: string
}

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

export interface QuestionDetail {
  id: number
  title: string
  type: string
  difficulty: number
  options: OptionDTO[]
  answer: any
  analysis: string
  tags: string[]
  submitCount: number
  correctCount: number
  isPublic: boolean
}