<template>
  <div class="sidebar">
  <!-- <div v-if="!onAir" class="sidebar"> -->
    <div class="border-sheet-one sheet">
      <LogoVer2></LogoVer2>
    </div>
    <v-card class="mx-auto border-sheet-one" max-width="230">
      <!-- 반려인 사이드바 -->
      <v-list
        v-if="userAuth == 'ROLE_USER' || userAuth == null"
        active-color="primary"
        :items="items"
        mandatory
        link
      ></v-list>
      <!-- 전문가 사이드바 -->
      <v-list
        v-else-if="userAuth == 'ROLE_CONSULTANT'"
        active-color="primary"
        :items="consultantItems"
        mandatory
        link
      ></v-list>
      <!-- 관리자 사이드바 -->
      <v-list
        v-else-if="userAuth == 'ROLE_ADMIN'"
        active-color="primary"
        :items="adminItems"
        mandatory
        link
      ></v-list>
    </v-card>
  </div>
</template>

<script>
import LogoVer2 from "@/views/LogoVer2.vue";
import { mapState } from "vuex";

const userStore = "userStore";
// const roomStore = "roomStore";
export default {
  name: "TheSidebar",
  components: {
    LogoVer2,
  },
  data() {
    return {
      items: [
        {
          title: "메인페이지",
          value: 1,
          props: { prependIcon: "mdi-home", to: "/" },
        },
        {
          title: "마이페이지",
          value: 2,
          props: { prependIcon: "mdi-account", to: "/mypage", disabled: true },
        },
        { type: "divider" },
        { type: "subheader", title: "상담" },
        {
          title: "신규 상담 등록",
          value: 3,
          props: {
            prependIcon: "mdi-calendar-plus",
            to: "/create-reservation",
            disabled: true,
          },
        }, // 반려인
        {
          title: "받은 상담 제안",
          value: 5,
          props: {
            prependIcon: "mdi-clipboard-text-outline",
            to: "/receive-matching",
            disabled: true,
          },
        }, // 반려인
        {
          title: "예정 상담 목록",
          value: 7,
          props: {
            prependIcon: "mdi-format-list-text",
            to: "/confirmed",
            disabled: true,
          },
        },
        // {
        //   title: "화상 상담 입장",
        //   value: 8,
        //   props: { prependIcon: "mdi-video-account", to: "/meeting-room" },
        // },
        {
          title: "이전 상담 이력",
          value: 9,
          props: {
            prependIcon: "mdi-format-list-bulleted",
            to: "/finished-reservation",
            disabled: true,
          },
        },
        { type: "divider" },
        { type: "subheader", title: "커뮤니티" },
        {
          title: "공지사항",
          value: 10,
          props: { prependIcon: "mdi-bell", to: "/notice" },
        },
        {
          title: "자유 게시판",
          value: 11,
          props: {
            prependIcon: "mdi-clipboard-edit-outline",
            to: "/free-board",
            disabled: true,
          },
        },
        {
          title: "공개된 상담",
          value: 12,
          props: {
            prependIcon: "mdi-share-variant",
            to: "/open-consult",
          },
        },
      ],
      consultantItems: [
        {
          title: "메인페이지",
          value: 1,
          props: { prependIcon: "mdi-home", to: "/" },
        },
        {
          title: "마이페이지",
          value: 2,
          props: { prependIcon: "mdi-account", to: "/mypage" },
        },
        { type: "divider" },
        { type: "subheader", title: "상담" },
        {
          title: "신규 상담 제안",
          value: 4,
          props: { prependIcon: "mdi-calendar-plus", to: "/create-matching" },
        }, // 전문가
        {
          title: "보낸 상담 제안",
          value: 6,
          props: {
            prependIcon: "mdi-clipboard-text-outline",
            to: "/send-matching",
          },
        }, // 전문가
        {
          title: "예정 상담 목록",
          value: 7,
          props: { prependIcon: "mdi-format-list-text", to: "/confirmed" },
        },
        // {
        //   title: "화상 상담 입장",
        //   value: 8,
        //   props: { prependIcon: "mdi-video-account", to: "/meeting-room" },
        // },
        {
          title: "이전 상담 이력",
          value: 9,
          props: {
            prependIcon: "mdi-format-list-bulleted",
            to: "/finished-reservation",
          },
        },
        { type: "divider" },
        { type: "subheader", title: "커뮤니티" },
        {
          title: "공지사항",
          value: 10,
          props: { prependIcon: "mdi-bell", to: "/notice" },
        },
        {
          title: "자유 게시판",
          value: 11,
          props: {
            prependIcon: "mdi-clipboard-edit-outline",
            to: "/free-board",
          },
        },
        {
          title: "공개된 상담",
          value: 12,
          props: {
            prependIcon: "mdi-share-variant",
            to: "/open-consult",
          },
        },
      ],
      adminItems: [
        {
          title: "메인페이지",
          value: 1,
          props: { prependIcon: "mdi-home", to: "/" },
        },
        { type: "divider" },
        { type: "subheader", title: "회원 관리" },
        {
          title: "전문가 가입 수락",
          value: 2,
          props: { prependIcon: "mdi-account-star", to: "/consultant-accept" },
        },
        {
          title: "회원 탈퇴 처리",
          value: 3,
          props: { prependIcon: "mdi-account-off", to: "/user-withdrawal" },
        },
        {
          title: "회원 알림 보내기",
          value: 4,
          props: { prependIcon: "mdi-comment-account", to: "/user-alarm" },
        },
        { type: "divider" },
        { type: "subheader", title: "게시판 관리" },
        {
          title: "공지사항",
          value: 10,
          props: { prependIcon: "mdi-bell", to: "/notice" },
        },
        {
          title: "자유 게시판",
          value: 11,
          props: {
            prependIcon: "mdi-clipboard-edit-outline",
            to: "/free-board",
          },
        },
        {
          title: "공개된 상담",
          value: 12,
          props: {
            prependIcon: "mdi-share-variant",
            to: "/open-consult",
          },
        },
      ],
    };
  },
  computed: {
    ...mapState(userStore, ["userAuth"]),
    // ...mapState(roomStore,["onAir"])
  },
  watch: {
    userAuth() {
      this.turnOnOffSideBar();
    },
  },
  created() {
    this.turnOnOffSideBar();
  },
  methods: {
    // [@Method] 로그인 여부에 따라 SideBar 비/활성화
    turnOnOffSideBar() {
      if (this.userAuth == null) this.disableSideBar();
      else this.activeSideBar();
    },
    // [@Method] 비활성화 되어 있는 일부 sideBar 활성화
    activeSideBar() {
      this.items[1].props.disabled = false;
      this.items[4].props.disabled = false;
      this.items[5].props.disabled = false;
      this.items[6].props.disabled = false;
      this.items[7].props.disabled = false;
      this.items[11].props.disabled = false;
    },
    // [@Method] 활성화 되어 있는 일부 sideBar 비활성화
    disableSideBar() {
      this.items[1].props.disabled = true;
      this.items[4].props.disabled = true;
      this.items[5].props.disabled = true;
      this.items[6].props.disabled = true;
      this.items[7].props.disabled = true;
      this.items[11].props.disabled = true;
    },
  },
};
</script>

<style>
.sidebar {
  padding-right: 48px;
}
.sidebar .sheet {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  margin-bottom: 20px;

  height: 100px;
  width: 230px;
}
.sidebar .v-list-item-title {
  font-family: "NanumSquareNeo-Variable";
}
</style>
