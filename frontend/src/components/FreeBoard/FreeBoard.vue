<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="page max-page border-sheet-four">
    <div class="page-inner max-page">
      <div class="page-inner-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-bulletin-board</v-icon>
        <h2>자유게시판</h2>
        <FreeBoardRegist v-if="userAuth != 'ROLE_ADMIN'"></FreeBoardRegist>
      </div>
      <div class="pa-5 page-inner-items border-sheet-four">
        <FreeBoardTable :boardSummaryList="boardSummaryList"></FreeBoardTable>
      </div>
    </div>
  </div>
</template>

<script>
import FreeBoardTable from "@/components/FreeBoard/FreeBoardTable.vue"
import FreeBoardRegist from "@/components/FreeBoard/FreeBoardRegist.vue"
import NowLoading from '@/views/NowLoading.vue';
import { getFreeBoard } from "@/api/communityFree.js"
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "FreeBoard",
  computed: {
    ...mapState(userStore, ["userAuth"]),
  },
  components: {
    FreeBoardTable,
    FreeBoardRegist,
    NowLoading,
  },
  data() {
    return {
      loaded: false,
      boardSummaryList: [],
    };
  },
  methods: {
    pushList(board) {
      console.log(board);
      this.boardSummaryList.push(board);
    },
  },
  async created() {
    this.loaded = false;
    this.boardSummaryList = await getFreeBoard();
    this.loaded = true;
    console.log(this.boardSummaryList);
  },
};
</script>

<style></style>
