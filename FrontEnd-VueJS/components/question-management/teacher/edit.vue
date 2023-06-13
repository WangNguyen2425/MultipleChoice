<script lang="ts" setup>
const props = defineProps({
  isEditQuestion: {
    type: Boolean,
    default: false,
  },
  questionById: {
    type: Object,
    default: () => ({}),
  },
});

const emit = defineEmits<{
  (e: "edit", data: any): void;
  (e: "close"): void;
}>();

const closeDialog = () => {
  emit("close");
};

const editQuestion = () => {
  // TODO CALL API
  emit("edit", {
    id: 1,
    topicText: "hanh",
  });
};
</script>

<template>
  <div class="dialog-edit-question">
    <v-row justify="center">
      <v-dialog :model-value="isEditQuestion" persistent width="1024">
        <v-card>
          <v-card-title>
            <span class="text-h5">Sửa thông tin câu hỏi</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-select
                    label="Môn học"
                    :items="subjects"
                    class="select"
                    :variant="'outlined'"
                  ></v-select>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-select
                    label="Chương"
                    :items="chapters"
                    class="select"
                    :variant="'outlined'"
                  ></v-select>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-select
                    label="Độ khó"
                    :items="levels"
                    item-title="label"
                    item-value="key"
                    class="select"
                    :model-value="questionById.level"
                    :variant="'outlined'"
                  ></v-select>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    required
                    :model-value="questionById.topicText"
                    :placeholder="'Nhập câu hỏi'"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">Đáp án </v-col>
                <v-col cols="12">
                  <div
                    class="wrap"
                    v-for="answer in questionById.answers"
                    :key="answer.content"
                  >
                    <v-text-field
                      :model-value="answer.content"
                      required
                      :placeholder="'Nhập đáp án'"
                    ></v-text-field>
                    <v-checkbox
                      :model-value="answer.corrected"
                      label="Đáp án đúng"
                    ></v-checkbox>
                  </div>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="closeDialog">
              Huỷ
            </v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="editQuestion">
              Lưu
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<style lang="scss" scoped>
.wrap {
  display: flex;
  align-items: center;
  gap: 32px;

  > .answer {
    flex: 2;
  }
  > .checkbox {
    flex: 1;
  }
}
</style>
