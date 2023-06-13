<script lang="ts" setup>
import { Question } from "@/types";
import { useQuestionStore } from "@/stores/question";
import { useSubjectStore } from "@/stores/subject";

const isEditQuestion = ref(false);

const questionStore = useQuestionStore();
const subjectStore = useSubjectStore();

const subjectCode = ref("");
const questions = computed(() => questionStore.questions);

const questionById = ref({});
//get subjects
const res = await subjectStore.getSubjects();
const subjects = computed(() => subjectStore.subjects);

// get questions
const fetchQuestionsBySubject = async (code: string) => {
  const res = await questionStore.getQuestions(code);
};

const openDialogEditQuestion = (question: object) => {
  questionById.value = question;
  isEditQuestion.value = true;
};

const closeDialog = () => {
  isEditQuestion.value = false;
};

const editQuestion = (value: any) => {
  console.log("kkkk", value.id, value.topicText);
  // TODO CALL API
};

const deleteQuestion = async (id: number) => {
  const res = await questionStore.deleteById(id);
};
</script>

<template>
  <h2 class="title">Ngân hàng câu hỏi</h2>

  <question-management-teacher-create :subjects="subjects" />

  <div class="search-question-list">
    <v-autocomplete
      clearable
      label="Nhập tên môn"
      :items="subjects"
      item-title="title"
      item-value="code"
      v-model="subjectCode"
    ></v-autocomplete>
    <v-btn @click="fetchQuestionsBySubject(subjectCode)">Tìm kiếm</v-btn>
  </div>
  <div class="list-questions">
    <v-table fixed-header height="500px">
      <thead>
        <tr>
          <th class="text-left"></th>
          <th class="text-left">ID</th>
          <th class="text-left">Câu hỏi</th>
          <th class="text-left">Độ khó</th>
          <th class="text-left">Hành động</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="question in questions" :key="question.id">
          <td><v-checkbox-btn /></td>
          <td>{{ question.id }}</td>
          <td>{{ question.topicText }}</td>
          <td>{{ question.level }}</td>
          <td class="action">
            <v-icon
              size="small"
              class="me-2"
              @click="openDialogEditQuestion(question)"
            >
              mdi-pencil
            </v-icon>
            <v-icon size="small" @click="deleteQuestion(question.id)">
              mdi-delete
            </v-icon>
          </td>
        </tr>
      </tbody>
    </v-table>
  </div>

  <question-management-teacher-edit
    :isEditQuestion="isEditQuestion"
    :questionById="questionById"
    @close="closeDialog"
    @edit="editQuestion"
  />
</template>

<style scoped lang="scss">
.title {
  padding-bottom: 16px;
  border-bottom: 1px solid $color-gray;
  margin-bottom: 12px;
}

.search-question-list {
  display: flex;
  gap: 32px;
  margin: 32px 0;
  justify-content: start;
  align-items: center;
}
</style>
