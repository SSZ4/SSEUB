<template>
  <NowLoading v-if="!loaded"></NowLoading>
  <div v-else class="mypage">
    <div class="mypage-inner border-sheet-four">
      <div class="mypage-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-account</v-icon>
        <h2>회원 정보</h2>
      </div>
      <div class="mypage-content border-sheet-four">
        <MyPageConsultantInfo v-if="getInfoVersion"></MyPageConsultantInfo>
        <MyPageConsultantModify v-else></MyPageConsultantModify>
      </div>
    </div>
    <div class="mypage-inner border-sheet-four">
      <div class="mypage-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-star</v-icon>
        <h2>나의 별점</h2>
      </div>
      <div class="mypage-content border-sheet-four">
        <MyPageConsultantStar></MyPageConsultantStar>
      </div>
    </div>
    <div class="mypage-inner border-sheet-four">
      <div class="mypage-title border-sheet-four">
        <v-icon class="mr-2" size="x-large">mdi-chart-bar</v-icon>
        <h2>나의 활동 통계</h2>
      </div>
      <div class="mypage-graph border-sheet-four">
        <MyPageConsultantGraph></MyPageConsultantGraph>
      </div>
    </div>
  </div>
</template>

<script>
import MyPageConsultantInfo from "@/components/MyPage/MyPageConsultant/MyPageConsultantInfo.vue";
import MyPageConsultantModify from "@/components/MyPage/MyPageConsultant/MyPageConsultantModify.vue";
import MyPageConsultantStar from "@/components/MyPage/MyPageConsultant/MyPageConsultantStar.vue";
import MyPageConsultantGraph from "@/components/MyPage/MyPageConsultant/MyPageConsultantGraph/MyPageConsultantGraph.vue";
import NowLoading from "@/views/NowLoading.vue";
import { getUserConsultantInfo } from "@/api/userInfoConsultant.js";
import { mapState } from "vuex";
const userStore = "userStore";

export default {
  name: "MyPageConsultant",
  components: {
    MyPageConsultantInfo,
    MyPageConsultantModify,
    MyPageConsultantStar,
    MyPageConsultantGraph,
    NowLoading,
  },
  computed: {
    ...mapState(userStore, ["userId"]),
    getConsultantInfo() {
      return this.$store.getters.getConsultantInfo;
    },
    getInfoVersion() {
      return this.$store.getters.getInfoVersion;
    },
  },
  data() {
    return {
      loaded: false,
    };
  },
  async mounted() {
    this.loaded = false;
    // if (this.getConsultantInfo.id != this.userId) {
      try {
        await getUserConsultantInfo(this.userId);
      } catch (e) {
        console.error("# 회원정보 조회 오류", e);
      }
    // }
    this.loaded = true;
  },
};
</script>

<style></style>
