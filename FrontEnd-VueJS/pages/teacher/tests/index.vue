<script lang="ts" setup>
import { Test } from "@/types";
import { useTestStore } from "@/stores/test";
const testsStore = useTestStore();

// get
const res = await testsStore.getTests();
const tests = computed(() => testsStore.tests);
</script>

<template>
  <h2 class="title">Quản lý bài thi</h2>
  <v-table fixed-header height="450px" class="test-table">
    <thead>
      <tr>
        <th class="text-left">ID</th>
        <th class="text-left">Môn học</th>
        <th class="text-left">Ngày tạo</th>
        <th class="text-left">Ngày mở đề</th>
        <th class="text-left">Tổng điểm</th>
        <th class="text-left">Thời gian làm bài (phút)</th>
        <th class="text-left">Hành động</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="test in tests" :key="test.id">
        <td>{{ test.id }}</td>
        <td>{{ test.subjectTitle }}</td>
        <td>{{ test.createdAt }}</td>
        <td>{{ test.testDay }}</td>
        <td>{{ test.totalPoint }}</td>
        <td>{{ test.duration }}</td>
        <td class="action">
          <v-icon size="small" class="me-2"> mdi-pencil </v-icon>
          <v-icon size="small"> mdi-delete </v-icon>
        </td>
      </tr>
    </tbody>
  </v-table>
</template>

<style lang="scss" scoped>
.title {
  padding-bottom: 16px;
  border-bottom: 1px solid $color-gray;
  margin-bottom: 12px;
}

.test-table {
  cursor: pointer;
}
</style>
