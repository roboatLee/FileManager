<template>
  <div class="question-detail">
    
    <!-- 题干 -->
    <div class="title" v-html="question.title"></div>

    <!-- 单选 -->
    <div v-if="question.type === 'single_choice'">
      <label v-for="opt in question.options" :key="opt.key">
        <input 
          type="radio" 
          :value="opt.key" 
          v-model="userAnswer[0]"
        />
        {{ opt.key }}. {{ opt.content }}
      </label>
    </div>

    <!-- 多选 -->
    <div v-if="question.type === 'multiple_choice'">
      <label v-for="opt in question.options" :key="opt.key">
        <input 
          type="checkbox" 
          :value="opt.key" 
          v-model="userAnswer"
        />
        {{ opt.key }}. {{ opt.content }}
      </label>
    </div>

    <!-- 判断 -->
    <div v-if="question.type === 'judge'">
      <label>
        <input type="radio" value="true" v-model="userAnswer[0]" />
        正确
      </label>
      <label>
        <input type="radio" value="false" v-model="userAnswer[0]" />
        错误
      </label>
    </div>

    <!-- 提交 -->
    <button @click="submit">提交</button>

    <!-- 结果 -->
    <div v-if="showResult">
      <p v-if="isCorrect" style="color:green;">✔ 正确</p>
      <p v-else style="color:red;">❌ 错误</p>

      <div class="analysis">
        解析：{{ question.analysis }}
      </div>
    </div>

  </div>
</template>

<script setup lang="js">
import { ref } from 'vue';
const userAnswer = ref([])
const showResult = ref(false)
const isCorrect = ref(false)

function submit() {}

const question = ref({
  id: 1,
  title: "函数 f(x)=x² 在 x=1 处的导数是？",
  type: "single_choice",
  options: [
    { key: "A", content: "1" },
    { key: "B", content: "2" },
    { key: "C", content: "3" }
  ],
  answer: ["B"],
  analysis: "导数为 2x，在 x=1 时等于2"
})



</script>


<style scoped>
.question-detail {
  max-width: 700px;
  margin: 0 auto;
}

.title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
}

label {
  display: block;
  margin: 8px 0;
}

button {
  margin-top: 20px;
}
</style>