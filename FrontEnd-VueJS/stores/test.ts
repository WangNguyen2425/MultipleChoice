import { Test } from "@/types";
import { api } from "@/apis";

export const useTestStore = defineStore("test", () => {
  const tests = ref<Test[]>([]);

  const getTests = async () => {
    const res = await api.get("/test/list").catch((err) => {
      console.log(err);
      return null;
    });
    tests.value = res?.data || [];
  };

  const deleteById = async (id: number) => {
    const res = await api.delete(`test/disable/${id}`).catch(() => null);
    if (res !== null) {
      const deletedItemIndex = tests.value.findIndex((item) => item.id === id);
      if (deletedItemIndex > -1) {
        tests.value.splice(deletedItemIndex, 1);
      }
    }
  };

  return {
    tests,
    getTests,
    deleteById,
  };
});
